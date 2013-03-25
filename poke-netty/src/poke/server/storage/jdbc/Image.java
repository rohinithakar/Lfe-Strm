package poke.server.storage.jdbc;

import java.util.Date;
import org.postgresql.geometric.PGpoint;

public class Image {
	private int imageId;
	private String imageTitle;
	private byte[] imageBytes;
	private Date imageTime;
	private int imageUserId;
	private PGpoint imageLatLng;
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImageTitle() {
		return imageTitle;
	}
	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	public Date getImageTime() {
		return imageTime;
	}
	public void setImageTime(Date imageTime) {
		this.imageTime = imageTime;
	}
	public int getImageUserId() {
		return imageUserId;
	}
	public void setImageUserId(int imageUserId) {
		this.imageUserId = imageUserId;
	}
	public PGpoint getImageLatLng() {
		return imageLatLng;
	}
	public void setImageLatLng(PGpoint imageLatLng) {
		this.imageLatLng = imageLatLng;
	}
}
