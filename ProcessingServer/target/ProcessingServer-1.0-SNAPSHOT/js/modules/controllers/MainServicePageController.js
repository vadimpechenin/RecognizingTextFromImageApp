import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";


let fileToRecognize;
let recognitionFile;
let recognition = 1;

export default class MainServicePageController {

    constructor() {
        this._network = new NetworkClient(this);
    }

    init() {
        CommonUtils.getContainer('LogoutButton').click(this.#onExit.bind(this));
        CommonUtils.getContainer('History').click(MainServicePageController.#onHistoryForm.bind(this));
        CommonUtils.getContainer('Recognized').click(this.#makeRecognition.bind(this));
        CommonUtils.getContainer('Download').click(this.#makeDownload.bind(this));
        CommonUtils.getContainer('Save').click(this.#makeSave.bind(this));
    }

    #onExit(){
        this._network.commandLogout(MainServicePageController.#onAuthorizationPassed, MainServicePageController.#onAuthorizationFailed)
    }

    static #onHistoryForm(){
        document.location.href = '../ProcessingServer/history.html';
    }

    static #onAuthorizationPassed(){
        document.location.href = '../ProcessingServer/loginform.html';
    }

    static #onAuthorizationFailed(){
        alert('Некорректный выход');
    }

    #makeRecognition() {
        if (fileToRecognize != null) {
            let title = document.getElementById('inputTitle').value;
            if (title!= null) {
                this._network.commandRecognition(title, fileToRecognize, MainServicePageController.#onRecognitionPassed, MainServicePageController.#onRecognitionFailed)
            }else{
                alert('Введите название');
            }
        }else{
            alert('Выберите файл');
        }
    }

    #makeDownload() {
        if (recognitionFile != null){
            let title = document.getElementById('inputTitle').value;
            if (title != null) {
                CommonUtils.downloadDOCX(recognitionFile, title);
            }
        }

    }

    #makeSave() {
        if ((fileToRecognize != null) && (recognitionFile != null)) {
            let title = document.getElementById('inputTitle').value;
            if (title != null) {
                if (recognition === 0){
                    this._network.commandSave(title, fileToRecognize, recognitionFile, MainServicePageController.#onSavePassed, MainServicePageController.#onSaveFailed)
                }else{
                    alert('Текущий документ уже сохранен');
                }
            }else{
                alert('Введите название');
            }
        }else{
            alert('Выберите файл или распознайте его');
        }
    }

    static #onRecognitionPassed(data){
        recognitionFile = null;
        recognitionFile = new Blob([data.value], {type: 'text/plain'});
        alert('Документ распознан');
        recognition = 0;
    }

    static #onRecognitionFailed(){
        alert('Не удалось распознать документ');
    }

    static #onSavePassed(){
        recognition = 1;
        alert('Запись сохранена');
    }

    static #onSaveFailed(){
        alert('Не удалось сохранить документ');
    }
}

;((D, B, log = (arg) => console.log(arg)) => {
    //1 Объявляем переменные для файлоприемника, инпута и файла
    const dropZone = D.querySelector('.loadFile')//('div')
    const input = D.querySelector('.content')//('input')
    let file
    // Отключаем обработку событий «dragover» и «drop» браузером:
    D.addEventListener('dragover', ev => ev.preventDefault())
    D.addEventListener('drop', ev => ev.preventDefault())
    dropZone.addEventListener('drop', ev => {
        ev.preventDefault()
        log(ev.dataTransfer)
        file = ev.dataTransfer.files[0]
        log(file)
        handleFile(file)
    })

//3 Обрабатываем клик по файлоприемнику (делегируем клик инпуту):
    dropZone.addEventListener('click', () => {
        input.click()
        input.addEventListener('change', () => {
            log(input.files)
            file = input.files[0]
            log(file)
            handleFile(file)
            fileToRecognize = file;
        })
    })

//Приступаем к обработке файла:
    const handleFile = file => {
        dropZone.remove();
        input.remove();
        if (file.type === 'text/html' ||
            file.type === 'text/css' ||
            file.type === 'text/javascript')
            return;

        if (file.type === 'application/pdf') {
            createIframe(file)
            return;
        }

        const type = file.type.replace(/\/.+/, '')

        log(file.type)
        switch (type) {
            case 'image':
                createImage(file)
                break;
            case 'audio':
                createAudio(file)
                break;
            case 'video':
                createVideo(file)
                break;
            case 'text':
                createText(file)
                break;
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
        const imageEl = D.createElement('img')
        imageEl.src = URL.createObjectURL(image)
        log(imageEl)
        B.append(imageEl)
        URL.revokeObjectURL(image)
    }
//Функция обработки аудио:
    const createAudio = audio => {
        const audioEl = D.createElement('audio')
        audioEl.setAttribute('controls', '')
        audioEl.src = URL.createObjectURL(audio)
        log(audioEl)
        B.append(audioEl)
        audioEl.play()
        URL.revokeObjectURL(audio)
    }
//Функция обработки видео:
    const createVideo = video => {
        const videoEl = D.createElement('video')
        videoEl.setAttribute('controls', '')
        videoEl.setAttribute('loop', 'true')
        videoEl.src = URL.createObjectURL(video)
        log(videoEl)
        B.append(videoEl)
        videoEl.play()
        URL.revokeObjectURL(video)
    }
//Функция обработки текста:
    const createText = text => {
        const reader = new FileReader()
        reader.readAsText(text, 'windows-1251')
        reader.onload = () => B.innerHTML = `<p><pre>${reader.result}</pre></p>`
    }
//функция обработки pdf-файлов:
    const createIframe = pdf => {
        const iframe = D.createElement('iframe')
        iframe.src = URL.createObjectURL(pdf)
        iframe.width = innerWidth
        iframe.height = innerHeight
        log(iframe)
        let element = document.getElementById('loadContent');
        //document.body.append(iframe)
        element.appendChild(iframe);
        //B.append(iframe)
        URL.revokeObjectURL(pdf)
    }
})(document, document.body)
