import NetworkClient from "../modules/NetworkClient.js";

function run() {
    this._username = "fedor";
    this._password = "123asd";

    this._network = new NetworkClient(this);
    this._network.commandLogin(this._username, this._password, onLoginSuccess, onError);
}

function onError(){
    alert("Ошибка");
}

function onLoginSuccess(){
    document.location.href = '../ProcessingServer/MainServicePage.html';
}

$(document).ready(run);
