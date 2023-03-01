import json
from typing import Optional

import pytesseract

from core.calculators.convertPdfToTextCalculator import ConvertPdfToTextCalculator
from core.optParameters import OptParameters


class RecognitionTextHandler(object):
    def __init__(self):
        self.pytesseract = pytesseract
        self.pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'

    def handle(self, packedParameters: bytearray) -> Optional[bytes]:
        if packedParameters is None or len(packedParameters) == 0:
            return None

        parametersStr = packedParameters.decode("utf8")

        parameters = json.loads(parametersStr)

        optParameters = OptParameters()
        optParameters.pytesseract = self.pytesseract
        result = ConvertPdfToTextCalculator.calculate(parameters, optParameters)
        try:
            response = json.dumps(result).encode("utf8")
        except:
            print('Error')

        return response