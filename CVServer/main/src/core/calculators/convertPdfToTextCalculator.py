import os
from io import StringIO

import docx
import fitz
import cv2

class ConvertPdfToTextCalculator:

    @staticmethod
    def calculate(dictOfProject, parameters):
        #
        pdfDocument = ConvertPdfToTextCalculator.__getPDF(dictOfProject, parameters)
        image = ConvertPdfToTextCalculator.__getImages(pdfDocument, parameters)
        items = ConvertPdfToTextCalculator.__recognitionText(pdfDocument, parameters)
        itemshex = ConvertPdfToTextCalculator.__convertToHex(items, parameters)
        result = {'result': (items is not None), 'value': itemshex}
        return result

    @staticmethod
    def __getPDF(dictOfProject, parameters):
        for imageFromList in dictOfProject['InputDocument']:
            byteImage = bytes.fromhex(imageFromList)
            fileName = str(parameters.imagesFolder.joinpath('example.pdf').resolve())
            file = open(fileName, 'wb')
            file.write(byteImage)
            file.close()
            pdfDocument = fitz.open(fileName)

        return pdfDocument

    @staticmethod
    def __getImages(pdfDocument, parameters):
        img = []
        for current_page in range(len(pdfDocument)):
            # for image in pdf_document.getPageImageList(current_page):
            for image in pdfDocument.get_page_images(current_page):
                xref = image[0]
                pix = fitz.Pixmap(pdfDocument, xref)
                fileName = str(parameters.imagesFolder.joinpath("page%s-%s.png" % (current_page, xref)).resolve())
                if pix.n > 5:
                    pix = None
                    pix = fitz.Pixmap(fitz.csRGB, pix)
                pix.save(fileName)
                img.append(pix)
                pix = None
        return img

    @staticmethod
    def __recognitionText(pdfDocument, parameters):
        mydoc = docx.Document()
        for current_page in range(len(pdfDocument)):
            for image in pdfDocument.get_page_images(current_page):
                xref = image[0]
                fileName = str(parameters.imagesFolder.joinpath("page%s-%s.png" % (current_page, xref)).resolve())
                # читать изображение с помощью OpenCV
                ConvertPdfToTextCalculator.__takeImage(parameters.pytesseract, mydoc,fileName)
        pdfDocument.close()
        return mydoc

    @staticmethod
    def __takeImage(pytesseract, mydoc,fileName):
        image = cv2.imread(fileName)
        # получаем строку
        pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
        string = pytesseract.image_to_string(image, lang='rus')
        # добавляем в документ
        mydoc.add_paragraph(string)

    @staticmethod
    def __convertToHex(items, parameters):
        fileName = str(parameters.imagesFolder.joinpath('example.docx').resolve())
        fileNamePDF = str(parameters.imagesFolder.joinpath('example.pdf').resolve())
        items.save(fileName)
        #with open(fileName, 'rb') as f:
        #    source_stream = StringIO(f.read())

        file = open(fileName, "rb")
        fileContent = file.read()
        strContent = fileContent.hex()
        file.close()
        #items.close()
        #os.remove(fileName)
        os.remove(fileNamePDF)
        return strContent