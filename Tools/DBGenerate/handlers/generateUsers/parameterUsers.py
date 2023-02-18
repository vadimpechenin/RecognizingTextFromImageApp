from handlers.baseCommandHandlerParameter import BaseCommandHandlerParameter

class UserParameter(BaseCommandHandlerParameter):
    def __init__(self, nameOfDatabase, nameOfTable, parameters, uuidObject):
        self.nameOfDatabase = nameOfDatabase
        self.nameOfTable = nameOfTable
        self.name = parameters.name
        self.surname = parameters.surname
        self.email = parameters.email
        self.username = parameters.username
        self.password = parameters.password
        self.uuidObject = uuidObject
