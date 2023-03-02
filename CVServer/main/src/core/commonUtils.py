import pathlib


class CommonUtils(object):
    @staticmethod
    def getSolutionFolder():
        return pathlib.Path(__file__).parent.parent.parent.parent.parent.resolve()

    @staticmethod
    def getProjectFolder():
        solutionFolder = CommonUtils.getSolutionFolder()
        return pathlib.Path(solutionFolder).joinpath("CVServer").joinpath("main").resolve()

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
