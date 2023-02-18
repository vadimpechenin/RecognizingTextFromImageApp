from core.support.UUIDClass import UUIDClass
from handlers.baseCommandHandler import BaseCommandHandler
from entity.roles import Roles
from entity.userRoles import UserRoles


class GenerateUserRoleCommandHandler(BaseCommandHandler):
    def __init__(self):
        pass

    def execute(self, parameters):
        # Запрос к базе данных на заполнение данных
        data_base = parameters.nameOfDatabase
        data_base.create_session()
        for j in range(len(parameters.uuidObject.usersIDList)):
            # Генерация uuid для подстановки
            ID = UUIDClass.geterateUUIDWithout_()
            parameters.uuidObject.userRoleIDList.append(ID)
            if (j==0):
                indexRole = 0
            else:
                indexRole = 1

            type_object = UserRoles(id=ID, userid=parameters.uuidObject.usersIDList[j], roleid=parameters.uuidObject.rolesIDList[indexRole])
            data_base.databaseAddCommit(type_object)
       
        #ciphers = data_base.select_all_params_in_table(parameters.nameOfTable)

        return True