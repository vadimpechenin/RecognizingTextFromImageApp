export default class CommonUtils {
    static getContainer(containerName) {
        return $('#'+containerName);
    }

    static clone(obj){
        return Object.assign({}, obj);
    }

}