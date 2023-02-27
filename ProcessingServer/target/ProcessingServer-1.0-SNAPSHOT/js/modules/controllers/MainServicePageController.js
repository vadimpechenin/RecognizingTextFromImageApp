import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";


export default class MainServicePageController {
    constructor() {
        this._network = new NetworkClient(this);
    }

    init() {
        CommonUtils.getContainer('LogoutButton').click(this.#onExit.bind(this));
        CommonUtils.getContainer('History').click(MainServicePageController.#onHistoryForm.bind(this));
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
}
