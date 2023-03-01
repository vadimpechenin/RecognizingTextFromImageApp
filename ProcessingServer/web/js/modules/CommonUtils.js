export default class CommonUtils {
    static getContainer(containerName) {
        return $('#'+containerName);
    }

    static clone(obj){
        return Object.assign({}, obj);
    }

    static downloadDOCX(resultFile, title){
        let fileURL = URL.createObjectURL(resultFile);
        console.log(fileURL);

        let anchor = document.createElement('a');
        anchor.href = fileURL;
        anchor.download = title + '.docx';
        document.body.append(anchor);
        anchor.style = "display none";
        anchor.click();
        anchor.remove();
    }

}