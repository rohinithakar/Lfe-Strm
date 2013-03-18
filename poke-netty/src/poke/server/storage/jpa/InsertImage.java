package poke.server.storage.jpa;

import java.sql.*;
/*import java.util.*;
import java.lang.*;
import org.postgis.*;
import org.postgresql.*;
import entities.Userinfo;*/

public class InsertImage  {
	private java.sql.Connection conn;;
	public  InsertImage(){
		/*
		 * Load the JDBC driver and establish a connection.
		 */
		
		try {
			Class.forName("org.postgresql.Driver");
		
		String url = "jdbc:postgresql://localhost:5432/lifestream";
		conn = DriverManager.getConnection(url,"postgres", "password");
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(byte[] imgstored,String imgname,int  userid) {
		
		try {
			
			/*
			 * Add the geometry types to the connection. Note that you must cast
			 * the connection to the pgsql-specific connection implementation
			 * before calling the addDataType() method.
			 */

			((org.postgresql.PGConnection) conn).addDataType("geometry",org.postgis.PGgeometry.class);
			((org.postgresql.PGConnection) conn).addDataType("box3d",org.postgis.PGbox3d.class);
			
			/*
			 * Create a statement and execute a select query.
			 */
			//Userbean userbean=new Userbean();
			
			
			
			Statement s = conn.createStatement();
			System.out.println("INSERT INTO images(imgid,geolocation,imgname,imgstored,imgtime,userid) VALUES (1,"+userid +",'image1',"+imgstored+",now(),st_GeomFromText('LINESTRING(0 10,0 0)',-1))");
			String query="INSERT INTO images (imgid,imgname,imgstored,imgtime,userid,geolocation) VALUES (?,?,?,now(),?,st_GeomFromText('LINESTRING(0 10,0 0)',-1))";
			PreparedStatement pstmt=conn.prepareStatement(query);
			
			PreparedStatement ps=conn.prepareStatement("SELECT max(imgid)from images");
			ResultSet r=ps.executeQuery();
			int tempimgid=1;
			if(r.next())
			{
				tempimgid=r.getInt(1)+1;
			}
			
			pstmt.setInt(1,tempimgid);
			pstmt.setString(2,imgname);
			pstmt.setBytes(3, imgstored);
			pstmt.setInt(4,userid);
			pstmt.execute();
			
			System.out.println("\nInserted...............\n");
			/*while (r.next()) {
				
				 * Retrieve the geometry as an object then cast it to the
				 * geometry type. Print things out.
				 
				PGgeometry geom = (PGgeometry) r.getObject(1);
				int id = r.getInt(2);
				System.out.println("Row " + id + ":");
				System.out.println(geom.toString());
			}*/
			s.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}