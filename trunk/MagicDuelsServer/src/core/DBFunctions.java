package core;

import java.net.InetAddress;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Utente;

public class DBFunctions {

	public static EntityManager getEntityManager()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPASE2");
		return emf.createEntityManager();
	}
	
	public static void iscrivi(String nome, String password, String mail, InetAddress ip)
	{
		EntityManager em = DBFunctions.getEntityManager();
		
		try
		{
			em.getTransaction().begin();
			Utente nuovo = new Utente();
			nuovo.setNome(nome);
			nuovo.setPassword(password);
			nuovo.setMail(mail);
			nuovo.setIscritto(new Date().getTime());
			nuovo.setIp_iscrizione(ip);
			em.persist(nuovo);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		
		
	}
	
}
