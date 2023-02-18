from handlers.baseCommandHandlerParameter import BaseCommandHandlerParameter

class UserRoleParameter(BaseCommandHandlerParameter):
    def __init__(self, nameOfDatabase, nameOfTable, uuidObject):
        self.nameOfDatabase = nameOfDatabase
        self.nameOfTable = nameOfTable
        self.uuidObject = uuidObject

