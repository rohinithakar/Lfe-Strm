package poke.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.protobuf.ByteString;

import eye.Comm.Image;
import eye.Comm.UserImageReply;

import poke.client.PokeClient;
import poke.client.PokeClient.ImageRetrieveCallback;
import poke.client.PokeClient.ImageUploadCallback;
import poke.client.PokeClient.RegisterCallback;

public class SimpleClient implements ImageUploadCallback, RegisterCallback, ImageRetrieveCallback{
	
	PokeClient client = null;
	
	SimpleClient() {
		client = new PokeClient("localhost", 5571, "SimpleClient");
		client.start();
	}
	
	public void uploadImage() throws IOException {
		ByteString bs = getImageByteString();
		client.setImageUploadCallback(this);
		eye.Comm.Image.Builder image = Image.newBuilder();
		image.setActualImage(bs);
		image.setOwneremail("abc@abc.com");
		image.setLatitude(-121.333333);
		image.setLongitude(33.323232);
		image.setTitle("test.png");
		client.uploadImage("abc@abc.com", image);
	}
	
	public void register() throws InterruptedException {
		client.setRegistrationCallback(this);
		//client.register("a", "b", "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc@abc.com", "1234");
		client.register("a", "b", "abc@abc.com", "1234");
	}
	
	public void retrieveImage() throws IOException {
		
		client.setImageRetrieveCallback(this);
		
		client.getImages("abc@abc.com");
	}

	@Override
	public void imageUploadReply(boolean uploaded) {
		try {
			System.out.println("Image Upload Response Received: " + uploaded);
			client.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
	
	public static void main(String args[]) throws Exception {
		SimpleClient client = new SimpleClient();
		client.register();
	}

	@Override
	public void registered(boolean registrationSuccess) {
		try {
			System.out.println("Registration Response Received: " + registrationSuccess);
			client.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void imageReply(UserImageReply images) {
		List<Image> imgList = images.getImgsList();
		for(Image img : imgList){
	      	try {
	      		System.out.println("Image timestamp: "+img.getTimestamp());
	      		System.out.println("Image Latitude: "+img.getLatitude());
	      		System.out.println("Image Longitude: "+img.getLongitude());
	      		String name = img.getOwneremail()+"_"+img.getTitle();
	      		if(!name.endsWith(".png")){
	      			name += ".png";
	      		}
	      		DataOutputStream dis = new DataOutputStream((new FileOutputStream(new File("resources/retrieved/"+name))));
				dis.write(img.getActualImage().toByteArray());
				dis.flush();
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			client.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
