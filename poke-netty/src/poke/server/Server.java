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

package poke.server;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.AdaptiveReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poke.monitor.HeartMonitor;
import poke.server.conf.ServerConf;
import poke.server.hash.HashingService;
import poke.server.management.ManagementDecoderPipeline;
import poke.server.management.ManagementQueue;
import poke.server.management.ServerHeartbeat;
import poke.server.routing.ServerDecoderPipeline;

/**
 * Note high surges of messages can close down the channel if the handler cannot
 * process the messages fast enough. This design supports message surges that
 * exceed the processing capacity of the server through a second thread pool
 * (per connection or per server) that performs the work. Netty's boss and
 * worker threads only processes new connections and forwarding requests.
 * <p>
 * Reference Proactor pattern for additional information.
 * 
 * @author gash
 * 
 */
public class Server {
	protected static Logger logger = LoggerFactory.getLogger("server");
	
	private Logger svrLogger = null;

	protected static final ChannelGroup allChannels = new DefaultChannelGroup(
			"server");
	protected static HashMap<Integer, Bootstrap> bootstrap = new HashMap<Integer, Bootstrap>();
	protected ChannelFactory cf, mgmtCF;
	public ServerConf conf;
	protected ServerHeartbeat heartbeat;
	public ManagementQueue mgmtQ;
	private ConcurrentHashMap<String, Boolean> serverStatus = new ConcurrentHashMap<String, Boolean>();
	
	public String id = null;	
	public ServerConf.GeneralConf generalConf;

	/**
	 * static because we need to get a handle to the factory from the shutdown
	 * resource
	 */
	public static void shutdown() {
		try {
			ChannelGroupFuture grp = allChannels.close();
			grp.awaitUninterruptibly(5, TimeUnit.SECONDS);
			for (Bootstrap bs : bootstrap.values())
				bs.getFactory().releaseExternalResources();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("Server shutdown");
		System.exit(0);
	}

	/**
	 * initialize the server with a configuration of it's resources
	 * 
	 * @param cfg
	 */
	public Server(ServerConf conf) {
		this.conf = conf;
		init();
	}

	private void init() {
		
		// Initialize the hashing service
		HashingService.initialize(conf);
		
		// communication - external (TCP) using asynchronous communication
		cf = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		// communication - internal (UDP)
		// mgmtCF = new
		// NioDatagramChannelFactory(Executors.newCachedThreadPool(),
		// 1);

		// internal using TCP - a better option
		mgmtCF = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newFixedThreadPool(2));

	}

	public void release() {
		if (heartbeat != null)
			heartbeat.release();
	}

	private void createPublicBoot(int port) {
		// construct boss and worker threads (num threads = number of cores)

		ServerBootstrap bs = new ServerBootstrap(cf);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ServerDecoderPipeline(this));

		// tweak for performance
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);
		bs.setOption("receiveBufferSizePredictorFactory",
				new AdaptiveReceiveBufferSizePredictorFactory(1024 * 2,
						1024 * 4, 1048576));

		bootstrap.put(port, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(port));
		allChannels.add(ch);

		// We can also accept connections from a other ports (e.g., isolate read
		// and writes)

		svrLogger.info("Starting server, listening on port = " + port);
	}

	private void createManagementBoot(int port) {
		// construct boss and worker threads (num threads = number of cores)

		// UDP: not a good option as the message will be dropped
		// ConnectionlessBootstrap bs = new ConnectionlessBootstrap(mgmtCF);

		// TCP
		ServerBootstrap bs = new ServerBootstrap(mgmtCF);

		// Set up the pipeline factory.
		bs.setPipelineFactory(new ManagementDecoderPipeline(this));

		// tweak for performance
		// bs.setOption("tcpNoDelay", true);
		bs.setOption("child.tcpNoDelay", true);
		bs.setOption("child.keepAlive", true);

		bootstrap.put(port, bs);

		// Bind and start to accept incoming connections.
		Channel ch = bs.bind(new InetSocketAddress(port));
		allChannels.add(ch);

		svrLogger.info("Starting server, listening on port = " + port);
	}

	public void run(ServerConf.GeneralConf generalConf) {
		this.id = generalConf.getProperty("node.id");
		this.generalConf = generalConf;
		svrLogger = LoggerFactory.getLogger("Server[" + this.id + "]");
		
		String str = generalConf.getProperty("port");
		if (str == null)
			str = "5570";
		
		int port = Integer.parseInt(str);

		str = generalConf.getProperty("port.mgmt");
		int mport = Integer.parseInt(str);
			
		// start communication
		createPublicBoot(port);
		createManagementBoot(mport);
		
		// start management
		mgmtQ = new ManagementQueue(this);
		mgmtQ.startup();

		// start heartbeat
		heartbeat = ServerHeartbeat.getInstance(this);
		heartbeat.start();
		
		// In the map, we know that we are always up
		serverStatus.put(this.id, true);

		svrLogger.info("Server " + str+ " ready on port: "+ port);
	}
	
	public void startMonitoring(){
		String[] edgeToNodes = this.generalConf.getProperty("edgeToNode").split("\\|");
		for(String e2n : edgeToNodes) {
			if( e2n != null && !e2n.isEmpty() ) {
				ServerConf.GeneralConf serverToConnect = this.conf.findConfById(e2n);
				HeartMonitor hm = new HeartMonitor(serverToConnect,this);
				svrLogger.info("Starting to Monitor Node: " + serverToConnect.getProperty("node.id"));
				hm.init();
				svrLogger.info("Server HB Monitor Started " + this.id); 
			}
		}
	}
	
	public void updateRemoteNodeStatus(String remoteNodeId, boolean status) {
		serverStatus.put(remoteNodeId, status);
	}
	
	public boolean getRemoteNodeStatus(String remoteNodeId) {
		Boolean status = serverStatus.get(remoteNodeId);
		if( status == null || status.booleanValue() == false ) {
			return false;
		}
		return true;
	}
}
