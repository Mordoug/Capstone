from http.server import BaseHTTPRequestHandler, HTTPServer
import json
import cgi
import re
from database import QueryManager

class Server(BaseHTTPRequestHandler):
    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def do_HEAD(self):
        self._set_headers()

    # All endpoints will be
    def do_GET(self):
        if None != re.search('/api/*', self.path):
            endpoint = self.path.split('/')[-1]
            identifier = self.path.split('/')[-2]
            if endpoint == "False":
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                self.wfile.write(bytes(json.dumps({'hello': 'world', 'received': 'ok'}),"utf-8"))
            elif endpoint == "Politicians":
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                values = QueryManager().query_people()
                self.wfile.write(bytes(json.dumps(values), "utf-8"))
            elif endpoint == "Issue" :
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                # 2606 and 5400 are good id values
                values = QueryManager().query_issue_by_politcian(identifier)
                self.wfile.write(bytes(json.dumps(values), "utf-8"))
            elif endpoint == "AllIssue":
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                values = QueryManager().query_issues()
                self.wfile.write(bytes(json.dumps(values), "utf-8"))
            elif endpoint == "Bio":
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                values = QueryManager().query_bio(identifier)
                self.wfile.write(bytes(json.dumps(values), "utf-8"))
            elif endpoint == "IssueByTopic":
                self.send_response(200)
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
                values = QueryManager().group_issues()
                self.wfile.write(bytes(json.dumps(values), "utf-8"))

            else:
                self.send_response(400, 'Bad Request: record does not exist')
                self.send_header('Content-Type', 'application/json')
                self.end_headers()
        else:
            self.send_response(403)
            self.send_header('Content-Type', 'application/json')
            self.end_headers()
        return


def run(server_class=HTTPServer, handler_class=Server, port=8008):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)

    print ('Starting httpd on port %d...' %port)
    httpd.serve_forever()

if __name__ == "__main__":
    from sys import argv

    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()
