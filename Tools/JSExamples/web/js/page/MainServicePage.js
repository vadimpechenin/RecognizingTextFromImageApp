//import MainServicePageController from "../modules/controllers/MainServicePageController.js";
//import Document from "../modules/entities/Document";

let documentsArrayMain = [];
class Document {
    constructor(id, userID, title) {
        this.id = id;
        this.userID = userID;
        this.title = title;
    }
}
/*function init() {
    //let controller = new MainServicePageController();

    //export { documentsArrayMain };
    //controller.init();
}

$(document).ready(init);*/
for (let i = 0; i < 3; i++) {
    documentsArrayMain[i]= new Document("2" + String(i), "1" + String(i), "Название" + String(i+1));
    console.log(documentsArrayMain[i].id)
}
export default documentsArrayMain;

