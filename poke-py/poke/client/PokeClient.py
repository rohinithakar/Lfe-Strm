#!/usr/bin/env python

import socket, select
from pygen import comm_pb2
import time
import pokeMessage

TCP_IP = 'localhost'
TCP_PORT = 6570
BUFFER_SIZE = 65535 # 1 MB buffer size
MESSAGE = "Hello, World!"

def sendRequest( message ):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((TCP_IP, TCP_PORT))
    s.sendall(message)
    res = ""
    data = s.recv(1024)
    while len(data):
        res = res + data
        data = s.recv(1024)
    s.close()
    return res

def register( emailid, fname, lname, password ):
    message = pokeMessage.register(fname, lname, emailid, password)
    response = sendRequest(message)
    resp = comm_pb2.Response()
    resp.ParseFromString(response)
    print "Reply received"
    return resp.header.reply_code
    
print register("a@abc.com","a", "a", "a")

#print "received data:", data
