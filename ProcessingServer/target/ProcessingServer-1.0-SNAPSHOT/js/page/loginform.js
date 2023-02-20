import LoginController from "../modules/controllers/LoginController.js";

function init() {
    let controller = new LoginController();
    controller.init();
}

$(document).ready(init);