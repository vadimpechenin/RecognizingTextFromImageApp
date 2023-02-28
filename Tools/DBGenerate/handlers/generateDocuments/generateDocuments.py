from random import randint

from core.support.UUIDClass import UUIDClass
from handlers.baseCommandHandler import BaseCommandHandler
from entity.documents import Documents
import core.support.supportFunctions as SA

class GenerateDocumentCommandHandler(BaseCommandHandler):
    def __init__(self):
        pass

    def execute(self, parameters):
        # Запрос к базе данных на заполнение данных
        data_base = parameters.nameOfDatabase
        data_base.create_session()
        k = 0
        k1 = 0
        for j in range(4):
            # Генерация uuid для подстановки
            ID = UUIDClass.geterateUUIDWithout_()
            parameters.uuidObject.documentsIDList.append(ID)
            file_binary_pdf = self.saveToByteFile(parameters.filepdf[0])
            file_binary_text = self.saveToByteFile(parameters.filetext[0])

            type_object = Documents(id=ID,
                                    userid = parameters.uuidObject.usersIDList[k1],
                                    title =parameters.title[k],
                                    filepdf = file_binary_pdf,
                                    filetext = file_binary_text)
            data_base.databaseAddCommit(type_object)
            k+=1
            if (j==1):
                k1+=1
                k=0

        #ciphers = data_base.select_all_params_in_table(parameters.nameOfTable)

        return True

    def saveToByteFile(self, path):
        mypic = open(path, 'rb').read()
        binary = SA.SaveToBytes(mypic)
        return binary