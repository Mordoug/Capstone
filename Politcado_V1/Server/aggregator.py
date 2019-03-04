from config import API_KEY_VOTESMART
from votesmart import votesmart
import json
from models import State

votesmart.apikey = API_KEY_VOTESMART

#addr = votesmart.address.getOffice(26732)[0]
#print (addr.street, addr.city, addr.state)

def get_office_branch_types():
    return votesmart.office.getBranches()

def get_office_levels():
    return votesmart.office.getLevels()

def get_official_list(state_id):
    return votesmart.officials.getStatewide(state_id)

def get_candidate_list(state_id):
    return votesmart.candidate.getStatewide(state_id)

def get_office_type():
    return votesmart.office.getTyoes()
def get_state_list():
    states = votesmart.state.getStateIDs()
    return states
def get_bio(id):
    bio = votesmart.candidatebio.getBio(id)
    return bio
def dump_object(object, file_name):
    with open(file_name + ".json", 'w') as outfile:
        outfile.write(json.dumps(str(object)))

if __name__ == "__main__":
    #vermont_candidates = votesmart.officials.getStatewide('NY')
    #dump_object(vermont_candidates, "JSONTXT/ny_candidates")
    #print(vermont_candidates)
    #print(votesmart.office.getLevels())
    dump_object(get_bio(26976), "JSONTXT/bio")
