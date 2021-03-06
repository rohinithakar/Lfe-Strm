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
package poke.server.queue;

import java.lang.Thread.State;
import java.util.concurrent.LinkedBlockingDeque;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.client.PokeClient;
import poke.resources.ServerUnvailableResource;
import poke.server.Server;
import poke.server.conf.ServerConf.GeneralConf;
import poke.server.hash.HashingService;
import poke.server.resources.Resource;
import poke.server.resources.ResourceFactory;
import poke.server.resources.ResourceUtil;

import com.google.protobuf.GeneratedMessage;

import eye.Comm.Header.ReplyStatus;
import eye.Comm.Request;
import eye.Comm.Response;

/**
 * A server queue exists for each connection (channel).
 * 
 * @author gash
 * 
 */
public class PerChannelQueue implements ChannelQueue {
	protected Logger logger ;

	private Channel channel;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> inbound;
	private LinkedBlockingDeque<com.google.protobuf.GeneratedMessage> outbound;
	private OutboundWorker oworker;
	private InboundWorker iworker;

	// not the best method to ensure uniqueness
	private ThreadGroup tgroup = new ThreadGroup("ServerQueue-"
			+ System.nanoTime());

	protected PerChannelQueue(Channel channel, Server svr) {
		this.channel = channel;
		logger = LoggerFactory.getLogger("server[" + svr.id + "]");
		init(svr);
	}

	protected void init(Server svr) {
		inbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();
		outbound = new LinkedBlockingDeque<com.google.protobuf.GeneratedMessage>();

		iworker = new InboundWorker(svr, tgroup, 1, this, logger);
		iworker.start();

		oworker = new OutboundWorker(svr, tgroup, 1, this, logger);
		oworker.start();

		// let the handler manage the queue's shutdown
		// register listener to receive closing of channel
		// channel.getCloseFuture().addListener(new CloseListener(this));
	}

	protected Channel getChannel() {
		return channel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#shutdown(boolean)
	 */
	@Override
	public void shutdown(boolean hard) {
		logger.info("server is shutting down");

		channel = null;

		if (hard) {
			// drain queues, don't allow graceful completion
			inbound.clear();
			outbound.clear();
		}

		if (iworker != null) {
			iworker.forever = false;
			if (iworker.getState() == State.BLOCKED
					|| iworker.getState() == State.WAITING)
				iworker.interrupt();
			iworker = null;
		}

		if (oworker != null) {
			oworker.forever = false;
			if (oworker.getState() == State.BLOCKED
					|| oworker.getState() == State.WAITING)
				oworker.interrupt();
			oworker = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueRequest(eye.Comm.Finger)
	 */
	@Override
	public void enqueueRequest(Request req) {
		try {
			inbound.put(req);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for processing", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see poke.server.ChannelQueue#enqueueResponse(eye.Comm.Response)
	 */
	@Override
	public void enqueueResponse(Response reply) {
		try {
			outbound.put(reply);
		} catch (InterruptedException e) {
			logger.error("message not enqueued for reply", e);
		}
	}

	protected class OutboundWorker extends Thread {
		int workerId;
		PerChannelQueue sq;
		boolean forever = true;
		@SuppressWarnings("unused")
		private Server svr = null;
		private Logger logger;

		public OutboundWorker(Server svr, ThreadGroup tgrp, int workerId, PerChannelQueue perChannelQ,Logger logger) {
			super(tgrp, "outbound-" + workerId);
			this.workerId = workerId;
			this.sq = perChannelQ;
			this.svr = svr;
			this.logger = logger;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			Channel conn = sq.channel;
			if (conn == null || !conn.isOpen()) {
				logger
				.error("connection missing, no outbound communication");
				return;
			}

			while (true) {
				if (!forever && sq.outbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = sq.outbound.take();
					if (conn.isWritable()) {
						boolean rtn = false;
						if (channel != null && channel.isOpen()
								&& channel.isWritable()) {
							ChannelFuture cf = channel.write(msg);

							// blocks on write - use listener to be async
							cf.awaitUninterruptibly();
							rtn = cf.isSuccess();
							if (!rtn)
								sq.outbound.putFirst(msg);
						}

					} else
						sq.outbound.putFirst(msg);
				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					logger.error(
							"Unexpected communcation failure", e);
					break;
				}
			}

			if (!forever) {
				logger.info("connection queue closing");
			}
		}
	}

	protected class InboundWorker extends Thread {
		int workerId;
		PerChannelQueue perChannelQueue;
		boolean forever = true;
		private Server svr = null;
		private Logger logger;

		public InboundWorker(Server svr, ThreadGroup tgrp, int workerId, PerChannelQueue perChannelQueue,Logger logger) {
			super(tgrp, "inbound-" + workerId);
			this.workerId = workerId;
			this.perChannelQueue = perChannelQueue;
			this.svr = svr;
			this.logger = logger;

			if (outbound == null)
				throw new RuntimeException(
						"connection worker detected null queue");
		}

		@Override
		public void run() {
			Channel conn = perChannelQueue.channel;
			if (conn == null || !conn.isOpen()) {
				logger
				.error("connection missing, no inbound communication");
				return;
			}

			while (true) {
				if (!forever && perChannelQueue.inbound.size() == 0)
					break;

				try {
					// block until a message is enqueued
					GeneratedMessage msg = perChannelQueue.inbound.take();

					// process request and enqueue response
					if (msg instanceof Request) {
						Request req = ((Request) msg);
						String emailId = req.getBody().getEmailid();
						String serverId = HashingService.getInstance().hash(emailId);
						if( !serverId.equalsIgnoreCase(svr.id)) {
							// Now forward this request to another server
							if(svr.getRemoteNodeStatus(serverId)) {
								logger.info("Fowarding Request to Server:" + serverId );
								GeneralConf gconf = svr.conf.findConfById(serverId);
								PokeClient client = new PokeClient(
										gconf.getProperty("hostname"), 
										Integer.parseInt(gconf.getProperty("port")), 
										svr.id);
								client.start();
								client.forwardRequest(req, perChannelQueue);
							} else {
								logger.info("Server down:" + serverId );
								ServerUnvailableResource rsc = new ServerUnvailableResource(); 
								perChannelQueue.enqueueResponse(rsc.process(null));
							}
						} else {
							Resource rsc = ResourceFactory.getInstance()
									.resourceInstance(
											req.getHeader().getRoutingId());

							Response reply = null;
							if (rsc == null) {
								logger.error("failed to obtain resource for " + req);
								reply = ResourceUtil.buildError(req.getHeader(),
										ReplyStatus.FAILURE,
										"Request not processed");
							} else {
								rsc.init(svr.generalConf);
								reply = rsc.process(req);
							}

							perChannelQueue.enqueueResponse(reply);
						}
					}

				} catch (InterruptedException ie) {
					break;
				} catch (Exception e) {
					logger.error(
							"Unexpected processing failure", e);
					break;
				}
			}

			if (!forever) {
				logger.info("connection queue closing");
			}
		}
	}

	public class CloseListener implements ChannelFutureListener {
		private ChannelQueue sq;

		public CloseListener(ChannelQueue sq) {
			this.sq = sq;
		}

		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			sq.shutdown(true);
		}
	}
}
