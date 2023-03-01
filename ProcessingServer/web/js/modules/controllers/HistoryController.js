import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";
import Document from "../entities/Document.js";
import DocumentWithoutFile from "../entities/DocumentWithoutFile.js";

let documentsArray;
let documentWithFile
let pdfFile
let resultFile

export default class HistoryController {
    constructor() {
        this._network = new NetworkClient(this);
        this._network.commandHistory(HistoryController.#onHistoryForm, HistoryController.#onHistoryFormFailed)
    }

    init() {
        CommonUtils.getContainer('ReturnToMain').click(HistoryController.#onExit.bind(this));
        CommonUtils.getContainer('SelectDocument').click(this.#selectDocument.bind(this));
        CommonUtils.getContainer('DownloadPDF').click(HistoryController.#downloadPDF.bind(this));
        CommonUtils.getContainer('DownloadDOCX').click(HistoryController.#downloadDOCX.bind(this));
    }

    static #onHistoryForm(result){
        documentsArray = []
        for (let i = 0; i < result.documents.length; i++) {
            HistoryController.addNewElement(i, result.documents[i])
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
        documentWithFile = new Document(data.documents[0].id,
            data.documents[0].userID,
            data.documents[0].title,
            data.documents[0].filepdf,
            data.documents[0].filetext);
        //TODO - заменить на просто файлы с сервера, уже не в массиве байт
        pdfFile = new Blob([documentWithFile.filepdf], { type: 'application/pdf' });
        resultFile = new Blob([documentWithFile.filetext], {type: 'text/plain'});
        createIframe(pdfFile)
        alert('Готово');
    }

    static #onLoadDocumentFailed(){
        alert('Нет сохраненного документа');
    }

    static #downloadPDF(){
        if (pdfFile != null) {
            let fileURL = URL.createObjectURL(pdfFile);
            console.log(fileURL);

            let anchor = document.createElement('a');
            anchor.href = fileURL;
            anchor.download = documentWithFile.title + '.pdf';
            document.body.append(anchor);
            anchor.style = "display none";
            anchor.click();
            anchor.remove();
        }
    }

    static #downloadDOCX(){
        if (resultFile != null) {
            let fileURL = URL.createObjectURL(resultFile);
            console.log(fileURL);

            let anchor = document.createElement('a');
            anchor.href = fileURL;
            anchor.download = documentWithFile.title + '.docx';
            document.body.append(anchor);
            anchor.style = "display none";
            anchor.click();
            anchor.remove();
        }
    }

    static #onExit(){
        document.location.href = '../ProcessingServer/MainServicePage.html';
    }
}


//функция обработки pdf-файлов:
const createIframe = pdf => {
    const iframe = document.createElement('iframe');
    iframe.src = URL.createObjectURL(pdf);
    iframe.width = innerWidth;
    iframe.height = innerHeight;
    console.log(iframe);
    let element = document.getElementById('content');
    element.appendChild(iframe);
    URL.revokeObjectURL(pdf);
}