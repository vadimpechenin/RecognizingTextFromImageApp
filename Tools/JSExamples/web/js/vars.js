const name = "bob";

//export default name;



let documentsArrayMain = [];
class Document {
    constructor(id, userID, title) {
        this.id = id;
        this.userID = userID;
        this.title = title;
    }
}
for (let i = 0; i < 3; i++) {
    documentsArrayMain[i]= new Document("2" + String(i), "1" + String(i), "Название" + String(i+1));
    //console.log(documentsArrayMain[i].id)
}

//import documentsArrayMain from "./page/MainServisePage.js";

export {documentsArrayMain, name};