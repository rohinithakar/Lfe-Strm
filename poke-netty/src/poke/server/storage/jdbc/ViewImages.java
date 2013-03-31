package poke.server.storage.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.postgis.PGgeometry;
import org.postgis.Point;

import poke.server.storage.jpa.UserOperation;

public class ViewImages  {
	private Connection conn;
	public ViewImages(String db, String dbUname, String dbPassword) throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost:5432/"+db;
		this.conn = DriverManager.getConnection(url, dbUname, dbPassword);
	}
	public List<ImageInfo> view(String emailId, String puName){
		List<ImageInfo> images = new ArrayList<ImageInfo>();
		try {
			((org.postgresql.PGConnection) conn).addDataType("geometry",org.postgis.PGgeometry.class);
			
			((org.postgresql.PGConnection) conn).addDataType("box3d",org.postgis.PGbox3d.class);
				
			/*
			 * Create a Pre.statement and execute a select imgtime for all images.
			 */
							
			UserOperation userOperation = new UserOperation(puName);
			int userId = userOperation.getUser(emailId).getUserid();
			
			String query="SELECT * from images where userid = ?";
			PreparedStatement pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,userId);
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				ImageInfo image = new ImageInfo();
				image.setImageBytes(rs.getBytes("imgstored"));
				image.setImageTime(rs.getDate("imgtime"));
				image.setImageTitle(rs.getString("imgname"));
				PGgeometry geom = (PGgeometry)rs.getObject("geolocation");
				Point p = (Point)geom.getGeometry();
				image.setImageLat(p.x);
				image.setImageLng(p.y);
				images.add(image);
			}
				
			if(images.size() > 0){
				System.out.println("\nAll Images are retrived..");
			}
				
			conn.close();
			return images;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return images;
		}
		
	}
}