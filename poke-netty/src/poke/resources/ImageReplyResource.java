package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eye.Comm.Header;
import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import eye.Comm.UserImageReply;
import poke.db.DBException;
import poke.db.IImageStorage;
import poke.db.StorageFactory;
import poke.server.conf.ServerConf;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;

/**
 * Resource class to process image retrieve request
 *
 */
public class ImageReplyResource implements Resource {

	protected static Logger logger = LoggerFactory.getLogger("ReplyResource");
	private ServerConf.GeneralConf param;
	@Override
	public Response process(Request request) {

		Response reply = null;

		try {

			eye.Comm.UserImageRequest userImageRequest = request.getBody().getImgreq();

			//fetch registration fields from request
			logger.debug("email_id: " + userImageRequest.getEmailid());

			Response.Builder r = Response.newBuilder();
			r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
					ReplyStatus.SUCCESS, null));

			String email = request.getBody().getEmailid();

			IImageStorage imageStorage = StorageFactory.getImageStorage(this.param);
			
			UserImageReply imageReply;
			imageReply = imageStorage.retrieveImage(email);



			eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();
			br.setImgreply(imageReply);
			r.setBody(br.build());

			eye.Comm.Header.Builder header = Header.newBuilder();
			header.setOriginator("server");
			header.setRoutingId(Header.Routing.IMGRETREIVE);
			r.setHeader(header.build());

			reply = r.build();
			logger.debug("Images Retrieved Successfully... " + email);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reply;
	}

	@Override
	public void init(ServerConf.GeneralConf param) {
		this.param = param;
		// TODO Auto-generated method stub

	}

}
