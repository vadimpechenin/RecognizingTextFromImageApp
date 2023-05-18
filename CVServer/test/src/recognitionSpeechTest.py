import json

from baseRequestTest import BaseRequestTest
from core.commonUtils import CommonUtils
from core.requests.requestCodes import RequestCodes
from testUtils import TestUtils


class RecognitionSpeechTest(BaseRequestTest):

    def test_Request(self):
        fileNames = []
        imagesFolder = CommonUtils.getSolutionFolder().joinpath("CVServer").joinpath("test").joinpath("resources")
        fileName = str(imagesFolder.joinpath("20230515_110812.wav").resolve())
        fileNames.append(fileName)

        requestCode = RequestCodes.RecognitionSpeech
        requestParameters = RecognitionSpeechTest.getRequestParameters(fileNames)
        response = BaseRequestTest.getResponse(requestCode, requestParameters)
        self.assertTrue(response["result"])

    @staticmethod
    def getRequestParameters(fileNames) -> bytes:
        images = TestUtils.getImages(fileNames)
        parameters = {'InputDocument': images}
        request = json.dumps(parameters).encode("utf8")
        return request
