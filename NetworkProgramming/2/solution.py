import json
import zlib
import socket
import ssl


class Solution():

    def special_bits(self, L=1, R=2, k=1):
        num = -2
       # Write your code between start and end for solution of problem 1
        # Start
        try:
            num = -1
            for i in range(L, R+1):  # loop L to R
                bin = format(i, 'b')  # i to binary string
                cnt = bin.count('1')  # count number of 1
                if(cnt == k):  # if count is k -> minimum num of answer
                    return i   # return
        except Exception as e:  # when parameter type is not appropriate
            print("Parameter type is not appropriate")
            return
        # End
        return num  # return -1 when no number satisfies.

    def toggle_string(self, S):
        s = ""
        # Write your code between start and end for solution of problem 2
        # Start
        upper = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']
        lower = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

        try:
            for i in S:
                if (i in upper):  # if character is uppercase
                    s += lower[upper.index(i)]  # concat to lower
                elif (i in lower):  # if character is lowercase
                    s += upper[lower.index(i)]  # concat to upper
                else:  # Exception case
                    raise Exception()
        except Exception as e:  # when parameter type is not appropriate
            return "Parameter type is not appropriate"
        # End
        return s

    def send_message(self, message):
        message = self.to_json(message)
        message = self.encode(message)
        message = self.compress(message)
        return message

    def recv_message(self, message):
        message = self.decompress(message)
        message = self.decode(message)
        message = self.to_python_object(message)
        return message

    # String to byte
    def encode(self, message):
        # Write your code between start and end for solution of problem 3
        # Start
        return message.encode("UTF-8")
        # End

    # Byte to string
    def decode(self, message):
        # Write your code between start and end for solution of problem 3
        # Start
        return message.decode("UTF-8")
        # End

    # Convert from python object to json string
    def to_json(self, message):
        # Write your code between start and end for solution of problem 3
        # Start
        return json.dumps(message)
        # End

    # Convert from json string to python object
    def to_python_object(self, message):
        # Write your code between start and end for solution of problem 3
        # Start
        return json.loads(message)
        # End

    # Returns compressed message
    def compress(self, message):
        # Write your code between start and end for solution of problem 3
        # Start
        return zlib.compress(message)
        # End

    # Returns decompressed message
    def decompress(self, compressed_message):
        # Write your code between start and end for solution of problem 3
        # Start
        return zlib.decompress(compressed_message)
        # End

    def client(self, host, port, cafile=None):
        # Write your code between start and end for solution of problem 4
        # Start
        cert = ""  # Variable to store the certificate received from server
        cipher = ""  # Variable to store cipher used for connection
        msg = ""  # Variable to store message received from server

        purpose = ssl.Purpose.SERVER_AUTH
        context = ssl.create_default_context(purpose, cafile=cafile)

        raw_sock = socket.socket(
            socket.AF_INET, socket.SOCK_STREAM)  # create raw socket
        raw_sock.connect((host, port))  # connect
        ssl_sock = context.wrap_socket(
            raw_sock, server_hostname=host)  # rawsocket to ssl socket

        data = ssl_sock.recv(1024)
        msg = data.decode("UTF-8")
        cert = ssl_sock.getpeercert()
        cipher = ssl_sock.cipher()

        # End
        return cert, cipher, msg
