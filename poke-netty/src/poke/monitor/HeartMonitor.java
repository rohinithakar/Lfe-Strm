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
package poke.monitor;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.server.Server;
import poke.server.conf.ServerConf;

import eye.Comm.Management;
import eye.Comm.Network;
import eye.Comm.Network.Action;

public class HeartMonitor {
	protected Logger logger;

	private String host;
	private int port;
	private ServerConf.GeneralConf monitoredSvrConf;
	private String monitoredNodeId;
	protected ChannelFuture channel; // do not use directly call connect()!
	protected ClientBootstrap bootstrap;
	private Server svr;

	// protected ChannelFactory cf;
//
//	public HeartMonitor(String host, int port) {
//		this.host = host;
//		this.port = port;
//		initTCP();
//	}

	public HeartMonitor(ServerConf.GeneralConf conf,Server svr) {
		this.monitoredSvrConf = conf;
		this.port = Integer.valueOf(conf.getProperty("port.mgmt"));
		this.host = conf.getProperty("hostname");
		this.svr = svr;
		this.logger = LoggerFactory.getLogger("monitor[" + svr.id + "]");
		this.monitoredNodeId = monitoredSvrConf.getProperty("node.id");
		initTCP();
	}
	
	protected void release() {
		// if (cf != null)
		// cf.releaseExternalResources();
	}

	protected void initUDP() {
		NioDatagramChannelFactory cf = new NioDatagramChannelFactory(
				Executors.newCachedThreadPool());
		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(cf);

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("keepAlive", true);

		// Set up the pipeline factory.
		bootstrap.setPipelineFactory(new MonitorPipeline(svr));
	}

	protected void initTCP() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newFixedThreadPool(2)));

		bootstrap.setOption("connectTimeoutMillis", 10000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		//bootstrap.setPipelineFactory(new MonitorPipeline());
		bootstrap.setPipelineFactory(new MonitorPipeline(this.monitoredSvrConf));

	}

	/**
	 * create connection to remote server
	 * 
	 * @return
	 */
	protected Channel connect() {
		// Start the connection attempt.
		if (channel == null) {
			logger.info("connecting");
			channel = bootstrap.connect(new InetSocketAddress(host, port));
			logger.info("connection done");
		}

		// wait for the connection to establish
		channel.awaitUninterruptibly();

		if (channel.isDone() && channel.isSuccess()) {
			svr.updateRemoteNodeStatus(this.monitoredNodeId, true);
			return channel.getChannel();
		} else{
			svr.updateRemoteNodeStatus(this.monitoredNodeId, false);
			throw new RuntimeException("Not able to establish connection to server");
		}
	}

	protected void waitForever() {
		try {
			
			init();
			while (true) {
				//logger.info("In while loop....");
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		logger.info("In waitForever*******");
		Channel ch = connect();
		Network.Builder n = Network.newBuilder();
		n.setNodeId("monitor[" + svr.id + "]");
		n.setAction(Action.NODEJOIN);
		logger.info("nodejoin done");
		
		Management.Builder m = Management.newBuilder();
		
		
		m.setGraph(n.build());
		//problem during ch.write
		ch.write(m.build());
		//Thread.sleep(10000);
		logger.info("All settings done*******");
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		HeartMonitor hm = new HeartMonitor("localhost", 5670);
//		hm.waitForever();
//	}

}
