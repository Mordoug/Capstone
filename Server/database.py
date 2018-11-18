from sqlalchemy.orm import sessionmaker
from models import *
from sqlalchemy.sql import *
from votesmart import votesmart
from votesmart import VotesmartApiError
from config import API_KEY_VOTESMART
import json
class QueryManager():
    def __init__(self):
        engine = db_connect()
        create_deals_table(engine)
        self.Session = sessionmaker(bind=engine)

    def query_people(self):
        session = self.Session()
        q = session.query(Person).all()
        row_dict = {}
        table_list = []
        print (len(q))
        for row in q:
            row_dict = {}
            row_dict["id"] = row.id
            row_dict["firstName"] = row.first_name
            row_dict["lastName"] = row.last_name
            #row_dict["party"] = row.party
            row_dict["state"] = row.state
            row_dict["office"] = session.query(Office).filter(Office.id==row.office).first().name
            table_list.append(row_dict)

        return table_list

class DatabaseFiller(object):
    """"""
    def __init__(self):
        """Initializes database connection and sessionmaker.
        """
        engine = db_connect()
        create_deals_table(engine)
        self.Session = sessionmaker(bind=engine)
        votesmart.apikey = API_KEY_VOTESMART


    def insert_states(self):
        """ Save deals in the database.

            This method is called for every item pipeline component.

        """
        session = self.Session()
        states = votesmart.state.getStateIDs()


        try:
            for st in states:
                session.add(State(id=st.stateId, name=st.name))
            session.commit()
        except:
            session.rollback()
            raise
        finally:
            session.close()

    def insert_office_type(self):
        """ Save deals in the database.

        This method is called for every item pipeline component.

        """
        session = self.Session()
        office_type = votesmart.office.getTypes()

        try:
            for ot in office_type:
                session.add(OfficeType(id=ot.officeTypeId, name=ot.name, office_level_id=ot.officeLevelId, office_branch_id=ot.officeBranchId))
            session.commit()
        except:
            session.rollback()
            raise
        finally:
            session.close()

    def insert_office_level(self):
        """ Save deals in the database.

        This method is called for every item pipeline component.

        """
        session = self.Session()
        office_level = votesmart.office.getLevels()
        try:
            for ot in office_level:
                session.add(OfficeLevel(id=ot.officeLevelId, name=ot.name))
                session.commit()
        except:
            session.rollback()
            raise
        finally:
            session.close()


    def insert_office_branch(self):
        """ Save deals in the database.

            This method is called for every item pipeline component.

        """
        session = self.Session()
        office_branch = votesmart.office.getBranches()

        try:
            for ob in office_branch:
                session.add(OfficeBranch(id=ob.officeBranchId, name=ob.name))
                session.commit()
        except:
            session.rollback()
            raise
        finally:
                session.close()

    def insert_offices(self):
        """ Save deals in the database.

        This method is called for every item pipeline component.
        offices.office*.officeId
        offices.office*.officeTypeId
        offices.office*.officeLevelId
        offices.office*.officeBranchId
        offices.office*.name
        offices.office*.title
        offices.office*.shortTitle
        """
        session = self.Session()
        q_session = self.Session()

        office_types = q_session.query(OfficeType).all()
        try:
            for type in office_types:
                office = votesmart.office.getOfficesByType(type.id)
                for o in office:
                    session.add(Office(id=o.officeId, name=o.name, office_type=o.officeTypeId, title=o.title, short_title=o.shortTitle))
                    session.commit()
        except:
            session.rollback()
            raise
        finally:
            session.close()


    def insert_district(self, state):
        """ Save deals in the database.

            This method is called for every item pipeline component.
            districtList.district*.districtId
            districtList.district*.name
            districtList.district*.officeId
            districtList.district*.stateId
        """
        session = self.Session()
        q_session = self.Session()

        office = q_session.query(Office).all()
        try:
            for o in office:
                try:
                    district = votesmart.district.getByOfficeState(o.id, state)

                    for d in district:
                        session.merge(District(id=d.districtId, name=d.name, office=d.officeId, state=d.stateId))
                        session.commit()
                except:
                    pass
        except:
            session.rollback()
            raise
        finally:
                session.close()

    def insert_officials(self, state):
        """ Save deals in the database.

        candidateList.candidate*.candidateId
        candidateList.candidate*.firstName
        candidateList.candidate*.nickName
        candidateList.candidate*.middleName
        candidateList.candidate*.lastName
        candidateList.candidate*.suffix
        candidateList.candidate*.title
        candidateList.candidate*.electionParties
        candidateList.candidate*.officeParties
        candidateList.candidate*.officeStatus
        candidateList.candidate*.officeDistrictId
        candidateList.candidate*.officeDistrictName
        candidateList.candidate*.officeTypeId
        candidateList.candidate*.officeId
        candidateList.candidate*.officeName
        candidateList.candidate*.officeStateId

        """
        session = self.Session()
        officials = votesmart.officials.getStatewide(state)
        print(officials)
        try:
            for o in officials:
                try:
                    session.add(Person(id=o.candidateId, first_name=o.firstName, nick_name=o.nickName,
                                last_name=o.lastName, middle_name=o.middleName, suffix=o.suffix, title=o.title,
                                party=o.electionParties, state=o.officeStateId, district=o.officeDistrictId,
                                office=o.officeId, is_official=True))
                    session.commit()
                except:
                    pass
        except:
            session.rollback()
            raise
        finally:
            session.close()

    def insert_candidates(self, state):
        """ Save deals in the database.
        officeId*, stateId(default: 'NA'), electionYear(default: >= current year), stageId
        candidateList.candidate*.candidateId
        candidateList.candidate*.firstName
        candidateList.candidate*.nickName
        candidateList.candidate*.middleName
        candidateList.candidate*.preferredName
        candidateList.candidate*.lastName
        candidateList.candidate*.suffix
        candidateList.candidate*.title
        candidateList.candidate*.ballotName
        candidateList.candidate*.electionParties
        candidateList.candidate*.electionStatus
        candidateList.candidate*.electionStage
        candidateList.candidate*.electionDistrictId
        candidateList.candidate*.electionDistrictName
        candidateList.candidate*.electionOffice
        candidateList.candidate*.electionOfficeId
        candidateList.candidate*.electionStateId
        candidateList.candidate*.electionOfficeTypeId
        candidateList.candidate*.electionYear
        candidateList.candidate*.electionSpecial
        candidateList.candidate*.electionDate
        candidateList.candidate*.officeParties
        candidateList.candidate*.officeStatus
        candidateList.candidate*.officeDistrictId
        candidateList.candidate*.officeDistrictName
        candidateList.candidate*.officeStateId
        candidateList.candidate*.officeId
        candidateList.candidate*.officeName
        candidateList.candidate*.officeTypeId
        candidateList.candidate*.runningMateId
        candidateList.candidate*.runningMateName

        """
        session = self.Session()
        q_session = self.Session()

        office = q_session.query(Office).all()

        try:
            for of in office:
                try:
                    officials = votesmart.candidates.getByOfficeState(of.id, state)
                    for o in officials:

                        session.add(Person(id=o.candidateId, first_name=o.firstName, nick_name=o.nickName,
                                    last_name=o.lastName, middle_name=o.middleName, suffix=o.suffix, title=o.title,
                                    party=o.electionParties, office=o.electionOfficeId, district=o.electionDistrictId, state=o.electionStateId,
                                    is_official=False))
                        session.commit()
                except:
                    pass

        except:
            session.rollback()
            raise
        finally:
            session.close()

    def insert_issues(self):

        session = self.Session()
        q_session = self.Session()
        index = 0
        people = q_session.query(Person).all()

        #dump_object(npat_list, "npat_formatted3")
        try:
            for person in people:
                try:
                    npat = votesmart.npat.getNpat(person.id)["section"]
                    npat_list = deal_with_data(npat)
                    for item in npat_list:
                        for row in npat_list[item]:
                            if row["optionText"] is "X":
                                session.add(Issue(topic=item, description=row['rowText'],
                                                  person=person.id, answer=row['answerText']))
                                session.commit()
                except KeyError:
                    pass





        except:
            session.rollback()
            raise
        finally:
            session.close()


