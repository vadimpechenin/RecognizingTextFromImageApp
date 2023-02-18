"""
Сущность из БД - таблица ролей
"""

from sqlalchemy import Column, String
import sqlalchemy as sa
from core.base import Base
class Documents(Base):
    __tablename__ = 'documetns'
    id = Column(String, primary_key=True, autoincrement=False)
    userid = sa.Column(sa.String, sa.ForeignKey('users.id'))
    title = Column(String)
    filepdf = sa.Column(sa.LargeBinary)
    filetext = sa.Column(sa.LargeBinary)


    def __repr__(self):
        return "<Documents[userid='{}', title='{}', filepdf='{}', filetext='{}']>" \
            .format(self.role, self.title, self.filepdf, self.filetext)