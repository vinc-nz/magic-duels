package core;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entities.Log;
import entities.Utente;

public class DBFunctions {

	public static EntityManager getEntityManager()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPASE2");
		return emf.createEntityManager();
	}
	
	public static boolean iscrivi(String nome, String password, String mail, InetAddress ip)
	{
		EntityManager em = DBFunctions.getEntityManager();
		
		try
		{
			Utente utenteDB = em.find(Utente.class, nome);
			if(utenteDB != null) return false;
			
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
			//e.printStackTrace();
			return false;
		} finally {
			em.close();
			return true;
		}
		
	}
	
	public static Utente logIn(String nome, String password, InetAddress ip)
	{	
		
		Utente utenteDB = DBFunctions.getEntityManager().find(Utente.class, nome);
		
		if(utenteDB != null)
		{
			if(DBFunctions.newConnectionLog((Inet4Address)ip, utenteDB))
				return utenteDB;
			else 
				return null;
		}
		else
			return null;
	}
	
	public static boolean newConnectionLog(InetAddress ip, Utente utente)
	{
		EntityManager em = DBFunctions.getEntityManager();
		
		try
		{
			
			em.getTransaction().begin();
			Log nuovo = new Log();
			nuovo.setIp(ip);
			nuovo.setTimestamp(new Timestamp(new Date().getTime()));
			nuovo.setUtente(utente);
			em.persist(nuovo);
			em.getTransaction().commit();
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
			return true;
		}
		
	}
	
}
