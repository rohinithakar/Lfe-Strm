#!/usr/bin/env python

import socket
from pygen import comm_pb2
import time
import pokeMessage

TCP_IP = '127.0.0.1'
TCP_PORT = 5570
BUFFER_SIZE = 1024*1024*1 # 1 MB buffer size
MESSAGE = "Hello, World!"

def sendRequest( message ):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((TCP_IP, TCP_PORT))
    s.send(message)
    data = s.recv(BUFFER_SIZE)
    s.close()
    return data

def register( emailid, fname, lname, password ):
    message = pokeMessage.register(fname, lname, emailid, password)
    response = sendRequest(message)
    resp = comm_pb2.Response()
    resp.ParseFromString(response)
    print "Reply received"
    return resp.header.reply_code
    
print register("a@abc.com","a", "a", "a")

#print "received data:", data
