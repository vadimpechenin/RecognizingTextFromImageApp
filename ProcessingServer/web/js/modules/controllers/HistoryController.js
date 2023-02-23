import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";

export default class HistoryController {
    constructor() {
        this._network = new NetworkClient(this);
        this.documents = [];
    }

    init() {
        CommonUtils.getContainer('ReturnToMain').click(HistoryController.#onExit.bind(this));
    }

    static #onExit(){
        document.location.href = '../ProcessingServer/MainServicePage.html';
    }
}
