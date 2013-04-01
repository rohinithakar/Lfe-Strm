package poke.server.hash;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import poke.server.conf.JsonUtil;
import poke.server.conf.ServerConf;
//import poke.server.hash.HashingService;
import poke.server.resources.ResourceFactory;


public class HashingServiceTest {

	private static HashingService hashingServiceInstance = null;
	
	@BeforeClass
	public static void init(){
		File cfg = new File("runtime/server.conf");
		ServerConf conf = readConfig(cfg);
		HashingService.initialize(conf);
		hashingServiceInstance = HashingService.getInstance();
	}
	
	private static ServerConf readConfig(File cfg) {
		ServerConf conf = null;
		// resource initialization - how message are processed
		BufferedInputStream br = null;
		try {
			byte[] raw = new byte[(int) cfg.length()];
			br = new BufferedInputStream(new FileInputStream(cfg));
			br.read(raw);
			conf = JsonUtil.decode(new String(raw), ServerConf.class);
			ResourceFactory.initialize(conf);
		} catch (Exception e) {
		}
		return conf;
	}
	
	@Test
	public void testHashForServerOne(){
		Assert.assertEquals("Expected node id one for email of length: 9", hashingServiceInstance.hash("a@abc.com"), "one");
	}
	
	@Test
	public void testHashForServerTwo(){
		Assert.assertEquals("Expected node id two for email of length: 13", hashingServiceInstance.hash("abcabc@abc.com"), "two");
	}
	
	@Test
	public void testHashForServerThree(){
		Assert.assertEquals("Expected node id three for email of length: 23", hashingServiceInstance.hash("abcabcabcabc@abcabc.com"), "three");
	}
	
	@Test
	public void testHashForServerFour(){
		Assert.assertEquals("Expected node id four for email of length: 32", hashingServiceInstance.hash("abcabcabcabcabcabc@abcabcabc.com"), "four");
	}
}
