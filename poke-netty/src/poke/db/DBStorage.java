package poke.db;

import eye.Comm.Image;
import eye.Comm.Register;
import eye.Comm.UserImageReply;

public class DBStorage implements IStorage {

	@Override
	public boolean register(String emailid, Register register)
			throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean storeImage(String emailId, Image img) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserImageReply retrieveImage(String emailId) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doesUserExist(String emailId) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

}
