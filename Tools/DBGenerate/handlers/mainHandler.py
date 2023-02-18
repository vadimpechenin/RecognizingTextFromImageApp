"""
Описывает базовый класс для заполнения всех таблиц
"""
from handlers.generateRoles.generateRoles import GenerateRoleCommandHandler
from handlers.generateUserRole.generateUserRole import GenerateUserRoleCommandHandler
from handlers.generateUsers.generateUsers import GenerateUserCommandHandler
from handlers.generateDocuments.generateDocuments import GenerateDocumentCommandHandler


class MainHandler():
    def __init__(self):
        self.dict = {}

        self.dict[0] = GenerateUserCommandHandler()
        self.dict[1] = GenerateRoleCommandHandler()
        self.dict[2] = GenerateUserRoleCommandHandler()
        self.dict[3] = GenerateDocumentCommandHandler()

    def initFunction(self,code_request, parameter):
        result = None
        if code_request in self.dict:
            handler = self.dict[code_request]
            result = handler.execute(parameter)

        return result