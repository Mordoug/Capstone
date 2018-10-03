from sqlalchemy.orm import sessionmaker
from models import Person, db_connect, create_deals_table
from sqlalchemy.sql import *

class CandidateManager(object):
    """"""
    def __init__(self):
        """Initializes database connection and sessionmaker.
        """
        engine = db_connect()
        create_deals_table(engine)
        self.Session = sessionmaker(bind=engine)
    
    def insert_row(self, pol_name):
        """ Save deals in the database.
            
            This method is called for every item pipeline component.
            
        """
        session = self.Session()
        person = Person()
        person.name = pol_name
        
        try:
            session.add(person)
            session.commit()
        except:
            session.rollback()
            raise
        finally:
            session.close()
        
        return person
    
    def get_one(self):
        conn = self.Session()
        result_set = conn.query(Person).all()
        return result_set[0].name


if __name__ == "__main__":
    cm = CandidateManager()
    cm.insert_row(input("name: "))
    print(cm.get_one())
