from core.support.UUIDClass import UUIDClass
from handlers.baseCommandHandler import BaseCommandHandler
from entity.users import Users


class GenerateUserCommandHandler(BaseCommandHandler):
    def __init__(self):
        pass

    def execute(self, parameters):
        # Запрос к базе данных на заполнение данных
        data_base = parameters.nameOfDatabase
        data_base.create_session()
        for j in range(len(parameters.name)):
            # Генерация uuid для подстановки
            ID = UUIDClass.geterateUUIDWithout_()
            parameters.uuidObject.usersIDList.append(ID)

            type_object = Users(id=ID, name=parameters.name[j], surname=parameters.surname[j],
                                     email=parameters.email[j],
                                     username=parameters.username[j], password=parameters.password[j])
            data_base.databaseAddCommit(type_object)

        #ciphers = data_base.select_all_params_in_table(parameters.nameOfTable)

        return True