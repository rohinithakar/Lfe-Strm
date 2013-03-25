package poke.server.storage.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ImageOperation {

	private InsertImage insertImage;
	private ViewImages viewImages;
	
	public ImageOperation(String db, String dbUname, String dbPassword) throws SQLException, ClassNotFoundException{
        insertImage = new InsertImage(db, dbUname, dbPassword);
        viewImages = new ViewImages(db, dbUname, dbPassword);
	}
	
	public boolean uploadImage(String emailId, String imageName, byte[] imageStored, double lat, double lng, String puName){
		return insertImage.insert(imageStored, imageName, emailId, lat, lng, puName);
	}
	
	public List<ImageInfo> retrieveImages(String emailId, String puName){
		return viewImages.view(emailId, puName);
	}
}
