package poke.debug;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

public class DebugFrameEncoder extends LengthFieldPrepender {

	public DebugFrameEncoder(int lengthFieldLength) {
		super(lengthFieldLength);
	}
	
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) {
		Object encodedData = null;
		try {
			encodedData = super.encode(ctx, channel, msg);
			System.err.println("----------------------------");
			System.err.println("Size before: " + msg.toString().length() );
			System.err.println("Size after: " + encodedData.toString().length() );
			System.err.println("----------------------------");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return encodedData;
	}

}
