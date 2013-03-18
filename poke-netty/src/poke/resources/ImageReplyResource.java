package poke.resources;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.ByteString;

import eye.Comm.Header;
import eye.Comm.Image;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import eye.Comm.UserImageReply;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;

/**
 * Resource class to process image retrieve request
 * @author swetapatel
 *
 */
public class ImageReplyResource implements Resource {

	protected static Logger logger = LoggerFactory.getLogger("ReplyResource");

	@Override
	public Response process(Request request) {

		logger.info("poke: " + request.getBody().getFinger().getTag());

		eye.Comm.UserImageRequest userImageRequest = request.getBody().getImgreq();

		//fetch registration fields from request
		logger.info("email_id: " + userImageRequest.getEmailid());

		Response.Builder r = Response.newBuilder();
		r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
				ReplyStatus.SUCCESS, null));

		eye.Comm.Image.Builder image = Image.newBuilder();
		image.setOwneremail("abc@abc.com");
		image.setImgid("1");
		image.setLatitude(32.333333);
		image.setLongitude(121.333333);
		File imgPath = new File("resources/warty-final-ubuntu.png");
		try {
			byte [] fileData = new byte[(int)imgPath.length()];
			DataInputStream dis = new DataInputStream((new FileInputStream(imgPath)));
			dis.readFully(fileData);
			dis.close();

			image.setActualImage(ByteString.copyFrom(fileData));
		}catch(IOException e){
			logger.error(e.toString());
			image.setActualImage(null);
		}

		eye.Comm.UserImageReply.Builder userImageReply = UserImageReply.newBuilder();
		userImageReply.addImgs(0, image);

		eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();
		br.setImgreply(userImageReply.build());
		r.setBody(br.build());

		eye.Comm.Header.Builder header = Header.newBuilder();
		header.setOriginator("server");
		header.setRoutingId(Header.Routing.IMGRETREIVE);
		r.setHeader(header.build());

		Response reply = r.build();
		logger.info("Registered Successfully...");

		return reply;
	}

	@Override
	public void init(String param) {
		// TODO Auto-generated method stub
		
	}

}
