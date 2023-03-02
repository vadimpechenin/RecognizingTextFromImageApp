import socket
from typing import Tuple, Optional


class RequestResponseBuilder(object):
    REQUEST_CODE_LENGTH = 3
    REQUEST_BODY_LENGTH = 8
    RESPONSE_BODY_LENGTH = 10

    @staticmethod
    def __recvNbytes(connection: socket, count: int) -> bytearray:
        data = bytearray()
        while 0 < count:
            try:
                part = connection.recv(count)
            except socket.timeout:
                part = None
            except Exception:
                raise
            if part:
                data.extend(part)
                count -= len(part)
            else:
                break
        return data

    @staticmethod
    def __readInt(connection: socket, numberLength: int) -> Optional[int]:
        data = RequestResponseBuilder.__recvNbytes(connection, numberLength)
        if len(data) < numberLength:
            return None
        numberStr = data.decode("utf8")
        number = int(numberStr)
        return number

    @staticmethod
    def __getRequestMask() -> str:
        mask = "{:0" + str(RequestResponseBuilder.REQUEST_CODE_LENGTH) + "d}" + \
               "{:0" + str(RequestResponseBuilder.REQUEST_BODY_LENGTH) + "d}"
        return mask

    @staticmethod
    def __getResponseMask() -> str:
        mask = "{:0" + str(RequestResponseBuilder.RESPONSE_BODY_LENGTH) + "d}"
        return mask

    @staticmethod
    def __buildRequest(requestCode: int, requestBody: bytearray) -> bytearray:
        if requestBody and 0 < len(requestBody):
            requestBodyLength = len(requestBody)
        else:
            requestBodyLength = 0
        mask = RequestResponseBuilder.__getRequestMask()
        requestHeader = mask.format(requestCode, requestBodyLength)
        request = bytearray()
        request.extend(requestHeader.encode("utf8"))
        if 0 < requestBodyLength:
            request.extend(requestBody)
        return request

    @staticmethod
    def __buildResponse(responseBody: bytearray) -> bytearray:
        if responseBody and 0 < len(responseBody):
            responseBodyLength = len(responseBody)
        else:
            responseBodyLength = 0
        mask = RequestResponseBuilder.__getResponseMask()
        responseHeader = mask.format(responseBodyLength)
        response = bytearray()
        response.extend(responseHeader.encode("utf8"))
        if 0 < responseBodyLength:
            response.extend(responseBody)
        return response

    @staticmethod
    def readRequest(connection: socket) -> Tuple[int, bytearray]:
        requestBodyLength = None
        requestBody = None

        requestCode = RequestResponseBuilder.__readInt(connection, RequestResponseBuilder.REQUEST_CODE_LENGTH)
        if not (requestCode is None):
            requestBodyLength = RequestResponseBuilder.__readInt(connection, RequestResponseBuilder.REQUEST_BODY_LENGTH)

        if not (requestBodyLength is None):
            if 0 < requestBodyLength:
                requestBody = RequestResponseBuilder.__recvNbytes(connection, requestBodyLength)
                if len(requestBody) < requestBodyLength:
                    requestBody = None
            else:
                requestBody = bytearray()

        return requestCode, requestBody

    @staticmethod
    def readResponse(connection: socket) -> bytearray:
        responseBody = None

        responseBodyLength = RequestResponseBuilder.__readInt(connection, RequestResponseBuilder.RESPONSE_BODY_LENGTH)

        if not (responseBodyLength is None):
            if 0 < responseBodyLength:
                responseBody = RequestResponseBuilder.__recvNbytes(connection, responseBodyLength)
            else:
                responseBody = bytearray

        return responseBody

    @staticmethod
    def writeRequest(connection: socket, requestCode: int, requestBody: bytearray):
        request = RequestResponseBuilder.__buildRequest(requestCode, requestBody)
        connection.sendall(request)

    @staticmethod
    def writeResponse(connection: socket, responseBody: bytearray):
        response = RequestResponseBuilder.__buildResponse(responseBody)
        connection.sendall(response)
