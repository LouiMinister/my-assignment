from pprint import pformat
from wsgiref.simple_server import make_server
import time


def app(environ, start_response):
    host = environ.get('HTTP_HOST', '127.0.0.1')
    path = environ.get('PATH_INFO', '/')

    print("start!!")

    if ':' in host:
        host, port = host.split(':', 1)
    if '?' in path:
        path, query = path.split('?', 1)

    print(host, port)

    headers = [('Content-Type', 'text/plain; charset=utf-8')]
    if environ['REQUEST_METHOD'] != 'GET':
        start_response('501 aa', headers)
        yield b'501'
    elif path == '/foo':
        headers = headers + [('location', '/')]
        start_response('303 aa', headers)
        yield b'303'
    elif host != '127.0.0.1' or path != '/':
        start_response('404 aa', headers)
        yield b'404'
    else:
        start_response('200 aa', headers)
        yield time.ctime().encode('ascii')


if __name__ == '__main__':
    httpd = make_server('', 8000, app)
    host, port = httpd.socket.getsockname()
    print('Serving on', host, 'port', port)
    httpd.serve_forever()
