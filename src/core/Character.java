package core;

/* 
 *  MAGO  
 */

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Character {	
	String name;		// Nome
	int lifePoints;		// Punti Vita iniziali
	int energyPoints;	// Mana iniziale
	int attack;			// Attacco
	int defense;		// Difesa
	float speed;		// Velocità
	
	HashMap<String, Spell> spells;  //magie a disposizione

	
	public Character(String name, int lifePoints, int energyPoints, int attack,	int defence, float speed)
	{	
		this.name = name;	
		this.lifePoints = lifePoints;
		this.energyPoints = energyPoints;
		this.attack = attack;
		this.defense = defence;
		this.speed = speed;		
		spells = new HashMap<String, Spell>();
	}
	
	public String getName() {
		return name;
	}
	
	
	//Svuota l'hashmap delle magie e inserisce tutte quelle
	//presenti nella lista data.
	public void setSpells(List<Spell> spellList)
	{
		spells.clear();	
		for(Spell i:spellList)
			spells.put(i.toString(), i);	
	}
	
	// Inserisce una nuova magia all'hashmap.
	public void addSpell(Spell s)
	{	
		spells.put(s.toString(), s);	
	}
	
	
	//restituisce i nomi delle magie
	public Set<String> getSpellsName() {
		return spells.keySet();
	}
	
	
	//true se il mago ha la magia s
	public boolean hasSpell(String s)
	{
		return spells.containsKey(s);	
	}
	
}
