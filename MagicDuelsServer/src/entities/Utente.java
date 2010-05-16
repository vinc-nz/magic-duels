package entities;

import java.io.Serializable;
import java.net.InetAddress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Utente
 *
 */
@Entity
@Table(name="Utente")
public class Utente implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nome")
	String nome;
	
	@Column(name="password")
	String password;
	
	@Column(name="mail")
	String mail;
	
	@Column(name="iscritto")
	Long iscritto;

	@Column(name="ip_iscrizione")
	InetAddress ip_iscrizione;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Long getIscritto() {
		return iscritto;
	}

	public void setIscritto(Long iscritto) {
		this.iscritto = iscritto;
	}

	public InetAddress getIp_iscrizione() {
		return ip_iscrizione;
	}

	public void setIp_iscrizione(InetAddress ipIscrizione) {
		ip_iscrizione = ipIscrizione;
	}
	
}
