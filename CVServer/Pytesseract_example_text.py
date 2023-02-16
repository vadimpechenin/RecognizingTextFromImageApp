#Использование pytesseract для распознавания текста с картинок

import pytesseract
import cv2
import matplotlib.pyplot as plt
from PIL import Image
import fitz
import docx

def take_image(current_page, xref, pl,mydoc):
    if (pl==0):
        image = cv2.imread("images\\page%s-%s.png" % (current_page, xref))
    else:
        image = cv2.imread("D:\\PYTHON\\Programms\\CompleteSetOfBlades2021\\IMGTest.jpg")
   
    # получаем строку
    pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
    string = pytesseract.image_to_string(image, lang='rus')
    # печатаем
    print(string)
    mydoc.add_paragraph(string)


pdf_document = fitz.open("pdf\\slaids.pdf")

index = 0
mydoc = docx.Document()
print(len(pdf_document))
for current_page in range(len(pdf_document)):
    for image in pdf_document.get_page_images(current_page):
        #if (index == 2):
        xref = image[0]
        pix = fitz.Pixmap(pdf_document, xref)
        print('****************')
        print('Страница %s' % (current_page))
        # читать изображение с помощью OpenCV
        take_image(current_page, xref, 0,mydoc)
        index+=1

mydoc.save("pdf\\slaids.docx")
