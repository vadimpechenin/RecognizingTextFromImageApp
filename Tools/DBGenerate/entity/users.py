"""
Сущность из БД - таблица пользователей
"""

from sqlalchemy import Column, String

from core.base import Base
class Users(Base):
    __tablename__ = 'users'
    id = Column(String, primary_key=True, autoincrement=False)
    name = Column(String)
    surname = Column(String)
    email = Column(String)
    username = Column(String)
    password = Column(String)

    def __repr__(self):
        return "<Users[name='{}', surname='{}', email='{}', username='{}', password='{}']>" \
            .format(self.name, self.surname, self.email, self.username, self.password)