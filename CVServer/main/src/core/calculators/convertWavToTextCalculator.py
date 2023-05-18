import os
import docx
from datetime import datetime

class ConvertWavToTextCalculator:

    @staticmethod
    def calculate(dictOfProject, parameters):
        #
        soundDocument = ConvertWavToTextCalculator.__getWav(dictOfProject, parameters)
        chunks = ConvertWavToTextCalculator.__getSounds(soundDocument, parameters)
        items = ConvertWavToTextCalculator.__recognitionSound(chunks, parameters)
        itemshex = ConvertWavToTextCalculator.__convertToHex(items, parameters)
        result = {'result': (items is not None), 'value': itemshex}
        return result

    @staticmethod
    def __getWav(dictOfProject, parameters):
        for imageFromList in dictOfProject['InputDocument']:
            byteFile = bytes.fromhex(imageFromList)
            fileName = str(parameters.imagesFolder.joinpath('example.wav').resolve())
            file = open(fileName, 'wb')
            file.write(byteFile)
            file.close()
            sound = parameters.AudioSegment.from_wav(fileName)

        return sound

    @staticmethod
    def __getSounds(soundDocument, parameters):
        chunks = parameters.split_on_silence(soundDocument,
                                  # experiment with this value for your target audio file
                                  min_silence_len=500,
                                  # adjust this per requirement
                                  silence_thresh=soundDocument.dBFS - 15,
                                  # keep the silence for 1 second, adjustable as well
                                  keep_silence=500,
                                  )
        return chunks

    @staticmethod
    def __recognitionSound(chunks, parameters):
        mydoc = docx.Document()
        r = parameters.sr.Recognizer()
        # process each chunk
        for i, audio_chunk in enumerate(chunks, start=1):
            # export audio chunk and save it in
            # the `folder_name` directory.
            chunk_filename = os.path.join(parameters.imagesFolder, f"chunk{i}.wav")
            audio_chunk.export(chunk_filename, format="wav")
            start_time = datetime.now()
            print(datetime.now() - start_time)
            # recognize the chunk
            with parameters.sr.AudioFile(chunk_filename) as source:
                audio_listened = r.record(source)
                # try converting it to text
                try:
                    text = r.recognize_google(audio_listened, language=parameters.language)
                except parameters.sr.UnknownValueError as e:
                    print("Error:", str(e))
                else:
                    text = f"{text.capitalize()}. "
                    print(" -- :", text)
                    mydoc.add_paragraph(text)
            print("Время выполнения: ")
            print(datetime.now() - start_time)
        return mydoc

    @staticmethod
    def __convertToHex(items, parameters):
        fileName = str(parameters.imagesFolder.joinpath('exampleSound.docx').resolve())
        fileNameWav = str(parameters.imagesFolder.joinpath('example.wav').resolve())
        items.save(fileName)

        file = open(fileName, "rb")
        fileContent = file.read()
        strContent = fileContent.hex()
        file.close()
        os.remove(fileNameWav)
        return strContent
