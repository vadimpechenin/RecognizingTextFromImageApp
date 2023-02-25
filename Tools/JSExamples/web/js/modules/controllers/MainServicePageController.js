import CommonUtils from "../CommonUtils.js";
import Document from "../entities/Document.js";

let documentsArray;

export default class MainServicePageController {
    constructor() {
    }

    init() {
        CommonUtils.getContainer('History').click(this.#onHistory.bind(this));
    }

    #onHistory(){
        MainServicePageController.#onHistoryForm()
    }


    static #onHistoryForm(){
        documentsArray = [];
        for (let i = 0; i < 3; i++) {
            documentsArray[i]= new Document("1", "11", "Название1")
        }
        export { documentsArray };
        document.location.href = '../JSExamples/history.html';
    }
}
