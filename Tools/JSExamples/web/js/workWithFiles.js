;((D, B, log = (arg) => console.log(arg)) => {
    //https://habr.com/ru/post/511742/
    // наш код

    // это позволит обращаться к document и document.body как к D и B, соответственно
    // log = arg => console.log(arg) - здесь мы используем параметры по умолчанию
    // это позволит вызывать console.log как log

    //1 Объявляем переменные для файлоприемника, инпута и файла
    const dropZone = D.querySelector('.buttonFile')//('div')
    const input = D.querySelector('.content')//('input')
    let file
    // Отключаем обработку событий «dragover» и «drop» браузером:
    D.addEventListener('dragover', ev => ev.preventDefault())
    D.addEventListener('drop', ev => ev.preventDefault())

    //2 Обрабатываем бросание файла в файлоприемник:
    dropZone.addEventListener('drop', ev => {
        // отключаем поведение по умолчанию
        ev.preventDefault()

        // смотрим на то, что получаем
        log(ev.dataTransfer)

        // получаем следующее (в случае передачи изображения)
        /*
        DataTransfer {dropEffect: "none", effectAllowed: "all", items: DataTransferItemList, types: Array(1), files: FileList}
            dropEffect: "none"
            effectAllowed: "all"
        =>  files: FileList
                length: 0
            __proto__: FileList
            items: DataTransferItemList {length: 0}
            types: []
            __proto__: DataTransfer
        */

        // интересующий нас объект (File) хранится в свойстве "files" объекта "DataTransfer"
        // извлекаем его
        file = ev.dataTransfer.files[0]

        // проверяем
        log(file)
        /*
        File {name: "image.png", lastModified: 1593246425244, lastModifiedDate: Sat Jun 27 2020 13:27:05 GMT+0500 (Екатеринбург, стандартное время), webkitRelativePath: "", size: 208474, …}
            lastModified: 1593246425244
            lastModifiedDate: Sat Jun 27 2020 13:27:05 GMT+0500 (Екатеринбург, стандартное время) {}
            name: "image.png"
            size: 208474
            type: "image/png"
            webkitRelativePath: ""
            __proto__: File
        */

        // передаем файл в функцию для дальнейшей обработки
        handleFile(file)
    })

    //3 Обрабатываем клик по файлоприемнику (делегируем клик инпуту):
    dropZone.addEventListener('click', () => {
        // кликаем по скрытому инпуту
        input.click()

        // обрабатываем изменение инпута
        input.addEventListener('change', () => {
            // смотрим на то, что получаем
            log(input.files)

            // получаем следующее (в случае передачи изображения)
            /*
            FileList {0: File, length: 1}
            =>  0: File
                    lastModified: 1593246425244
                    lastModifiedDate: Sat Jun 27 2020 13:27:05 GMT+0500 (Екатеринбург, стандартное время) {}
                    name: "image.png"
                    size: 208474
                    type: "image/png"
                    webkitRelativePath: ""
                    __proto__: File
                length: 1
                __proto__: FileList
            */

            // извлекаем File
            file = input.files[0]

            // проверяем
            log(file)

            // передаем файл в функцию для дальнейшей обработки
            handleFile(file)
        })
    })

    //Приступаем к обработке файла:
    const handleFile = file => {
        //Удаляем файлоприемник и инпут:
        dropZone.remove();
        input.remove();
        // дальнейшие рассуждения

        if (file.type === 'text/html' ||
            file.type === 'text/css' ||
            file.type === 'text/javascript')
            return;


        if (file.type === 'application/pdf') {
            createIframe(file)
            return;
        }

        // нас интересует то, что находится до слеша
        const type = file.type.replace(/\/.+/, '')

        log(file.type)
        // в случае изображения
        // image/png
        switch (type) {
            // если изображение
            case 'image':
                createImage(file)
                break;
            // если аудио
            case 'audio':
                createAudio(file)
                break;
            // если видео
            case 'video':
                createVideo(file)
                break;
            // если текст
            case 'text':
                createText(file)
                break;
            // иначе, выводим сообщение о неизвестном формате файла,
            // и через две секунды перезагружаем страницу
            default:
                B.innerHTML = `<h3>Unknown File Format!</h3>`
                const timer = setTimeout(() => {
                    location.reload()
                    clearTimeout(timer)
                }, 2000)
                break;
        }

    }
    //Функция обработки изображения:
    const createImage = image => {
        // создаем элемент "img"
        const imageEl = D.createElement('img')
        // привязываем его к полученному изображению
        imageEl.src = URL.createObjectURL(image)
        // проверяем
        log(imageEl)
        // помещаем в документ
        B.append(imageEl)
        // удаляем ссылку на файл
        URL.revokeObjectURL(image)
    }
    //Функция обработки аудио:
    const createAudio = audio => {
        // создаем элемент "audio"
        const audioEl = D.createElement('audio')
        // добавляем панель управления
        audioEl.setAttribute('controls', '')
        // привязываем элемент к полученному файлу
        audioEl.src = URL.createObjectURL(audio)
        // проверяем
        log(audioEl)
        // помещаем в документ
        B.append(audioEl)
        // запускаем воспроизведение
        audioEl.play()
        // удаляем ссылку на файл
        URL.revokeObjectURL(audio)
    }
    //Функция обработки видео:
    const createVideo = video => {
        // создаем элемент "video"
        const videoEl = D.createElement('video')
        // добавляем панель управления
        videoEl.setAttribute('controls', '')
        // зацикливаем воспроизведение
        videoEl.setAttribute('loop', 'true')
        // привязываем элемент к полученному файлу
        videoEl.src = URL.createObjectURL(video)
        // проверяем
        log(videoEl)
        // помещаем в документ
        B.append(videoEl)
        // запускаем воспроизведение
        videoEl.play()
        // удаляем ссылку на файл
        URL.revokeObjectURL(video)
    }
    //Функция обработки текста:
    const createText = text => {
        // создаем экземпляр объекта "FileReader"
        const reader = new FileReader()
        // читаем файл как текст
        // вторым аргументом является кодировка
        // по умолчанию - utf-8,
        // но она не понимает кириллицу
        reader.readAsText(text, 'windows-1251')
        // дожидаемся завершения чтения файла
        // и помещаем результат в документ
        reader.onload = () => B.innerHTML = `<p><pre>${reader.result}</pre></p>`
    }
    //Last, but not least, функция обработки pdf-файлов:
    const createIframe = pdf => {
        // создаем элемент "iframe"
        const iframe = D.createElement('iframe')
        // привязываем его к полученному файлу
        iframe.src = URL.createObjectURL(pdf)
        // увеличиваем размеры фрейма до ширины и высоты области просмотра
        iframe.width = innerWidth
        iframe.height = innerHeight
        // проверяем
        log(iframe)
        // помещаем в документ
        B.append(iframe)
        // удаляем ссылку на файл
        URL.revokeObjectURL(pdf)
    }
})(document, document.body)