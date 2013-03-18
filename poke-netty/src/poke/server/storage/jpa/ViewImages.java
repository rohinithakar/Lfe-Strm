package poke.server.storage.jpa;

import java.sql.*;
import java.awt.Desktop;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ViewImages  {
	public void view(int userid) {
		java.sql.Connection conn;
		try {
			/*
			 * Load the JDBC driver and establish a connection.
			 */
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/lifestream";
			conn = DriverManager.getConnection(url,"postgres", "password");
			/*
			 * Add the geometry types to the connection. Note that you must cast
			 * the connection to the pgsql-specific connection implementation
			 * before calling the addDataType() method.
			 */

			((org.postgresql.PGConnection) conn).addDataType("geometry",org.postgis.PGgeometry.class);
			((org.postgresql.PGConnection) conn).addDataType("box3d",org.postgis.PGbox3d.class);
			
			/*
			 * Create a Pre.statement and execute a select imgtime for all images.
			 */
						
			String query="SELECT imgtime from images where userid = ?";
			PreparedStatement pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,userid);
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				int i=1;
				System.out.println("Image "+i+" Time: "+rs.getTimestamp(1));
				i++;
			}
			
			/*
			 * Create a Pre.statement and execute a select all images and open all.
			 */
			String query1="SELECT imgstored,imgname from images where userid = ?";
			PreparedStatement pstmt1=conn.prepareStatement(query1);
			pstmt1.setInt(1,userid);
						
			ResultSet rs1=pstmt1.executeQuery();
			while(rs1.next())
			{
				byte[] imagebyte=rs1.getBytes(1);
				String imgname=rs1.getString(2);
				
				File xfile= new File("/home/abhi/workspace/jpa_spatial/src/"+imgname);
				try {
				if(!xfile.exists())
					{
						xfile.createNewFile();
					}
				DataOutputStream dis = new DataOutputStream((new FileOutputStream(xfile)));
	            
	          	dis.write(imagebyte);
				dis.close();
				open(xfile);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

			System.out.println("\nAll Images are open..");
			System.out.println("\nEnd of view Images.java..");
			/*while (r.next()) {
				
				 * Retrieve the geometry as an object then cast it to the
				 * geometry type. Print things out.
				 
				PGgeometry geom = (PGgeometry) r.getObject(1);
				int id = r.getInt(2);
				System.out.println("Row " + id + ":");
				System.out.println(geom.toString());
			}*/
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void open(File document) throws IOException {
	    Desktop dt = Desktop.getDesktop();
	    dt.open(document);
	}
}