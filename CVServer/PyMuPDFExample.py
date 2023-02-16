"""
Использование PyMuPDF для распознавания картинок
pip install PyMuPDF

"""
import fitz

#pdf_document = fitz.open("pdf\\PDFOfImage.pdf")
#pdf_document = fitz.open("pdf\\slaids.pdf")
pdf_document = fitz.open("pdf\\Text_handwritten.pdf")
for current_page in range(len(pdf_document)):
   #for image in pdf_document.getPageImageList(current_page):
    for image in pdf_document.get_page_images(current_page):
       xref = image[0]
       pix = fitz.Pixmap(pdf_document, xref)
       if pix.n < 5:        # this is GRAY or RGB
           pix.save("images\\page%s-%s.png" % (current_page, xref))
       else:                # CMYK: convert to RGB first
           pix1 = fitz.Pixmap(fitz.csRGB, pix)
           pix1.save("images\\page%s-%s.png" % (current_page, xref))
           pix1 = None
       pix = None
