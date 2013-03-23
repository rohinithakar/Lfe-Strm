package poke.db;

import eye.Comm.Image;
import eye.Comm.Register;
import eye.Comm.UserImageReply;

public interface IStorage {
	public boolean register(String emailid, Register register ) throws DBException;
	
	public boolean doesUserExist(String emailId) throws DBException;
	
	public boolean storeImage(String emailId, Image img ) throws DBException;
	
	public UserImageReply retrieveImage( String emailId ) throws DBException;
}
