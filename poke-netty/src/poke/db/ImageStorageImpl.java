package poke.db;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import poke.server.conf.ServerConf;
import poke.server.storage.jdbc.ImageInfo;
import poke.server.storage.jdbc.ImageOperation;

import com.google.protobuf.ByteString;

import eye.Comm.Image;
import eye.Comm.UserImageReply;

public class ImageStorageImpl implements IImageStorage {

	private ImageOperation imageOperation;
	private ServerConf.GeneralConf server;
	
	public ImageStorageImpl(ServerConf.GeneralConf server){
		try {
			imageOperation = new ImageOperation(server.getProperty("db"), 
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
		return imageOperation.uploadImage(emailId, img.getTitle(), img.getActualImage().toByteArray(),
					img.getLatitude(), img.getLongitude(), this.server.getProperty("pu.name"));
	}

	@Override
	public UserImageReply retrieveImage(String emailId) throws DBException {
		List<ImageInfo> images = imageOperation.retrieveImages(emailId, this.server.getProperty("pu.name"));
		int index = 0;
		eye.Comm.UserImageReply.Builder userImageReply = UserImageReply.newBuilder();
		for(ImageInfo img : images){
			eye.Comm.Image.Builder image = Image.newBuilder();
			image.setOwneremail(emailId);
			image.setTitle(img.getImageTitle());
			
			image.setLatitude(img.getImageLat());
			image.setLongitude(img.getImageLng());
			
			image.setActualImage(ByteString.copyFrom(img.getImageBytes()));
			image.setTimestamp(Long.parseLong((new SimpleDateFormat("MMddyyhhmmss")).format(img.getImageTime())));
		
			userImageReply.addImgs(index, image.build());
			index++;
		}
		return userImageReply.build();
	}


}
