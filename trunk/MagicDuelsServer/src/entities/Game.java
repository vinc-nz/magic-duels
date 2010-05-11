package entities;

import java.io.Serializable;

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
 * Entity implementation class for Entity: Game
 *
 */
@Entity
@Table(name="Game")
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	int id;

	@JoinColumn(name="utente1_id")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	Utente utente1;

	@JoinColumn(name="utente2_id")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	Utente utente2;
	
	@JoinColumn(name="winner_id")
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	Utente winner;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Utente getUtente1() {
		return utente1;
	}

	public void setUtente1(Utente utente1) {
		this.utente1 = utente1;
	}

	public Utente getUtente2() {
		return utente2;
	}

	public void setUtente2(Utente utente2) {
		this.utente2 = utente2;
	}

	public Utente getWinner() {
		return winner;
	}

	public void setWinner(Utente winner) {
		this.winner = winner;
	}
	
}