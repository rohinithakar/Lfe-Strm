package poke.db;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;

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
		File imgPath = new File("resources/image_server"+emailId+ ".png");
		try {
			DataOutputStream dis = new DataOutputStream((new FileOutputStream(imgPath)));
			byte [] byteArray = new byte[img.getActualImage().size()];
			img.getActualImage().copyTo(byteArray, 0);
			dis.write(byteArray);
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public UserImageReply retrieveImage(String emailId) throws DBException {
		eye.Comm.Image.Builder image = Image.newBuilder();
		image.setOwneremail("abc@abc.com");
		image.setImgid("1");
		image.setLatitude(32.333333);
		image.setLongitude(121.333333);
		File imgPath = new File("resources/warty-final-ubuntu.png");
		try {
			byte [] fileData = new byte[(int)imgPath.length()];
			DataInputStream dis = new DataInputStream((new FileInputStream(imgPath)));
			dis.readFully(fileData);
			dis.close();

			image.setActualImage(ByteString.copyFrom(fileData));
		}catch(IOException e){
			image.setActualImage(null);
		}

		eye.Comm.UserImageReply.Builder userImageReply = UserImageReply.newBuilder();
		userImageReply.addImgs(0, image);
		return userImageReply.build();
	}

	@Override
	public boolean doesUserExist(String emailId) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

}
