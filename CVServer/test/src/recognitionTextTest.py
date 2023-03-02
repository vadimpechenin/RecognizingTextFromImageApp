import json

from baseRequestTest import BaseRequestTest
from core.commonUtils import CommonUtils
from core.requests.requestCodes import RequestCodes
from testUtils import TestUtils


class RecognitionTextTest(BaseRequestTest):

    def test_Request(self):
        fileNames = []
        imagesFolder = CommonUtils.getSolutionFolder().joinpath("data")
        fileName = str(imagesFolder.joinpath("partIdentification").joinpath("IMG_09_15_2022_04_15_09_AM.jpg").resolve())
        fileNames.append(fileName)

        requestCode = RequestCodes.QualityIdentificationImageNN
        requestParameters = RecognitionTextTest.getRequestParameters(fileNames)
        response = BaseRequestTest.getResponse(requestCode, requestParameters)
        self.assertTrue(response["result"])
        self.assertTrue("РК_8_заг" == response["code"][0]["className"])

    @staticmethod
    def getRequestParameters(fileNames) -> bytes:
        images = TestUtils.getImages(fileNames)
        parameters = {'InputDocument': images}
        request = json.dumps(parameters).encode("utf8")
        return request
