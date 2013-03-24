package poke.server.storage.jpa;

import java.io.*;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;

public class UserOperation {
	EntityManagerFactory emf;
	EntityManager em;

	public UserOperation(String puName){
		Properties pros = new Properties();

		pros.setProperty(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, 
		                 "META-INF/persistence.xml");
		emf = Persistence.createEntityManagerFactory(puName, pros);
		em=emf.createEntityManager();
	}
	
	public boolean registerUser(String emailId, String fname, String lname, String password){
		boolean success = true;
		try{
			UserInfo userInfo = new UserInfo(emailId, fname, lname, password);
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			em.persist(userInfo);
			tx.commit();
		}catch(Exception e){
			success = false;
		}
		return success;
	}
	
	public UserInfo getUser(String email){
		UserInfo userinfo = null;
		Query pquery = em.createNamedQuery("getPassword");
		pquery.setParameter("id",email);
		List<?> presult = pquery.getResultList();
		for(Object obj : presult)
		{
			userinfo = (UserInfo)obj;
			//String password=userinfo.getPassword();
		}
		em.close();
		return userinfo;
		
	}
	
}
