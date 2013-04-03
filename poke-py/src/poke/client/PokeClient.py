#!/usr/bin/env python

import socket, struct
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
            size = struct.pack(">L", len(message))
            socketObj.sendall(size + message )
            dataSize = socketObj.recv(4)
            size = struct.unpack(">L",dataSize)
            fulldata = ''
            receivedBytes = 0
            while( receivedBytes < size[0] ):              
                data = socketObj.recv(size[0]-receivedBytes)
                fulldata += data
                receivedBytes += len(data)
            print "Received Reply"
            resp = comm_pb2.Response()
            
            resp.ParseFromString(fulldata)
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
        try:
            fin = open(filePath, "rb")
            data = fin.read()
            fin.close()
        except IOError:
            print("Image file %s not found" % filePath)
            return None
        return data;
    
    def writeImageFile(self, fileData, filePath):
        try:
            fin = open(filePath, "wb")
            fin.write(fileData)
            fin.close()
        except IOError as e:
            print("Error writing image %s" % e)
    
    def uploadImage(self, emailid, imagePath, latitude, longitude, title ):
        img = comm_pb2.Image()
        img.actualImage = self.__readImageFile(imagePath)
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
# print pc.register("abc@abc.com","a", "b", "1234")
# print pc.login("abc@abc.com",'1234')
# pc.uploadImage("abc@abc.com", "resource/upload/warty-final-ubuntu.png", 33.323232, -121.121212, "pythonTestImage")
#resp = pc.getImages("abc@abc.com")
#for img in resp.body.imgreply.imgs:
#    pc.writeImageFile(img.actualImage, "resource/temp.png")