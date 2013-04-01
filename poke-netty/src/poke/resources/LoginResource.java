package poke.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import poke.db.DBException;
import poke.db.IUserStorage;
import poke.db.StorageFactory;
import poke.server.conf.ServerConf;
import poke.server.conf.ServerConf.GeneralConf;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;

public class LoginResource implements Resource {
	protected static Logger logger = LoggerFactory.getLogger("Login Resource");
	private ServerConf.GeneralConf param;
	
	@Override
	public Response process(Request request) {
		
        logger.info("poke: " + request.getBody().getFinger().getTag());
        
        eye.Comm.Login login = request.getBody().getLogin();
        
//        fetch registration fields from request
        logger.info("email_id: " + request.getBody().getEmailid() + 
        		    "password: " + login.getPassword() + 
        		    " Server: " + param
        		    );
        IUserStorage userStorage = StorageFactory.getUserStorage(this.param);
        Response.Builder r = Response.newBuilder();
        
        try {
			if(userStorage.login(request.getBody().getEmailid(), login)){
				r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
		                ReplyStatus.SUCCESS, null));
			}else{
				r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
		                ReplyStatus.FAILURE, null));
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
	                ReplyStatus.FAILURE, null));
		}
        
        eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();
                r.setBody(br.build());
         
        Response reply = r.build();
                logger.info("Login Successful...");

        return reply;
	}

	@Override
	public void init(GeneralConf param) {
		this.param = param;
	}

}
