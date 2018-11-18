import os
import sys
from sqlalchemy import Column, ForeignKey, Integer, String, Boolean
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import relationship
from sqlalchemy import create_engine
from votesmart import votesmart
from psycopg2 import IntegrityError
Base = declarative_base()


class Person(Base):
    __tablename__ = 'person'
    # Here we define columns for the table person
    # Notice that each column is also a normal Python instance attribute.
    id = Column(Integer, primary_key=True)
    first_name = Column(String(250), nullable=True)
    nick_name = Column(String(250), nullable=True)
    middle_name = Column(String(250), nullable=True)
    last_name = Column(String(250), nullable=False)
    suffix = Column(String(250), nullable=True)
    title = Column(String(250), nullable=True)
    party = Column(String(250), nullable=True)
    is_official = Column(Boolean, nullable=True)
    state = Column(String(20), ForeignKey('state.id'))
    district = Column(Integer, ForeignKey('district.id'))
    office =   Column(Integer, ForeignKey('office.id'))
    address = Column(Integer, ForeignKey('address.id'))


class Address(Base):
    __tablename__ = 'address'
    # Here we define columns for the table address.
    # Notice that each column is also a normal Python instance attribute.
    id = Column(Integer, primary_key=True)
    street_name = Column(String(250), nullable=False)
    street_number = Column(String(250), nullable=False)
    post_code = Column(String(250), nullable=False)

class Branch(Base):
    __tablename__ = 'branch'

    id = Column(String(250), primary_key=True)
    name = Column(String(250), nullable=False)

class District(Base):
    __tablename__ = 'district'

    id = Column(Integer, primary_key=True)
    name = Column(String(250), nullable=False)
    office = Column(Integer, ForeignKey('office.id'))
    state = Column(String(20), ForeignKey('state.id'))

class Issue(Base):
    __tablename__  = 'issue'
# Fill with npat responses for now.s
    id = Column(Integer, primary_key=True, autoincrement=True)
    topic = Column(String(200), nullable=False)

    description = Column(String(200), nullable=False)
    person = Column(Integer, ForeignKey('person.id'))
    answer = Column(String(1200), nullable=True)

class OfficeLevel(Base):
    __tablename__ = 'office_level'

    id = Column(String(50), primary_key=True)
    name = Column(String(250), nullable=False)

class OfficeType(Base):
    __tablename__ = 'office_type'

    id = Column(String(100), primary_key=True)
    name = Column(String(250), nullable=False)
    office_level_id = Column(String(200), ForeignKey('office_level.id'))
    office_branch_id = Column(String(200), ForeignKey('office_branch.id'))

class OfficeBranch(Base):
    __tablename__ = 'office_branch'

    id = Column(String(100), primary_key=True)
    name = Column(String(250), nullable=False)

class State(Base):
    __tablename__ = 'state'

    id = Column(String(20), primary_key=True)
    name = Column(String(250), nullable=False)

class Office(Base):
    __tablename__ = 'office'

    id = Column(Integer, primary_key=True)
    office_type = Column(String(50), ForeignKey('office_type.id'))
    name = Column(String(250), nullable=False)
    title = Column(String(250), nullable=False)
    short_title = Column(String(250), nullable=False)

"""class PersonIssueIntermediary(Base):
    __tablename__ = 'person_issue_intermediary'
    id = Column(Integer, primary_key=True, autoincrement=True)
    issue = Column(String(200), ForeignKey('issue.id'))
    person = Column(Integer, ForeignKey('person.id'))
"""
# Create an engine that stores data in the local directory's
# sqlalchemy_example.db file.
def db_connect():
    return create_engine('postgresql://morganseielstad@localhost:7999/voter_information')

# Create all tables in the engine. This is equivalent to "Create Table"
# statements in raw SQL.
def create_deals_table(engine):
    """"""
    Base.metadata.create_all(engine)
