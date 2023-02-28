import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";
import Document from "../entities/Document.js";
import DocumentWithoutFile from "../entities/DocumentWithoutFile.js";

let documentsArray;
let documentWithFile
let pdfFile

export default class HistoryController {
    constructor() {
        this._network = new NetworkClient(this);
        this._network.commandHistory(HistoryController.#onHistoryForm, HistoryController.#onHistoryFormFailed)
    }

    init() {
        CommonUtils.getContainer('ReturnToMain').click(HistoryController.#onExit.bind(this));
        CommonUtils.getContainer('SelectDocument').click(this.#selectDocument.bind(this));
    }

    static #onHistoryForm(result){
        documentsArray = []
        for (let i = 0; i < result.length; i++) {
            HistoryController.addNewElement(i, result[i])
        }
        document.querySelector('.list').innerHTML =`<div class="documents"></div>`
        for (let i = 0; i < documentsArray.length; i++){
            let _document = documentsArray[i]
            let row = document.createElement('div')
            row.innerHTML=`<input type="radio" class="i-1" id=${_document.id} checked name="rb1" value= ${_document.id}>
                    <label for=${_document.id}>${_document.title}</label>`
            document.querySelector('.documents').appendChild(row)

        }
    }

    #selectDocument(){
        let radio = document.querySelectorAll('.i-1');
        let data = '';
        for (let i=0; i<radio.length; i++){
            if (radio[i].checked){
                data = radio[i].value;
                break;
            }
        }
        console.log(data)
        this._network.commandLoadDocument(data, HistoryController.#onLoadDocumentPassed, HistoryController.#onLoadDocumentFailed);
    }

    static #onHistoryFormFailed(){
        alert('Нет ранее загруженных документов');
    }

    static addNewElement(i, result){
        documentsArray[i] = new DocumentWithoutFile(result.id, result.userID, result.title);
        console.log(documentsArray[i]);
    }

    static #onLoadDocumentPassed(data){
        documentWithFile = new Document(data[0].id, data[0].userID, data[0].title, data[0].filepdf, data[0].filetext);
        pdfFile = documentWithFile.filepdf
        let file = new Blob([pdfFile], { type: 'application/pdf' });
        let fileURL = URL.createObjectURL(file);
        window.open(fileURL);
        createIframe(file)
      /*  getDocument()
            .success(function(data) {
                let file = new Blob([data], { type: 'application/pdf' });
                let fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            })*/

        alert('Готово');
    }

    static #onLoadDocumentFailed(){
        alert('Нет сохраненного документа');
    }

    static #onExit(){
        document.location.href = '../ProcessingServer/MainServicePage.html';
    }
}


//функция обработки pdf-файлов:
const createIframe = pdf => {
    const iframe = document.createElement('iframe')
    iframe.src = URL.createObjectURL(pdf)
    iframe.width = innerWidth
    iframe.height = innerHeight
    log(iframe)
    document.body.append(iframe)
    URL.revokeObjectURL(pdf)
}