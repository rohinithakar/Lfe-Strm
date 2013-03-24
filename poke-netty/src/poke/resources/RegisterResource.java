package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;
import poke.server.storage.jpa.UserInfo;
import poke.server.storage.jpa.UserOperation;

/**
 * Resource class to process user registration request
 * @author swetapatel
 *
 */
public class RegisterResource implements Resource {

	protected static Logger logger = LoggerFactory.getLogger("RegisterResource");
	String param = null;
	@Override
	public Response process(Request request) {
		
        logger.info("poke: " + request.getBody().getFinger().getTag());
        
        eye.Comm.Register register = request.getBody().getReg();
        
//        fetch registration fields from request
        logger.info("email_id: " + request.getBody().getEmailid() + 
        		    "firstname: "+ register.getFname()   +
        		    "lastname: " + register.getLname()   +
        		    "password: " + register.getPassword() + 
        		    " Server: " + param
        		    );
        
        
        /*
        UserOperation op = new UserOperation();
        op.registerUser(request.getBody().getEmailid(), register.getLname(), register.getLname(), register.getPassword());
        */
        
        Response.Builder r = Response.newBuilder();
        r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
                ReplyStatus.SUCCESS, null));
        
        eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();
                r.setBody(br.build());
         
        Response reply = r.build();
                logger.info("Registered Successfully...");

        return reply;
	}
	@Override
	public void init(String param) {
		this.param = param;
	}

}
