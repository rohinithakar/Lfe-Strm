package poke.db;

import eye.Comm.Register;

public interface IUserStorage {
	
	public boolean register(String emailid, Register register ) throws DBException;
	
	public boolean doesUserExist(String emailId) throws DBException;
	
	
}
