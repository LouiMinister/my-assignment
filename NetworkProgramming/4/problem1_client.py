import csv
import time
import webob
from pprint import pformat
from wsgiref.simple_server import make_server
from urllib.parse import urlparse

import requests


def client():
    while(True):
        print("write a word want to find definition! >>> ", end='')
        word = input()
        r = requests.get('http://localhost:8000',
                         params={'key': word})
        print(r.text)


if __name__ == '__main__':
    client()