def dump_object(object, file_name):
    with open(file_name + ".json", 'w') as outfile:
        outfile.write(json.dumps(str(object)))

def deal_with_data(data):
    topics = []
    row = []
    result = {}

    new_data = data
    for ele in data:
        end = False
        if isinstance(ele, dict):
            topics.append(ele["name"])
            new_data = data[(len(topics) - 1)]['row']

            #print (new_data['row'])

            while end is False:
                try:
                    #try:
                    new_data = new_data["row"]
                    for ele in range(len(new_data)):
                        element = {}
                        #print(new_data[ele]['rowText'])
                        element["rowText"] = new_data[ele]["rowText"]
                        element["optionText"] = new_data[ele]["optionText"]
                        element["answerText"] = new_data[ele]["answerText"]

                        row.append(element)

                except KeyError:
                    #print("KEY")
                    result[topics[-1]] = row
                    #print (result)
                    row = []
                    end = True
                except TypeError:
                    #print("TYPE")
                    result[topics[-1]] = row
                    row = []
                    end = True


    return result
    #return





if __name__ == "__main__":
    df = DatabaseFiller()
    #df.insert_states()
    #df.insert_office_branch()
    #df.insert_office_level()
    #df.insert_office_type()
    #df.insert_offices()
    #df.insert_district('VT')
    #df.insert_officials('VT')
    #df.insert_candidates('VT')
    df.insert_issues()
