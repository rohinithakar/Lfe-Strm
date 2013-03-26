package poke.db;

import eye.Comm.Register;
import poke.server.conf.ServerConf;
import poke.server.storage.jpa.UserOperation;


public class UserStorageImpl implements IUserStorage {

	private String puName;
	private UserOperation userOperation;
	
	public UserStorageImpl(ServerConf.GeneralConf server){
		this.puName = server.getProperty("pu.name");
		userOperation = new UserOperation(this.puName);
	}
	
	@Override
	public boolean register(String emailId, Register register) throws DBException {
		return this.userOperation.registerUser(emailId, register.getFname(), register.getLname(), register.getPassword());
	}

	
	@Override
	public boolean doesUserExist(String emailId) throws DBException {
		
		return false;
	}

}
