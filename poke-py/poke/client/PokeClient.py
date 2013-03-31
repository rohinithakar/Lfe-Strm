#!/usr/bin/env python

import socket, select
from pygen import comm_pb2
import time
import pokeMessage
import struct

TCP_IP = '127.0.0.1'
TCP_PORT = 6570
MESSAGE = "Hello, World!"

def sendRequest( message ):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((TCP_IP, TCP_PORT))
    size = struct.pack(">l", len(message))
    print "Bytes Sent: " + str(s.send(size + message ))
    # print "Bytes Sent2: " + str(s.send(message))
    dataSize = s.recv(4)
    size = struct.unpack(">l",dataSize)
    data = s.recv(size[0])
    resp = comm_pb2.Response()
    resp.ParseFromString(data)

    s.close()
    return resp

def register( emailid, fname, lname, password ):
    message = pokeMessage.register(fname, lname, emailid, password)
    resp = sendRequest(message)
    print "Reply received"
    return resp.header.reply_code
    
print register("abcabcabcabcabcabc@abcabcabcabcabc.com","a", "b", "1234")