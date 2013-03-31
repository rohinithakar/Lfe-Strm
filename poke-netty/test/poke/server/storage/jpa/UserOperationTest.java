package poke.server.storage.jpa;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Assert;

public class UserOperationTest {
	private static UserOperation userOperation = null;
	
	@BeforeClass
	public static void init(){
		userOperation = new UserOperation("jpa_spatial");
	}
	
	@Test
	public void testRegister(){
		Assert.assertTrue("Expecting successful insertion operation", userOperation.registerUser("b@cba.com", "abc", "xyz", "1234"));
	}
	
	@Test
	public void testLogin(){
		Assert.assertTrue("Expecting successful login for user inserted in testRegister", userOperation.login("b@cba.com", "1234"));
	}
}
