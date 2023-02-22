import MainServicePageController from "../modules/controllers/MainServicePageController.js";

function init() {
    let controller = new MainServicePageController();
    controller.init();
}

$(document).ready(init);


