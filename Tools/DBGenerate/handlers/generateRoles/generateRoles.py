from core.support.UUIDClass import UUIDClass
from handlers.baseCommandHandler import BaseCommandHandler
from entity.roles import Roles


class GenerateRoleCommandHandler(BaseCommandHandler):
    def __init__(self):
        pass

    def execute(self, parameters):
        # Запрос к базе данных на заполнение данных
        data_base = parameters.nameOfDatabase
        data_base.create_session()
        for j in range(len(parameters.role)):
            # Генерация uuid для подстановки
            ID = UUIDClass.geterateUUIDWithout_()
            parameters.uuidObject.rolesIDList.append(ID)

            type_object = Roles(id=ID, role=parameters.role[j])
            data_base.databaseAddCommit(type_object)

        #ciphers = data_base.select_all_params_in_table(parameters.nameOfTable)

        return True