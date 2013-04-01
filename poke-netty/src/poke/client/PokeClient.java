package poke.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.debug.DebugFrameDecoder;
import poke.debug.DebugFrameEncoder;
import poke.server.queue.PerChannelQueue;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Header.*;
import eye.Comm.*;

/**
 * Self-contained client that provides methods to communicate to
 * server
 *
 */
public class PokeClient {

	protected Logger logger;

	private String host;
	private int port;
	
	public static interface ImageUploadCallback {
		void imageUploadReply(boolean uploaded);
	}
	
	public static interface ImageRetrieveCallback {
		void imageReply(eye.Comm.UserImageReply images);
	}
	
	public static interface RegisterCallback {
		void registered(boolean registrationSuccess);
	}
	
	public static interface LoginCallback {
		void loggedIn(boolean loginSuccess);
	}
	
	private ImageUploadCallback imgUpload = null;
	private ImageRetrieveCallback imgRetrieve = null;
	private RegisterCallback registerCb = null;
	private LoginCallback loginCb = null;
	private ClientBootstrap bootstrap;
	private ChannelFuture channelFuture;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private OutboundWorker worker = null;
	private String clientName = null;
	private PerChannelQueue perChannelQueue = null;
	private ExecutorService bossExecService = null;
	private ExecutorService workerExecService = null;
	private NioClientSocketChannelFactory nioCF = null;
	
	public PokeClient(String hostname, int port) {
		this(hostname, port, PokeClient.class.getCanonicalName());
	}
	
