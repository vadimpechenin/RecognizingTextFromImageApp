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

    //Bytest to bytes for download document
    static BytesToViewBytes(recognitionFileB) {
        let bytes = new Uint8Array(recognitionFileB.length);

        for (let i = 0; i < bytes.length; i++) {
            bytes[i] = recognitionFileB[i];
        }
        return bytes
    }

    // Convert a hex string to a byte array
    static hexToBytes(hex) {
        let bytes = [];
        for (let c = 0; c < hex.length; c += 2)
            bytes.push(parseInt(hex.substr(c, 2), 16));
        return bytes;
    }

    // Convert a byte array to a hex string
    static bytesToHex(bytes) {
        let hex = [];
        for (let i = 0; i < bytes.length; i++) {
            let current = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
            hex.push((current >>> 4).toString(16));
            hex.push((current & 0xF).toString(16));
        }
        return hex.join("");
    }
}