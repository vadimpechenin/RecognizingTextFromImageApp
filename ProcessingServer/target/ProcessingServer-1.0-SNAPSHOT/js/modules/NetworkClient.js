export default class NetworkClient {

    constructor(parent) {
        this._parent = parent;
        this._serverUrl = 'handler';
        this._defaultTimeout = 30000; //миллисек
    }

    #executeCommand(commandName, commandParameters, onSuccess, onError) {
        let query = {
            method: 'POST',
            url: this._serverUrl,
            timeout: this._defaultTimeout,
            context: this._parent,
            success: onSuccess,
            error: onError
        };

        let parameters = commandParameters;
        if (parameters instanceof FormData) {
            parameters.append("cmd", commandName);
            query.data = parameters;
            query.processData = false;
            query.contentType = false;
        } else {
            parameters.cmd = commandName;
            query.data = parameters;
        }

        $.ajax(query);
    }

    #executeCommandWihtResult(commandName, commandParameters, onSuccess, onError) {
        let query = {
            method: 'POST',
            url: this._serverUrl,
            timeout: this._defaultTimeout,
            context: this._parent,
            dataType: 'json',
            success: onSuccessResult,
            error: onError
        };

        let parameters = commandParameters;
        if (parameters instanceof FormData) {
            parameters.append("cmd", commandName);
            console.log('3 Добавлена команда');
            console.log(commandName);
            query.data = parameters;
            query.processData = false;
            query.contentType = false;
            console.log('5 Успешно добавлены параметры в запрос');
        } else {
            parameters.cmd = commandName;
            query.data = parameters;
        }
        console.log(query.data);
        let response = $.ajax(query);

        function onSuccessResult(data) {
/*            let result = null;
            if (data.documents.length>0){
                result = data.documents
            }*/
            onSuccess(data)
        }
    }



    commandLogin(username, password, onSuccess, onError) {
        let command = "SESSION_OPEN";
        let commandParameters = {"id": username.trim(), "string": md5(password.trim())};
        this.#executeCommand(command, commandParameters, onSuccess, onError);
    }

    commandLogout(onSuccess, onError) {
        let command = "SESSION_CLOSE";
        let commandParameters = {};
        this.#executeCommand(command, commandParameters, onSuccess, onError);
    }

    commandRegistration(name, surname, email, username, password, onSuccess, onError) {
        let command = "REGISTRATION_USER_INFO";
        let entity = {"name": name.trim(), "surname": surname.trim(), "email": email.trim(), "username": username.trim(), "password": md5(password.trim())}
        let commandParameters = {"type": "GetNewUserEntity", "entity": JSON.stringify(entity)};
        this.#executeCommand(command, commandParameters, onSuccess, onError);
    }

    commandHistory(onSuccess, onError) {
        let command = "GET_DOCUMENTS_HISTORY";
        let commandParameters = {};
        this.#executeCommandWihtResult(command, commandParameters, onSuccess, onError);
    }

    commandLoadDocument(documentID, onSuccess, onError) {
        let command = "GET_DOCUMENT_BY_ID";
        let commandParameters = {"id": documentID.trim()};
        this.#executeCommandWihtResult(command, commandParameters, onSuccess, onError);
    }

    commandRecognition(documentTitle, file,  onSuccess, onError) {
        let command = "RECOGNIZE_DOCUMENT";
        let commandParameters = new FormData();
        commandParameters.append("fileCount", 1);
        if (file) {
            commandParameters.append("file1", file);
            console.log('Добавлено в formData');
        }
        console.log('2 Создаем запрос с командой');
        console.log(command);
        this.#executeCommandWihtResult(command, commandParameters, onSuccess, onError);
    }

    commandRecognitionSound(documentTitle, file,  onSuccess, onError) {
        let command = "RECOGNIZE_AUDIO_DOCUMENT";
        let commandParameters = new FormData();
        commandParameters.append("fileCount", 1);
        if (file) {
            commandParameters.append("file1", file);
            console.log('Добавлено в formData');
        }
        console.log('2 Создаем запрос с командой');
        console.log(command);
        this.#executeCommandWihtResult(command, commandParameters, onSuccess, onError);
    }
    commandSave(documentTitle, file,  resultFile, onSuccess, onError) {
        let command = "SAVE_DOCUMENT";
        let commandParameters = new FormData();
        commandParameters.append("fileCount", 3);
        if (file) {
            commandParameters.append("file1", file);
        }
        if (resultFile) {
            commandParameters.append("file2", resultFile);
        }
        if (documentTitle) {
            commandParameters.append("file3", documentTitle);
        }
        this.#executeCommand(command, commandParameters, onSuccess, onError);
    }
}