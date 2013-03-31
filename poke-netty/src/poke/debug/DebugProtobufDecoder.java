package poke.debug;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;

import com.google.protobuf.MessageLite;

public class DebugProtobufDecoder extends ProtobufDecoder {

	public DebugProtobufDecoder(MessageLite prototype) {
		super(prototype);
	}
	
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) {
		Object response = null;
		try {
			response = super.decode(ctx, channel, msg);
			System.err.println("----------------------------");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return msg;		
	}

}
