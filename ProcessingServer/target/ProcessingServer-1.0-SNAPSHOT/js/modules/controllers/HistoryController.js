import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";
import Document from "../entities/Document.js";

export default class HistoryController {
    constructor() {
        this._network = new NetworkClient(this);
        this._documents = [];
        import documentsArray from '../controllers/MainServicePageController.js';
        for (let i = 0; i < result.length; i++) {
            this.addNewElement(i, result[i])
        }
    }

    addNewElement(i, result){
        this._documents[i] = new Document(result.id, result.userID, result.title);
    }

    init() {
        CommonUtils.getContainer('ReturnToMain').click(HistoryController.#onExit.bind(this));
    }

    static #onExit(){
        document.location.href = '../ProcessingServer/MainServicePage.html';
    }
}
