#!/usr/bin/env python

import socket
from pygen import comm_pb2
import time

TCP_IP = '127.0.0.1'
TCP_PORT = 5005
BUFFER_SIZE = 1024
MESSAGE = "Hello, World!"

def send( message ):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((TCP_IP, TCP_PORT))
    s.send(message)
    data = s.recv(BUFFER_SIZE)
    s.close()

def register( emailid, fname, lname, password ):
    request = comm_pb2.Request() 
    payload = comm_pb2.Payload()
    payload.emailid = emailid
    request.body.MergeFrom(payload)
    header = comm_pb2.Header()
    header.originator = 'python_client'
    header.time = int(time.time())
    #header.routing_id = comm_pb2.Header.
    request.header.MergeFrom(header)
    #header.routing_id = comm_pb2.Header.FINGER
    print request.SerializeToString()
    
register("a","a", "a", "a")

#print "received data:", data
