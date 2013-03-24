package poke.db;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import poke.server.conf.ServerConf;
import poke.server.storage.jpa.ImageOperation;

import com.google.protobuf.ByteString;

import eye.Comm.Image;
import eye.Comm.UserImageReply;

public class ImageStorageImpl implements IImageStorage {

	private ImageOperation imageOperation;
	private ServerConf.GeneralConf server;
	
	public ImageStorageImpl(ServerConf.GeneralConf server){
		try {
			imageOperation = new ImageOperation(server.getProperty("db.name"), 
					server.getProperty("db.uname"), server.getProperty("db.password"));
			this.server = server;
		} catch (SQLException e) {
			new DBException(" SQLException caught while creating ImageOperation object");
		} catch (ClassNotFoundException e) {
			new DBException(" ClassNotFoundException caught while creating ImageOperation object");
		}
	}
	
	@Override
	public boolean storeImage(String emailId, Image img) throws DBException {
		return imageOperation.uploadImage(emailId, img.getTitle(), img.toByteArray(),
					img.getLatitude(), img.getLongitude(), this.server.getProperty("pu.name"));
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


}
