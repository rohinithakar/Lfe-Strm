
from pygen import comm_pb2
import time

class PokeMessage:
    __clientName = ''

    def __init__(self, clientNameParam='PythonClient'):
        self.__clientName = clientNameParam 

    def register(self, fname,lname,email,password):
        r = comm_pb2.Request()
        p = comm_pb2.Payload()
        p.__setattr__("emailid",email)
    
        reg = comm_pb2.Register()
        reg.__setattr__("fname",fname)
        reg.__setattr__("lname",lname)
        reg.__setattr__("password",password)
        p.reg.MergeFrom(reg)    
        r.body.MergeFrom(p)
    
        #Header with routing Info
        h=comm_pb2.Header()    
        h.__setattr__("originator",self.__clientName)
        h.__setattr__("time",int(time.time()))
        h.__setattr__("routing_id",int(h.__getattribute__("REGISTER")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
        r.header.MergeFrom(h)   
        return r.SerializeToString()

    def getImages(self, email):
        r = comm_pb2.Request()
        p=comm_pb2.Payload()
        p.__setattr__("emailid",email)
        r.body.MergeFrom(p)
               
        h=comm_pb2.Header()
        h.__setattr__("originator",self.__clientName)
        h.__setattr__("time",int(time.time()))
        h.__setattr__("routing_id",int(h.__getattribute__("IMGRETREIVE")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
        
        r.header.MergeFrom(h)        
        return r.SerializeToString()

    def uploadImage(self,email,img):
        r = comm_pb2.Request()
        p=comm_pb2.Payload()
        p.__setattr__("emailid",email)
        p.imageup.MergeFrom(img)
        
        r.body.MergeFrom(p)
        
        h=comm_pb2.Header()
        h.__setattr__("originator",self.__clientName)
        h.__setattr__("time",int(time.time()))
        h.__setattr__("routing_id",int(h.__getattribute__("IMGUPLOAD")))  #comm_pb2.Header.DESCRIPTOR.__getattribute__("REGISTER")
        
        r.header.MergeFrom(h)
        
        return r.SerializeToString()

    def login(self, emailid, password):
        r = comm_pb2.Request()
        p = comm_pb2.Payload()
        p.emailid = emailid
        login = comm_pb2.Login()
        login.password = password
        p.login.MergeFrom(login)
        r.body.MergeFrom(p)
        h=comm_pb2.Header()
        h.__setattr__("originator",self.__clientName)
        h.__setattr__("time",int(time.time()))
        h.__setattr__("routing_id",int(h.__getattribute__("LOGIN")))
        r.header.MergeFrom(h)
        return r.SerializeToString()
