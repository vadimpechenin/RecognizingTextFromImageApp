import socket
from threading import Thread

from core.clientsManager import ClientsManager
from core.clientServer.clientHandler import ClientHandler
from core.requests.requestFactory import RequestFactory


class NetworkServer(object):
    def __init__(self):
        self._socket = None
        self._continueWork = False
        self._thread = None
        self._clientsManager = ClientsManager()
        self._requestFactory = RequestFactory()

    def init(self, portNumber):
        self._socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self._socket.settimeout(0.1)
        self._socket.bind(("127.0.0.1", portNumber))
        self._socket.listen(0)

    def done(self):
        self.stop()
        if not (self._socket is None):
            self._socket.close()
            self._socket = None

    def start(self):
        if not (self._thread is None):
            self.stop()
        self._continueWork = True
        self._thread = Thread(target=self.__work)
        self._thread.start()

    def stop(self):
        if self._thread is None:
            return
        self._continueWork = False
        self._thread.join()
        self._thread = None

    def __work(self):
        while self._continueWork:
            try:
                clientSocket, clientAddress = self._socket.accept()
                client = ClientHandler(self, clientSocket)
                self.getClientsManager().Add(client)
                client.run()
            except socket.timeout:
                pass
            except Exception as e:
                print("Exception occur: " + e.__str__())
                raise

    def getClientsManager(self):
        return self._clientsManager

    def getRequestFactory(self):
        return self._requestFactory