	public PokeClient(String hostname, int port, String clientName ) {
		this.port = port;
		this.host = hostname;
		this.clientName = clientName; 
		logger = LoggerFactory.getLogger(clientName);
		
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();
		bossExecService = Executors.newFixedThreadPool(1);
		workerExecService = Executors.newFixedThreadPool(1);
		
		nioCF = new NioClientSocketChannelFactory(
				bossExecService, workerExecService);
		
		bootstrap = new ClientBootstrap(nioCF);

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new PokeClientDecoderPipeline(this));
	}
	
	public void setImageUploadCallback(ImageUploadCallback cb) {
		this.imgUpload = cb;
	}
	
	public void setImageRetrieveCallback(ImageRetrieveCallback cb) {
		this.imgRetrieve = cb;
	}
	
	public void setRegistrationCallback(RegisterCallback cb) {
		this.registerCb = cb;
	}
	
	public void setLoginCallback(LoginCallback cb) {
		this.loginCb = cb;
	}
	
	
	public void start() {
		if( worker == null ) {
			worker = new OutboundWorker(this);
			worker.start();
		}
	}
	
	public boolean stop() throws InterruptedException {
		worker.stopWorker();
		worker.join();
		channelFuture.getChannel().close().awaitUninterruptibly();
		bossExecService.shutdown();
		workerExecService.shutdown();
//		nioCF.releaseExternalResources();
		return true;
	}
	
	public void register(String firstName, String lastName, String email, String password) {
		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setEmailid(email);
		
		eye.Comm.Register.Builder reg = Register.newBuilder();
		reg.setFname(firstName);
		reg.setLname(lastName);
		reg.setPassword(password);
		p.setReg(reg.build());
		r.setBody(p.build());
		
		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator(clientName);
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.REGISTER);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	public void login(String email, String password) {
		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setEmailid(email);
		eye.Comm.Login.Builder login = Login.newBuilder();
		login.setPassword(password);
		p.setLogin(login.build());
		r.setBody(p.build());
		
		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator(clientName);
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.LOGIN);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	public void forwardRequest(eye.Comm.Request req, PerChannelQueue sq) {
		this.perChannelQueue = sq;
		try {
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	public void getImages(String emailId) {
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setEmailid(emailId);
		r.setBody(p.build());
		
		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator(clientName);
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.IMGRETREIVE);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}
	
	public void uploadImage(String emailId, eye.Comm.Image.Builder img) {
		// payload containing data
		Request.Builder r = Request.newBuilder();
		eye.Comm.Payload.Builder p = Payload.newBuilder();
		p.setEmailid(emailId);
		
		p.setImageup(img.build());
		r.setBody(p.build());

		// header with routing info
		eye.Comm.Header.Builder h = Header.newBuilder();
		h.setOriginator(clientName);
		h.setTime(System.currentTimeMillis());
		h.setRoutingId(eye.Comm.Header.Routing.IMGUPLOAD);
		r.setHeader(h.build());

		eye.Comm.Request req = r.build();

		try {
			// enqueue message
			outbound.put(req);
		} catch (InterruptedException e) {
			logger.warn("Unable to deliver message, queuing");
		}
	}

	
	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	protected Channel connect() {
		// Start the connection attempt.
		if (channelFuture == null) {
			// System.out.println("---> connecting");
			channelFuture = bootstrap.connect(new InetSocketAddress(host, port));

			// cleanup on lost connection

		}

		// wait for the connection to establish
		channelFuture.awaitUninterruptibly();

		if (channelFuture.isDone() && channelFuture.isSuccess())
			return channelFuture.getChannel();
		else
			throw new RuntimeException(
					"Not able to establish connection to server");
	}
	
	public class PokeClientDecoderPipeline implements ChannelPipelineFactory {

		private PokeClient client;
		
		public PokeClientDecoderPipeline(PokeClient client) {
			this.client = client;
		}

		public ChannelPipeline getPipeline() throws Exception {
			ChannelPipeline pipeline = Channels.pipeline();

			pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(
					67108864, 0, 4, 0, 4));
//			pipeline.addLast("frameDecoder", new DebugFrameDecoder(
//					67108864, 0, 4, 0, 4));
			pipeline.addLast("protobufDecoder", new ProtobufDecoder(
					eye.Comm.Response.getDefaultInstance()));
			pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
//			pipeline.addLast("frameEncoder", new DebugFrameEncoder(4));
			pipeline.addLast("protobufEncoder", new ProtobufEncoder());

			// our message processor
			pipeline.addLast("handler", new PokeClientHandler(client));

			return pipeline;
		}
	}
	
	/**
	 * queues outgoing messages - this provides surge protection if the client
	 * creates large numbers of messages.
	 * 
	 * @author gash
	 * 
	 */
	protected class OutboundWorker extends Thread {
		boolean forever = true;
		PokeClient client;

		public OutboundWorker(PokeClient conn) {
			super("client-outboundWorker");
			this.client = conn;
			this.setName(conn.clientName + "-outboundWorker");

			if (conn.outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}
		
		public void stopWorker() { 
			this.forever = false;
			this.interrupt();
		}

		@Override
		public void run() {
			Channel ch = client.connect();
			if (ch == null || !ch.isOpen()) {
				ClientConnection.logger
						.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && client.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = client.outbound.take();
					if (ch.isWritable()) {
						PokeClientHandler handler = client.connect().getPipeline()
								.get(PokeClientHandler.class);

						if (!handler.send(msg))
							client.outbound.putFirst(msg);

					} else
						client.outbound.putFirst(msg);
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					ClientConnection.logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				ClientConnection.logger.info("connection queue closing");
			}
		}
	}
	
	public class PokeClientHandler extends SimpleChannelHandler {
		private PokeClient client;
		
		private volatile Channel channel;
		
		public PokeClientHandler(PokeClient client) {
			this.client = client;
		}
		
		private boolean send(GeneratedMessage msg) {
			// TODO a queue is needed to prevent overloading of the socket
			// connection. For the demonstration, we don't need it
			ChannelFuture cf = channel.write(msg);
			if (cf.isDone() && !cf.isSuccess()) {
				logger.error("failed to poke!");
				return false;
			}

			return true;
		}

		private void handleMessage(eye.Comm.Response msg) {
			
			// Short Circuit
			// If the client is called via server
			// We pass the message and close the client
			if( perChannelQueue != null ) {
				perChannelQueue.enqueueResponse(msg);
				perChannelQueue = null;
				try {
					client.stop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			Routing routingId = msg.getHeader().getRoutingId();
			switch(routingId) {
			case IMGRETREIVE:
				if( imgRetrieve != null ) {
					imgRetrieve.imageReply(msg.getBody().getImgreply());
				}
				break;
			case IMGUPLOAD:
				if( imgUpload != null ) {
					imgUpload.imageUploadReply(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS);
				}
				break;
			case REGISTER:
				if( registerCb != null ) {
					registerCb.registered(msg.getHeader().getReplyCode() == ReplyStatus.SUCCESS);
				}
				break;
				default:
					throw new RuntimeException("Unsupported Routing Id: " + routingId );
			}
		}
		
		@Override
		public void channelInterestChanged(ChannelHandlerContext ctx,
				ChannelStateEvent e) throws Exception {
			if (e.getState() == ChannelState.INTEREST_OPS
					&& ((Integer) e.getValue() == Channel.OP_WRITE)
					|| (Integer) e.getValue() == Channel.OP_READ_WRITE)
				logger.warn("channel is not writable! <--------------------------------------------");
		}

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
			handleMessage((eye.Comm.Response) e.getMessage());
		}
		
		@Override
		public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
				throws Exception {
			channel = e.getChannel();
			super.channelOpen(ctx, e);
		}

		@Override
		public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
				throws Exception {
			logger.info("Channel Closed Invoked");
			if (channel.isConnected())
				channel.write(ChannelBuffers.EMPTY_BUFFER).addListener(
						ChannelFutureListener.CLOSE);
		}		
	}
}
