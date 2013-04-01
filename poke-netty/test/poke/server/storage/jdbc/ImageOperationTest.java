package poke.server.storage.jdbc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Assert;

import com.google.protobuf.ByteString;

public class ImageOperationTest {

	private static ImageOperation imageOperation = null;
	
	@BeforeClass
	public static void init(){
		try {
			imageOperation = new ImageOperation("lifestream", "postgres", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private ByteString getImageByteString () throws IOException {
		 File imgPath = new File("resources/warty-final-ubuntu.png");
		 byte [] fileData = new byte[(int)imgPath.length()];
		 DataInputStream dis = new DataInputStream((new FileInputStream(imgPath)));
		 dis.readFully(fileData);
		 dis.close();
		 return ByteString.copyFrom(fileData);
	}
	
	@Test
	public void testUploadImage(){
		try {
			imageOperation.uploadImage("b@cba.com", "test.png", getImageByteString().toByteArray(), 32.335353, -121.880159, "jpa_spatial");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetrieveImage(){
		try {
			List<ImageInfo> images = imageOperation.retrieveImages("b@cba.com", "jpa_spatial");
			ImageInfo imageInfo = images.get(0);
			String assertStr = imageInfo.getImageTitle()+imageInfo.getImageLat()+imageInfo.getImageLng();
			Assert.assertEquals("Matching image title, lat and lng with image uploaded in testUploadImage",
					"test.png32.335353-121.880159", assertStr);
			Assert.assertEquals("Matching image bytes with image uploaded in testUploadImage", getImageByteString().toByteArray(), imageInfo.getImageBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
