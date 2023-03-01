import unittest
from core.commonUtils import CommonUtils


class CommonUtilsTest(unittest.TestCase):
    def test_GetFolders(self):
        solutionFolder = CommonUtils.getSolutionFolder()
        self.assertNotEqual('', solutionFolder)

        projectFolder = CommonUtils.getProjectFolder()
        self.assertNotEqual('', projectFolder)


if __name__ == "__main__":
    unittest.main()
