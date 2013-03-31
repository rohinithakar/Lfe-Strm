package poke.server.storage.jpa;

import java.io.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserOperation {
	EntityManagerFactory emf;
	EntityManager em;

	public UserOperation(){
		emf=Persistence.createEntityManagerFactory("jpa_spatial");
		em=emf.createEntityManager();
	}
	
	public boolean registerUser(String emailId, String fname, String lname, String password){
		UserInfo userInfo = new UserInfo(emailId, fname, lname, password);
		EntityTransaction tx=em.getTransaction();
		boolean ret=false;
		try{
			tx.begin();
			em.persist(userInfo);
			tx.commit();
			ret=true;
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return ret;
	}
	
}
