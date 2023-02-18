from config.namesOfTables import NamesOfTables

from config.entityParameters.roleParameters import RoleParameters
from config.entityParameters.userParameters import UserParameters
from config.entityParameters.documentParameters import DocumentParameters

class AllParameters():
    def __init__(self):
        self.namesOfTables = NamesOfTables()
        self.usersParameters = UserParameters()
        self.rolesParameters = RoleParameters()
        self.documentsParameters = DocumentParameters()
