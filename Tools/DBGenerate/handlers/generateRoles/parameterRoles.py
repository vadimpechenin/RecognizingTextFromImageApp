from handlers.baseCommandHandlerParameter import BaseCommandHandlerParameter

class RoleParameter(BaseCommandHandlerParameter):
    def __init__(self, nameOfDatabase, nameOfTable, parameters,uuidObject):
        self.nameOfDatabase = nameOfDatabase
        self.nameOfTable = nameOfTable
        self.role = parameters.role
        self.uuidObject = uuidObject

