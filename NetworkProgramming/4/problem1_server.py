import csv
import webob
from wsgiref.simple_server import make_server
from urllib.parse import parse_qs


def app(environ, start_response):
    params = parse_qs(environ['QUERY_STRING'])

    res = 'Not Exist'
    f = open('./problem1_csv.csv', 'r', encoding='utf-8', newline="")
    data = list(csv.reader(f))[1:]
    for line in data:
        if(line[0] == params.get('key')[0]):
            res = line[1]
    f.close()

    response = webob.Response(res)
    return response(environ, start_response)


if __name__ == '__main__':
    httpd = make_server('', 8000, app)
    host, port = httpd.socket.getsockname()
    print('Serving on', host, 'port', port)
    httpd.serve_forever()
