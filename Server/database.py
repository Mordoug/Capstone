from sqlalchemy.orm import sessionmaker
from models import Person, db_connect, create_deals_table
from sqlalchemy.sql import *

class LivingSocialPipeline(object):
    """Livingsocial pipeline for storing scraped items in the database"""
    def __init__(self):
        """
            Initializes database connection and sessionmaker.
            Creates deals table.
            """
        engine = db_connect()
        create_deals_table(engine)
        self.Session = sessionmaker(bind=engine)
    
    def process_item(self, item, spider):
        """Save deals in the database.
            
            This method is called for every item pipeline component.
            
            """
        session = self.Session()
        deal = Person()
        deal.name = "morgan"
        
        try:
            session.add(deal)
            session.commit()
            print("HERE")
        except:
            session.rollback()
            raise
        finally:
            session.close()
        
        return item
    
    def get_one(self):
        conn = self.Session()
        result_set = conn.query(Person).all()
        return result_set[0].name


if __name__ == "__main__":
    lsp = LivingSocialPipeline()
    lsp.process_item("item", "Spider")
    print(lsp.get_one())
