"""
Создан класс Base - фабричная функция, которая создает базовый класс для декларативных определений классов
"""

from sqlalchemy import MetaData, create_engine
from sqlalchemy.ext.declarative import as_declarative
from sqlalchemy.orm import sessionmaker, scoped_session, Query, Mapper
from config.config import DATABASE_URI

def _get_query_cls(mapper, session):
    if mapper:
        m = mapper
        if isinstance(m, tuple):
            m = mapper[0]
        if isinstance(m, Mapper):
            m = m.entity

        try:
            return m.__query_cls__(mapper, session)
        except AttributeError:
            pass

    return Query(mapper, session)


Session = sessionmaker(query_cls=_get_query_cls)
engine = create_engine(DATABASE_URI)
metadata = MetaData(bind=engine)
current_session = scoped_session(Session)


@as_declarative(metadata=metadata)
class Base:
    pass