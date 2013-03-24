package poke.server.storage.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ImageOperation {

	private InsertImage insertImage;
	
	public ImageOperation(String db, String dbUname, String dbPassword) throws SQLException, ClassNotFoundException{
        insertImage = new InsertImage(db, dbUname, dbPassword);
	}
	
	public boolean uploadImage(String emailId, String imageName, byte[] imageStored, double lat, double lng, String puName){
		return insertImage.run(imageStored, imageName, emailId, lat, lng, puName);
	}
}
