"""
Сущность из БД - таблица ролей
"""

from sqlalchemy import Column, String

from core.base import Base
class Roles(Base):
    __tablename__ = 'roles'
    id = Column(String, primary_key=True, autoincrement=False)
    role = Column(String)

    def __repr__(self):
        return "<Roles[role='{}']>" \
            .format(self.role)