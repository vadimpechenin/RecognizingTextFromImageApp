import NetworkClient from "../NetworkClient.js";
import CommonUtils from "../CommonUtils.js";

export default class RegistrationController {
    constructor() {
        this._network = new NetworkClient(this);
    }

    init() {
        CommonUtils.getContainer('OkButton').click(this.#onOk.bind(this));
        CommonUtils.getContainer('CancelButton').click(RegistrationController.#onCancel.bind(this));
        CommonUtils.getContainer('ExitButton').click(RegistrationController.#onExit.bind(this));
    }

    #onOk(){
        let firstname = CommonUtils.getContainer('firstname').val()
        let lastname = CommonUtils.getContainer('lastname').val();
        let email = CommonUtils.getContainer('email').val()
        let username = CommonUtils.getContainer('UserName').val()
        let password = CommonUtils.getContainer('Password').val();
        this._network.commandRegistration(firstname, lastname, email, username, password, RegistrationController.#onAuthorizationPassed, RegistrationController.#onAuthorizationFailed)
    }

    static #onCancel(){
        CommonUtils.getContainer('firstname').val('');
        CommonUtils.getContainer('lastname').val('');
        CommonUtils.getContainer('email').val('');
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
        alert('Некорректно заполненные поля формы');
    }
}
