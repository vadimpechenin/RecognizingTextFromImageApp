from threading import Thread

from core.requests.requestResponseBuilder import RequestResponseBuilder


class ClientHandler(object):
    RECEIVE_TIMEOUT = 30

    def __init__(self, parent, clientSocket):
        self._parent = parent
        self._socket = clientSocket
        self._thread = None

    def run(self):
        self._thread = Thread(target=self.__work)
        self._thread.start()

    def __work(self):
        self._socket.settimeout(self.RECEIVE_TIMEOUT)

        requestCode, requestBody = RequestResponseBuilder.readRequest(self._socket)
        responseBody = self._parent.getRequestFactory().handle(requestCode, requestBody)
        RequestResponseBuilder.writeResponse(self._socket, responseBody)

        self._socket.close()
        self._parent.getClientsManager().Remove(self)
