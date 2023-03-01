from threading import Lock


class ClientsManager(object):
    def __init__(self):
        self._clientsLock = Lock()
        self._clients = []

    def Add(self, client):
        self._clientsLock.acquire()
        if not (client in self._clients):
            self._clients.append(client)
        self._clientsLock.release()

    def Remove(self, client):
        self._clientsLock.acquire()
        if client in self._clients:
            self._clients.remove(client)
        self._clientsLock.release()
