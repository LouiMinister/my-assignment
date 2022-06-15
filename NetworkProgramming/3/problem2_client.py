

import zmq
import time
import threading


def subscribe(zcontext, addr, category):
    zsock = zcontext.socket(zmq.SUB)
    zsock.connect(addr)
    zsock.setsockopt(zmq.SUBSCRIBE, category.encode("utf-8"))
    while True:
        msg = zsock.recv_string()
        print(msg)


def start_thread(function, *args):
    thread = threading.Thread(target=function, args=args)
    thread.daemon = True  # so you can easily Ctrl-C the whole program
    thread.start()


def main(zcontext):
    addr = 'tcp://127.0.0.1:6700'
    subscribed_category = "sport"
    start_thread(subscribe, zcontext, addr, subscribed_category)
    time.sleep(30)


if __name__ == '__main__':
    main(zmq.Context())
