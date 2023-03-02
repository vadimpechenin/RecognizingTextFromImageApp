import json

from baseRequestTest import BaseRequestTest
from core.commonUtils import CommonUtils
from core.requests.requestCodes import RequestCodes
from testUtils import TestUtils


class RecognitionTextTest(BaseRequestTest):

    def test_Request(self):
        fileNames = []
        imagesFolder = CommonUtils.getSolutionFolder().joinpath("CVServer").joinpath("test").joinpath("resources")
        fileName = str(imagesFolder.joinpath("slaids.pdf").resolve())
        fileNames.append(fileName)

        requestCode = RequestCodes.RecognitionText
        requestParameters = RecognitionTextTest.getRequestParameters(fileNames)
        response = BaseRequestTest.getResponse(requestCode, requestParameters)
        self.assertTrue(response["result"])
        #strdoc = response["text"].decode("utf8")
        #doc = json.loads(response["text"])
        #g = 0
        #self.assertTrue("РК_8_заг" == response["code"][0]["className"])

    @staticmethod
    def getRequestParameters(fileNames) -> bytes:
        images = TestUtils.getImages(fileNames)
        parameters = {'InputDocument': images}
        request = json.dumps(parameters).encode("utf8")
        return request
