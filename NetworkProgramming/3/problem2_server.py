

import zmq
import time
import random
import threading
import string


def str_generator(size=12, chars=string.ascii_uppercase + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))


def publish(zcontext, addr):
    zsock = zcontext.socket(zmq.PUB)
    zsock.bind(addr)
    while True:
        category = random.choice(["sport", "technology", "science"])
        article = category+":"+str_generator()
        zsock.send_string(article)
        print(article)
        time.sleep(0.5)


def start_thread(function, *args):
    thread = threading.Thread(target=function, args=args)
    thread.daemon = True  # so you can easily Ctrl-C the whole program
    thread.start()


def main(zcontext):
    addr = 'tcp://127.0.0.1:6700'
    start_thread(publish, zcontext, addr)
    time.sleep(30)


if __name__ == '__main__':
    main(zmq.Context())
