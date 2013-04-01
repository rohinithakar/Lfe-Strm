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
import poke.client.PokeClient.LoginCallback;
import poke.client.PokeClient.RegisterCallback;

public class SimpleClient implements ImageUploadCallback, RegisterCallback, ImageRetrieveCallback, LoginCallback{
	
	PokeClient client = null;
	
	SimpleClient() {
//		client = new PokeClient("localhost", 5005, "SimpleClient");
		client = new PokeClient("localhost", 6570, "SimpleClient");
		client.start();
	}
	
	public void register() throws InterruptedException {
		client.setRegistrationCallback(this);
		//below request will be processed by server: id = one and port = 5571
		client.register("a", "b", "a@abc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = two and port = 5572
		client.register("a", "b", "abcabc@abc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = three and port = 5573
		client.register("a", "b", "abcabcabcabc@abcabc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = four and port = 5574
		client.register("a", "b", "abcabcabcabcabcabc@abcabcabc.com", "1234");
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
	
	public void login() throws InterruptedException {
		client.setLoginCallback(this);
		//below request will be processed by server: id = one and port = 5571
		client.login("a@abc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = two and port = 5572
		client.login("abcabc@abc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = three and port = 5573
		client.login("abcabcabcabc@abcabc.com", "1234");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = four and port = 5574
		client.login("abcabcabcabcabcabc@abcabcabc.com", "1234");
	}
	
	@Override
	public void loggedIn(boolean loginSuccess) {
		try {
			System.out.println("Login Response Received: " + loginSuccess);
			client.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void uploadImage() throws IOException {
		ByteString bs = getImageByteString();
		client.setImageUploadCallback(this);
		//below request will be processed by server: id = one and port = 5571
		eye.Comm.Image.Builder image = Image.newBuilder();
		image.setActualImage(bs);
		image.setOwneremail("a@abc.com");
		image.setLatitude(37.335363);
		image.setLongitude(-121.887068);
		image.setTitle("testFrom3rdStreet.png");
		client.uploadImage("a@abc.com", image);
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = two and port = 5572
		image = Image.newBuilder();
		image.setActualImage(bs);
		image.setOwneremail("abcabc@abc.com");
		image.setLatitude(37.335169);
		image.setLongitude(-121.88107);
		image.setTitle("testFromSJSU.png");
		client.uploadImage("abcabc@abc.com", image);
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = three and port = 5573
		image = Image.newBuilder();
		image.setActualImage(bs);
		image.setOwneremail("abcabcabcabc@abcabc.com");
		image.setLatitude(37.335686);
		image.setLongitude(-121.885469);
		image.setTitle("testFromMLKLibrary.png");
		client.uploadImage("abcabcabcabc@abcabc.com", image);
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = four and port = 5574
		image = Image.newBuilder();
		image.setActualImage(bs);
		image.setOwneremail("abcabcabcabcabcabc@abcabcabc.com");
		image.setLatitude(37.337187);
		image.setLongitude(-121.886871);
		image.setTitle("testFromSJCityHall.png");
		client.uploadImage("abcabcabcabcabcabc@abcabcabc.com", image);
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


	public void retrieveImage() throws IOException {
		client.setImageRetrieveCallback(this);
		//below request will be processed by server: id = one and port = 5571
		client.getImages("a@abc.com");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = two and port = 5572
		client.getImages("abcabc@abc.com");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = three and port = 5573
		client.getImages("abcabcabcabc@abcabc.com");
		//below request will be redirected by server: id = one and port = 5571 and processed by server: id = four and port = 5574
		client.getImages("abcabcabcabcabcabc@abcabcabc.com");
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
	

	public static void main(String args[]) throws Exception {
		SimpleClient client = new SimpleClient();
		client.login();
	}

}
