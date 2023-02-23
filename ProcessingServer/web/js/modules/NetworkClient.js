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
            method: 'GET',
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

        let response = $.ajax(query);
        //let result = JSON.parse(response);
        return response
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
        let response = this.#executeCommandWihtResult(command, commandParameters, onSuccess, onError);
        let i = 0
    }
}