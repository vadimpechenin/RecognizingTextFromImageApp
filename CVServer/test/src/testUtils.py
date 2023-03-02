import pathlib
import unittest

from core.commonUtils import CommonUtils


class TestUtils(unittest.TestCase):
    @staticmethod
    def getTestFolder():
        solutionFolder = CommonUtils.getSolutionFolder()
        return pathlib.Path(solutionFolder).joinpath("CVServer").joinpath("test").resolve()

    @staticmethod
    def getImages(fileNames):
        images = []
        for fileName in fileNames:
            file = open(fileName, "rb")
            fileContent = file.read()
            strContent = fileContent.hex()
            images.append(strContent)
            file.close()
        return images


if __name__ == "__main__":
    unittest.main()
