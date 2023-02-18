from handlers.baseCommandHandlerParameter import BaseCommandHandlerParameter

class DocumentParameter(BaseCommandHandlerParameter):
    def __init__(self, nameOfDatabase, nameOfTable, parameters,uuidObject):
        self.nameOfDatabase = nameOfDatabase
        self.nameOfTable = nameOfTable
        self.title = parameters.title
        self.filepdf = parameters.filepdf
        self.filetext = parameters.filetext
        self.uuidObject = uuidObject

