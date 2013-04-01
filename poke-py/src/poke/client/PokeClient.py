#!/usr/bin/env python

import socket, struct, io
import PokeMessage
from pygen import comm_pb2

IP = '127.0.0.1'
TCP_PORT = 6571

class PokeClient:
    __hostname = None
    __port = None
    __pm = None
    
    def __init__(self, hostname, port):
        self.__hostname = hostname
        self.__port = port
        self.__pm = PokeMessage.PokeMessage('PythonClient')

    def __sendRequest( self, message ):
        resp = None
        try:
            socketObj = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            socketObj.connect((self.__hostname, self.__port))
            size = struct.pack(">l", len(message))
            socketObj.send(size + message )
            dataSize = socketObj.recv(4)
            size = struct.unpack(">l",dataSize)
            data = socketObj.recv(size[0])
            print "Received Reply"
            resp = comm_pb2.Response()
            
            resp.ParseFromString(data)
        except IOError as e:
            print "I/O error({0}): {1}".format(e.errno, e.strerror)
        except struct.error:
            print "Error in reading response"
            resp = None
        finally:
            socketObj.close()
        return resp

    def register( self, emailid, fname, lname, password ):
        message = self.__pm.register(fname, lname, emailid, password)
        resp = self.__sendRequest(message)
        if resp == None:
            return None
        return resp.header.reply_code == 1

    def login(self, emailid, password):
        message = self.__pm.login(emailid, password)
        resp = self.__sendRequest(message)
        if resp == None:
            return None
        return resp.header.reply_code == 1
    
    def getImages(self, emailid):
        message = self.__pm.getImages(emailid)
        resp = self.__sendRequest(message)
        if resp == None:
            return None
        return resp
    
    def __readImageFile(self, filePath):
        with io.open(filePath, 'rb') as fileObj:
            data = fileObj.readAll()
        return data
    
    def __readImageFile2(self, filePath):
        try:
            fin = open(filePath, "rb")
            data = fin.read()
            fin.close()
        except IOError:
            print("Image file %s not found" % filePath)
        return data;
    
    def uploadImage(self, emailid, imagePath, latitude, longitude, title ):
        img = comm_pb2.Image()
        img.actualImage = self.__readImageFile2(imagePath)
        img.latitude = latitude
        img.longitude = longitude
        img.title = title
        img.owneremail = emailid
        message = self.__pm.uploadImage(emailid, img)
        resp = self.__sendRequest(message)
        if resp == None:
            return None
        return resp
    
pc = PokeClient(IP,TCP_PORT)
print pc.register("abc@abc.com","a", "b", "1234")
print pc.login("abc@abc.com",'1234')
print pc.uploadImage("abc@abc.com", "resource/upload/warty-final-ubuntu.png", 33.323232, -121.121212, "pythonTestImage")