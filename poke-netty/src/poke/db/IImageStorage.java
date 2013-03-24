package poke.db;

import eye.Comm.Image;
import eye.Comm.UserImageReply;

public interface IImageStorage {
	public boolean storeImage(String emailId, Image img ) throws DBException;
	public UserImageReply retrieveImage( String emailId ) throws DBException;
}
