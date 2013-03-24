package poke.demo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;

import eye.Comm.Image;

import poke.client.PokeClient;
import poke.client.PokeClient.ImageUploadCallback;
import poke.client.PokeClient.RegisterCallback;

public class SimpleClient implements ImageUploadCallback, RegisterCallback {
	
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
		client.uploadImage("abc@abc.com", image);
	}
	
	public void register() throws InterruptedException {
		client.setRegistrationCallback(this);
		//client.register("a", "b", "abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc@abc.com", "1234");
		client.register("a", "b", "abc@abc.com", "1234");
	}

	@Override
	public void imageUploadReply(boolean uploaded) {
		try {
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
}
