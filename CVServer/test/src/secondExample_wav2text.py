import os
import speech_recognition as sr
import docx
from datetime import datetime

from pydub import AudioSegment
from pydub.silence import split_on_silence
#Необходимо сначала сконвертировать waw файл

def wav2text(sound, r, mydoc,folder_name, language="ru-RU"):
    # split audio sound where silence is 700 miliseconds or more and get chunks
    chunks = split_on_silence(sound,
                              # experiment with this value for your target audio file
                              min_silence_len=500,
                              # adjust this per requirement
                              silence_thresh=sound.dBFS - 15,
                              # keep the silence for 1 second, adjustable as well
                              keep_silence=500,
                              )
    # process each chunk
    for i, audio_chunk in enumerate(chunks, start=1):
        # export audio chunk and save it in
        # the `folder_name` directory.
        chunk_filename = os.path.join(folder_name, f"chunk{i}.wav")
        audio_chunk.export(chunk_filename, format="wav")
        start_time = datetime.now()
        print(datetime.now() - start_time)
        # recognize the chunk
        with sr.AudioFile(chunk_filename) as source:
            audio_listened = r.record(source)
            # try converting it to text
            try:
                text = r.recognize_google(audio_listened, language=language)
            except sr.UnknownValueError as e:
                print("Error:", str(e))
            else:
                text = f"{text.capitalize()}. "
                print(" -- :", text)
                mydoc.add_paragraph(text)
        mydoc.save("doc\\speech_EK_NTS.docx")
        print("Время выполнения: ")
        print(datetime.now() - start_time)

folder_name = "D:\\PYTHON\\Programms\\audio"
if (1==0):
	file_name = "speech.waw"
else:
	#file_name = "speech_BOL.wav"
    file_name = "speech_EK_NTS.wav"
AUDIO_FILE  = os.path.join(folder_name,file_name)
# open the audio file using pydub
sound = AudioSegment.from_wav(AUDIO_FILE)
#TODO почему то не работает импорт m4a, хотя должен
#AUDIO_FILE  = os.path.join(folder_name,'speech.m4a')
#sound = AudioSegment.from_file(AUDIO_FILE, format='m4a')

mydoc = docx.Document()
# create a speech recognition object
r = sr.Recognizer()
wav2text(sound, r, mydoc, folder_name, "ru-RU")
