import json
from typing import Optional

import speech_recognition as sr


from core.calculators.convertWavToTextCalculator import ConvertWavToTextCalculator

from core.optParameters import OptParameters
from core.commonUtils import CommonUtils

from pydub import AudioSegment
from pydub.silence import split_on_silence


class RecognitionSpeechHandler(object):
    def __init__(self):
        self.sr = sr
        self.AudioSegment = AudioSegment
        self.split_on_silence = split_on_silence

    def handle(self, packedParameters: bytearray) -> Optional[bytes]:
        if packedParameters is None or len(packedParameters) == 0:
            return None

        parametersStr = packedParameters.decode("utf8")

        parameters = json.loads(parametersStr)

        optParameters = OptParameters()
        optParameters.AudioSegment = self.AudioSegment
        optParameters.split_on_silence = self.split_on_silence
        optParameters.sr = self.sr
        optParameters.imagesFolder = CommonUtils.getSolutionFolder().joinpath("CVServer").joinpath("main").joinpath("data").joinpath("sound")

        result = ConvertWavToTextCalculator.calculate(parameters, optParameters)
        try:
            response = json.dumps(result).encode("utf8")
        except:
            print('Error')

        return response