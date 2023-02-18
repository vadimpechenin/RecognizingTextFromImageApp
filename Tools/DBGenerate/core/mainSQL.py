"""
Класс для работы с базой данных
"""
from sqlalchemy import create_engine
from sqlalchemy.orm.session import sessionmaker

from core.support.supportFunctions import resultproxy_to_dict, result_query_to_dict

#from base import Base
from sqlalchemy.ext.declarative import declarative_base

from config.config import DATABASE_URI

from contextlib import contextmanager
from core.base import Base



class SQLDataBase():

    def __init__(self):
        #name_of_database = 'set_of_blades'
        self.engine = create_engine(DATABASE_URI)

    def db_create(self):
        #Метод для создания таблиц и базы данных
       Base.metadata.create_all(self.engine)

    def recreate_database(self):
        Base.metadata.drop_all(self.engine)
        Base.metadata.create_all(self.engine)

    def create_session(self):
        #Создание сессии, через которую мапяться объекты
        self.session = sessionmaker(bind=self.engine)()

    def databaseAddCommit(self,type_object):
        self.session.add(type_object)
        self.session.commit()

    @contextmanager
    def session_scope(self):
        session = self.session
        try:
            yield session
            session.commit()
        except Exception:
            session.rollback()
            raise
        finally:
            session.close()

    def sessionCloseAll(self):
        session = self.session
        session.close_all()

    def select_all_params_in_table(self, name):
        # Функция для подачи запроса
        request_str = "SELECT * \
                              FROM \
                              " + str(name)
        #s = self.session.query(ParameterDescriptions)
        s = self.session.execute(request_str)
        result_of_query = resultproxy_to_dict(s)
        #result_of_query = result_query_to_dict(s)
        return result_of_query