from config import config
from config.allParameters import AllParameters
from core.mainSQL import SQLDataBase
from core.support.UUIDClass import UUIDClass
from handlers.generateRoles.parameterRoles import RoleParameter
from handlers.generateUserRole.parameterUserRole import UserRoleParameter
from handlers.generateUsers.parameterUsers import UserParameter
from handlers.generateDocuments.parameterDocuments import DocumentParameter
from handlers.mainHandler import MainHandler


class Bootstrap():

    @staticmethod
    def initEnviroment():
        config.AllParametersObj = AllParameters()
        # Создание объекта подключения к БД
        config.SQLDataBaseObj = SQLDataBase()
        #config.SQLDataBaseObj.db_create()
        config.MainHandlerObj = MainHandler()
        config.UUIDClassObj = UUIDClass()

    @staticmethod
    def run():
        # Создание БД
        # config.SQLDataBaseObj.db_create()
        # Работа с сессией
        config.SQLDataBaseObj.create_session()
        # Уничтожение всего что было в БД (не обязательно)
        config.SQLDataBaseObj.recreate_database()

        # Создание таблицы Users
        parameter =UserParameter(config.SQLDataBaseObj,
                                    config.AllParametersObj.namesOfTables.UsersName,
                                    config.AllParametersObj.usersParameters,
                                    config.UUIDClassObj)

        config.MainHandlerObj.initFunction(0, parameter)

        # Создание таблицы Roles
        parameter = RoleParameter(config.SQLDataBaseObj,
                                    config.AllParametersObj.namesOfTables.RolesName,
                                    config.AllParametersObj.rolesParameters,
                                    config.UUIDClassObj)

        config.MainHandlerObj.initFunction(1, parameter)

        # Создание таблицы UserRole
        parameter = UserRoleParameter(config.SQLDataBaseObj,
                                    config.AllParametersObj.namesOfTables.UserRoleName,
                                   config.UUIDClassObj)

        config.MainHandlerObj.initFunction(2, parameter)

        # Создание таблицы Documents
        parameter = DocumentParameter(config.SQLDataBaseObj,
                                      config.AllParametersObj.namesOfTables.DocumentsName,
                                      config.AllParametersObj.documentsParameters,
                                      config.UUIDClassObj)

        config.MainHandlerObj.initFunction(3, parameter)

        g=0