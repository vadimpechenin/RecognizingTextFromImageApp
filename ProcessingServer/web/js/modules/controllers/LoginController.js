import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";

export default class LoginController {
    constructor() {
        this._network = new NetworkClient(this);
    }

    init() {
        CommonUtils.getContainer('OkButton').click(this.#onOk.bind(this));
        CommonUtils.getContainer('CancelButton').click(LoginController.#onCancel.bind(this));
        CommonUtils.getContainer('ExitButton').click(LoginController.#onExit.bind(this));
    }

    #onOk(){
        let username = CommonUtils.getContainer('UserName').val()
        let password = CommonUtils.getContainer('Password').val();
        this._network.commandLogin(username, password, LoginController.#onAuthorizationPassed, LoginController.#onAuthorizationFailed)
    }

    static #onCancel(){
        CommonUtils.getContainer('UserName').val('');
        CommonUtils.getContainer('Password').val('');
    }

    static #onExit(){
        document.location.href = '../ProcessingServer/startform.html';
    }

    static #onAuthorizationPassed(){
        document.location.href = '../ProcessingServer/MainServicePage.html';
    }

    static #onAuthorizationFailed(){
        alert('Неправильное имя пользователя или пароль');
    }
}
