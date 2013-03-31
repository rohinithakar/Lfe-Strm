
import sys
from pygen import comm_pb2
from datetime import datetime
import time


def register(fname,lname,email,password):
    r = comm_pb2.Request()
    p=comm_pb2.Payload()
    p.__setattr__("emailid",email)
    
    reg=comm_pb2.Register()
    reg.__setattr__("fname",fname)
    reg.__setattr__("lname",lname)
    reg.__setattr__("password",password)
    
    p.DESCRIPTOR.__setattr__("reg", reg)
   
    
    r.body.MergeFrom(p)
    
    #Header with routing Info
    h=comm_pb2.Header()
    
    h.__setattr__("originator","originator")
    h.__setattr__("time",int(time.time()))
    h.__setattr__("routing_id",int(h.__getattribute__("REGISTER")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
    r.header.MergeFrom(h)
   
    return r.SerializeToString()

def getImages(email):
    r = comm_pb2.Request()
    p=comm_pb2.Payload()
    p.__setattr__("emailid",email)
    r.body.MergeFrom(p)
   
    h=comm_pb2.Header()
    h.__setattr__("originator","originator")
    h.__setattr__("time",int(time.time()))
    h.__setattr__("routing_id",int(h.__getattribute__("REGISTER")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
    
    r.header.MergeFrom(h)
    
    return r.SerializeToString()

def uploadImage(email,img):
    r = comm_pb2.Request()
    p=comm_pb2.Payload()
    p.__setattr__("emailid",email)
    p.__setattr__("imageup",comm_pb2.Image(img))
    
    r.body.MergeFrom(p)
    
    h=comm_pb2.Header()
    h.__setattr__("originator","originator")
    h.__setattr__("time",int(time.time()))
    h.__setattr__("routing_id",int(h.__getattribute__("REGISTER")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
    
    r.header.MergeFrom(h)
    
    return r.SerializeToString()

    