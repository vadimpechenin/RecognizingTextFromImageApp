export default class NetworkClient {
    constructor(parent) {
        this._parent = parent;
        this._serverUrl = 'handler';
        this._defaultTimeout = 20000; //миллисек
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
}