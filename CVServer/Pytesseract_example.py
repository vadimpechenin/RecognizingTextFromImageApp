#Использование pytesseract для распознавания текста с картинок

import pytesseract
import cv2
import matplotlib.pyplot as plt
from PIL import Image
import fitz

def take_image(current_page, xref, pl,target_word):
    if (pl==0):
        image = cv2.imread("images\\page%s-%s.png" % (current_page, xref))
    else:
        image = cv2.imread("D:\\PYTHON\\Programms\\CompleteSetOfBlades2021\\IMGTest.jpg")
   
    # получаем строку
    pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
    string = pytesseract.image_to_string(image, lang='rus')
    # печатаем
    print(string)

    # чтобы нарисовать сделаем копию изображения
    image_copy = image.copy()
    # слово для поиска
    #target_word = "по"
    # получить все данные из изображения
    data = pytesseract.image_to_data(image, lang='rus', output_type=pytesseract.Output.DICT)

    # Повышенние резкости изображения:
    #enhancer1 = ImageEnhance.Sharpness(image)
    #factor1 = 0.01  # чем меньше, тем больше резкость
    #im_s_1 = enhancer1.enhance(factor1)

    text = pytesseract.image_to_string(image, config='--psm 6 -c tessedit_char_whitelist=0123456789,. ')
    #text = pytesseract.image_to_string(image, config='--psm 6 -c tessedit_char_whitelist=0123456789,. ').split('\n')
    #print(text)

    # получить все вхождения нужного слова
    word_occurences = [i for i, word in enumerate(data["text"]) if word == target_word] #word.lower()== target_word
    # Распознаем числа
    #word_occurences = [i for i, word in enumerate(text) if word.lower() == target_word]
    # Теперь нарисуем вокруг найденного слова рамку
    for occ in word_occurences:
        # извлекаем ширину, высоту, верхнюю и левую позицию для обнаруженного слова
        w = data["width"][occ]
        h = data["height"][occ]
        l = data["left"][occ]
        t = data["top"][occ]
        # определяем все точки окружающей рамки
        p1 = (l, t)
        p2 = (l + w, t)
        p3 = (l + w, t + h)
        p4 = (l, t + h)
        # рисуем 4 линии (прямоугольник)
        image_copy = cv2.line(image_copy, p1, p2, color=(255, 0, 0), thickness=2)
        image_copy = cv2.line(image_copy, p2, p3, color=(255, 0, 0), thickness=2)
        image_copy = cv2.line(image_copy, p3, p4, color=(255, 0, 0), thickness=2)
        image_copy = cv2.line(image_copy, p4, p1, color=(255, 0, 0), thickness=2)
    # Сохраним полученное изображение
    if (pl == 0):
        plt.imsave("images\\all_customer_words.png%s-%s.png" % (current_page, xref), image_copy)
    else:
        plt.imsave("D:\\PYTHON\\Programms\\CompleteSetOfBlades2021\\IMGTestDetection1.jpg", image_copy)
    plt.imshow(image_copy)
    plt.show()


#Пытаемся взять данные с чертежа
if (1==0):
    print('****************')
    word ="3"
    print('Берем слово %s' % (word))
    take_image(1, 1, 1,word)

pdf_document = fitz.open("pdf\\PDFOfImage.pdf")
index = 0
for current_page in range(len(pdf_document)):
    # for image in pdf_document.getPageImageList(current_page):
    for image in pdf_document.get_page_images(current_page):
        if (index == 2):
            xref = image[0]
            pix = fitz.Pixmap(pdf_document, xref)
            print('****************')
            print('Страница %s' % (current_page))
            # читать изображение с помощью OpenCV
            take_image(current_page, xref, 0,"НИР")
        index+=1

