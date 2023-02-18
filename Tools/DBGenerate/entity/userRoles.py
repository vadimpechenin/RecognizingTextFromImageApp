"""
Сущность из БД - связь пользователей и ролей
"""

from sqlalchemy import Column, String
import sqlalchemy as sa


from core.base import Base
class UserRoles(Base):
    __tablename__ = 'userroles'
    id = Column(String, primary_key=True, autoincrement=False)
    userid = sa.Column(sa.String, sa.ForeignKey('users.id'), nullable=False)
    roleid = sa.Column(sa.String, sa.ForeignKey('roles.id'), nullable=False)

    def __repr__(self):
        return "<UserRoles[userID='{}', roleID='{}']>" \
            .format(self.userid, self.roleid)