import uuid


class UUIDClass():

    def __init__(self):

        self.usersIDList = []
        self.rolesIDList = []
        self.userRoleIDList = []
        self.documentsIDList = []

    @staticmethod
    def geterateUUIDWithout_():
        #Генерация ключа
        myuuid = uuid.uuid4()
        myuuid_str = str(myuuid)
        # Избавление от "-"
        myuuid_str_ = myuuid_str.replace('-', '')
        return myuuid_str_