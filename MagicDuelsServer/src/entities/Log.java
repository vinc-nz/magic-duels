package entities;

import java.io.Serializable;
import java.net.Inet4Address;
import java.security.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Log
 *
 */
@Entity
@Table(name="Log")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	int id;
	
	@JoinColumn(name="utente_id")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	Utente utente;
	
	@Column(name="timestamp")
	Timestamp timestamp;
	
	@Column(name="ip")
	Inet4Address ip;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public Inet4Address getIp() {
		return ip;
	}
	public void setIp(Inet4Address ip) {
		this.ip = ip;
	}	
	
}
