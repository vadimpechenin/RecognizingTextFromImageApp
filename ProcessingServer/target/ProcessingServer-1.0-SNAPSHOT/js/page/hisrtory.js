import HistoryController from "../modules/controllers/HistoryController.js";

function init() {
    let controller = new HistoryController();
    controller.init();
}

$(document).ready(init);