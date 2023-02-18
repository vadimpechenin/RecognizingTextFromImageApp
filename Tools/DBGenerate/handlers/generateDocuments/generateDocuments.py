from random import randint

from core.support.UUIDClass import UUIDClass
from handlers.baseCommandHandler import BaseCommandHandler
from entity.documetns import Documents
import core.support.supportFunctions as SA

class GenerateDocumentCommandHandler(BaseCommandHandler):
    def __init__(self):
        pass

    def execute(self, parameters):
        # Запрос к базе данных на заполнение данных
        data_base = parameters.nameOfDatabase
        data_base.create_session()
        for j in range(2):
            # Генерация uuid для подстановки
            ID = UUIDClass.geterateUUIDWithout_()
            parameters.uuidObject.documentsIDList.append(ID)
            file_binary_pdf = self.saveToByteFile(parameters.filepdf[0])
            file_binary_text = self.saveToByteFile(parameters.filetext[0])

            type_object = Documents(id=ID,
                                    userid = parameters.uuidObject.usersIDList[j],
                                    title =parameters.title[0],
                                    filepdf = file_binary_pdf,
                                    filetext = file_binary_text)
            data_base.databaseAddCommit(type_object)

        #ciphers = data_base.select_all_params_in_table(parameters.nameOfTable)

        return True

    def saveToByteFile(self, path):
        mypic = open(path, 'rb').read()
        binary = SA.SaveToBytes(mypic)
        return binary