package poke.resources;

import eye.Comm.PayloadReply;
import eye.Comm.Request;
import eye.Comm.Response;
import eye.Comm.Header.ReplyStatus;
import poke.server.conf.ServerConf;
import poke.server.resources.Resource;
import poke.server.resources.ResourceUtil;

public class ServerUnvailableResource implements Resource {

	private ServerConf.GeneralConf param;
	@Override
	public Response process(Request request) {
		Response.Builder r = Response.newBuilder();
        r.setHeader(ResourceUtil.buildHeaderFrom(request.getHeader(),
                ReplyStatus.SERVER_UNAVAILABLE, null));
        
        eye.Comm.PayloadReply.Builder br=PayloadReply.newBuilder();
      
        r.setBody(br.build());
         
        Response reply = r.build();

        return reply;
	}

	@Override
	public void init(ServerConf.GeneralConf param) {
		this.param = param;

	}

}
