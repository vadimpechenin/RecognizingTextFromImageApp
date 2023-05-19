from typing import Optional

from core.requests.recognitionTextHandler import RecognitionTextHandler
from core.requests.recognitionSpeechHandler import RecognitionSpeechHandler
from core.requests.requestCodes import RequestCodes


class RequestFactory(object):

    def __init__(self) -> None:
        self._handlers = {
            RequestCodes.RecognitionText: RecognitionTextHandler(),
            RequestCodes.RecognitionSpeech: RecognitionSpeechHandler()
        }

    def handle(self, requestCode: int, requestBody: bytearray) -> Optional[bytes]:
        if requestCode in self._handlers:
            response = self._handlers[requestCode].handle(requestBody)
        else:
            response = None
        return response
