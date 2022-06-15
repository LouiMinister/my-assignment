#!/usr/bin/env python3
# Foundations of Python Network Programming, Third Edition
# https://github.com/brandon-rhodes/fopnp/blob/m/py3/chapter07/srv_asyncio1.py
# Asynchronous I/O inside "asyncio" callback methods.

import asyncio
import argparse
from random import randrange


class ZenServer(asyncio.Protocol):
    status = "READY"
    num = 0
    life = 0

    def connection_made(self, transport):
        self.transport = transport
        self.address = transport.get_extra_info('peername')
        print('Accepted connection from {}'.format(self.address))

    def announce(self, msg):
        self.transport.write(msg.encode('utf-8'))
        print(msg)

    def close(self):
        self.announce("close")
        self.transport.close()

    def startGame(self):
        self.status = "PLAYING"
        self.life = 5
        self.num = randrange(1, 11)
        self.announce("start game. guess a number between 1 to 10")

    def endGame(self):
        self.status = "READY"
        self.announce("game end")

    def decideNum(self, num):
        if(self.status != "PLAYING"):
            self.announce("Please start game first")
            return
        self.life -= 1
        if(self.num == num):
            self.announce("Congratulations you did it.")
            self.status = "READY"
        elif(self.num > num):
            if(self.life == 0):
                self.endGame()
            else:
                self.announce("You Guessed too small!")
        elif(self.num < num):
            if(self.life == 0):
                self.endGame()
            else:
                self.announce("You Guessed too high!")

    def data_received(self, data):
        data = data.decode('utf-8')
        if data == "start":
            self.startGame()
        elif data == "end":
            self.endGame()
        elif data == "close":
            self.close()
        elif data.isdigit():
            self.decideNum(int(data))
        else:
            self.announce("unknown command")

    def connection_lost(self, exc):
        if exc:
            print('Client {} error: {}'.format(self.address, exc))
        else:
            print('Client {} closed socket'.format(self.address))


def parse_command_line(description):
    """Parse command line and return a socket address."""
    parser = argparse.ArgumentParser(description=description)
    parser.add_argument('host', help='IP or hostname')
    parser.add_argument('-p', metavar='port', type=int, default=1060,
                        help='TCP port (default 1060)')
    args = parser.parse_args()
    address = (args.host, args.p)
    return address


if __name__ == '__main__':
    address = parse_command_line('asyncio server using callbacks')
    loop = asyncio.get_event_loop()
    coro = loop.create_server(ZenServer, *address)
    server = loop.run_until_complete(coro)
    print('Listening at {}'.format(address))
    try:
        loop.run_forever()
    finally:
        server.close()
        loop.close()
