package poke.db;

import eye.Comm.Login;
import eye.Comm.Register;

public interface IUserStorage {
	
	public boolean register(String emailid, Register register ) throws DBException;
	
	public boolean login(String emailId, Login login) throws DBException;
	
	
}
