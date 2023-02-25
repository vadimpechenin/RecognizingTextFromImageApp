import Document from "../entities/Document.js";
import CommonUtils from "../CommonUtils.js";


export default class HistoryController {
    constructor() {
        this._documents = [];
        /*import { documentsArray } from '../controllers/MainServicePageController.js';
        for (let i = 0; i < result.length; i++) {
            this.addNewElement(i, result[i])
        }*/
    }

    addNewElement(i, result){
        this._documents[i] = new Document(result.id, result.userID, result.title);
    }

    init() {
        CommonUtils.getContainer('ReturnToMain').click(HistoryController.#onExit.bind(this));
        this._documetns = {
            'Пожарная служба' : [
                ['Номер 1', '101'],
                ['Номер 2', '112']
            ],
            'Полиция' : [
                ['Номер 1', '102'],
                ['Номер 2', '112']
            ]
        }
    }

    static #onExit(){
        document.location.href = '../JSExamples/MainServicePage.html';
    }
}
