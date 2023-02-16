"""
Работа по чтению и записи файлов pdf с библиотекой PyPDF2
pip install PyPDF2
"""
# reader.py
from PyPDF2 import PdfReader

def get_pdf_info(path):
    reader = PdfReader(path)
    number_of_pages = len(reader.pages)
    page = reader.pages[0]
    text = page.extract_text()

    print(text)
    print(page)
    print('PDF has {} pages'.format(number_of_pages))


if __name__ == '__main__':
    #чтение pdf с четким текстом
    get_pdf_info('pdf\\PDFWithWords.pdf')
    #get_pdf_info('pdf\\slaids.pdf')
    #чтение pdf с нечетким текстом
    get_pdf_info('pdf\\PDFOfImage.pdf')