package poke.server.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

import poke.server.conf.ServerConf;
import poke.server.conf.ServerConf.GeneralConf;

public class HashingService {

	NavigableMap<Integer, String> map = new TreeMap<Integer, String>();
	
	int minRange = Integer.MAX_VALUE;
	int maxRange = Integer.MIN_VALUE;
	
	private static AtomicReference<HashingService> instance = 
			new AtomicReference<HashingService>();
	
	public static void initialize(ServerConf conf) {
		if( instance.get() == null ) {
			instance.compareAndSet(null, new HashingService(conf));
		}
	}
	
	public static HashingService getInstance() {
		if( instance.get() == null) {
			throw new RuntimeException("Hashing Service not initialized");
		}
		return instance.get();
	}
	
	private HashingService(ServerConf conf) {
		List<GeneralConf> generalConfs = conf.getServer();
		int fromRange;
		int toRange;
		for( GeneralConf gconf : generalConfs ) {
			fromRange = Integer.parseInt(gconf.getProperty("fromRange"));
			toRange = Integer.parseInt(gconf.getProperty("toRange"));
			if( minRange > fromRange ) {
				minRange = fromRange;
			}
			if( maxRange < toRange ) {
				maxRange = toRange;
			}
			
			map.put(toRange, gconf.getProperty("node.id"));
		}
	}
	
	/**
	 * Returns the node.id of the server that will
	 * have the data of this emailid
	 * @param emailId
	 * @return
	 */
	public String hash(String emailId) {
		//int hashValue = emailId.length() * 100;
		String hexMD5 = getMD5hex(emailId);
		int hashValue = (int) hexMD5.charAt(0);
		return map.get(map.ceilingKey(hashValue));
	}
	
	private String getMD5hex(String email){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		md.update(email.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(Integer.toHexString((int) (b & 0xff)));
		}

		System.out.println("original:" + email);
		System.out.println("digested:" + digest);
		System.out.println("digested(hex):" + sb.toString());
		return sb.toString();
	}
}
