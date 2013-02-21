/*
 * copyright 2012, gash
 * 
 * Gash licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package poke.demo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.google.protobuf.ByteString;

import poke.client.ClientConnection;

public class Jab {
	private String tag;
	private int count;

	public Jab(String tag) {
		this.tag = tag;
	}

	public void run() throws IOException {
		ClientConnection cc = ClientConnection
				.initConnection("localhost", 5570);
		
		
		for (int i = 0; i < 3; i++) {
			count++;
			cc.poke(tag, count, getImageByteString());
		}
	}
	
	public ByteString getImageByteString () throws IOException {
		 // open image
		 File imgPath = new File("/usr/share/backgrounds/warty-final-ubuntu.png");
		 //File imgPath = new File("/home/user/test.txt");
		 byte [] fileData = new byte[(int)imgPath.length()];
		 DataInputStream dis = new DataInputStream((new FileInputStream(imgPath)));
		 dis.readFully(fileData);
		 dis.close();
		 return ByteString.copyFrom(fileData);
	}

	public static void main(String[] args) {
		try {
			Jab jab = new Jab("jab");
			jab.run();

			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
