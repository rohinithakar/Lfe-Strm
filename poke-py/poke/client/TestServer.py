#!/usr/bin/env python

import socket
import struct
from pygen import comm_pb2 

TCP_IP = '127.0.0.1'
TCP_PORT = 5005
BUFFER_SIZE = 20  # Normally 1024, but we want fast response

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)

conn, addr = s.accept()
print 'Connection address:', addr
while 1:
    data = conn.recv(4)
    print "Data: " + data
    dataSize = struct.unpack(">l", data)
    print "Size :" + str(dataSize)
    data = conn.recv(dataSize[0])
    r = comm_pb2.Request()
    r.ParseFromString(data)
    print "Email: " + r.body.emailid
    if not data: break
    print "received data:", data
    conn.send(data)  # echo
conn.close()