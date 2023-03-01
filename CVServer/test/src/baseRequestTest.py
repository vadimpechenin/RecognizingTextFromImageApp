import socket
import unittest
import json

from core.clientServer.clientHandler import ClientHandler
from core.clientServer.networkServer import NetworkServer
from core.requests.requestResponseBuilder import RequestResponseBuilder


class BaseRequestTest(unittest.TestCase):
    SERVER_PORT = 10000

    def setUp(self):
        self._server = NetworkServer()
        self._server.init(self.SERVER_PORT)
        self._server.start()

    def tearDown(self):
        self._server.stop()
        self._server.done()

    @staticmethod
    def sendRequest(requestCode: int, requestParameters: bytearray) -> bytearray:
        connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        connection.connect(("127.0.0.1", BaseRequestTest.SERVER_PORT))
        connection.settimeout(ClientHandler.RECEIVE_TIMEOUT)
        RequestResponseBuilder.writeRequest(connection, requestCode, requestParameters)
        response = RequestResponseBuilder.readResponse(connection)
        connection.close()
        return response

    @staticmethod
    def getResponse(requestCode, requestParameters):
        packedResponse = BaseRequestTest.sendRequest(requestCode, bytearray(requestParameters))
        if not (packedResponse is None) and 0 < len(packedResponse):
            responseStr = packedResponse.decode("utf8")
        else:
            responseStr = ""
        response = json.loads(responseStr)
        return response
