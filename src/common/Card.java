package common;

import java.io.Serializable;

import common.CardType.Type;

/**
 * A class that represents Magic: The Gathering cards
 * @author CJ and Vanessa
 */
public class Card implements Serializable{

	/** The amount of mana each card is worth*/
	public String mana; 

	/** The name of the card*/
	public String cardName;

	/** The id number of each card*/
	public short id; 

	/** The type of the card*/
	public CardType.Type type;

	/**
	 * A constructor to create a card 
	 * 
	 * @param id the id number
	 * @param name the name
	 * @param type the type
	 * @param mana the mana amount and type
	 */
	public Card(short id, String name, CardType.Type type, String mana){
		this.id = id;
		this.cardName = name;
		this.type = type;
		this.mana = mana;
	}

	/**
	 * A constructor to create a card, turns a string type into 
	 * one from the Type Enum
	 * 
	 * @param id the id number
	 * @param name the name
	 * @param type the type
	 * @param mana the mana amount and type
	 */
	public Card(short id, String name, String type, String mana){
		this.id = id;
		this.cardName = name;
		assignType(type);
		this.mana = mana;
	}

	/**
	 * A constructor to create a card, turns a string id into a short and
	 * a string type into one from the Type Enum
	 * 
	 * @param id the id number
	 * @param name the name
	 * @param type the type
	 * @param mana the mana amount and type
	 */
	public Card(String id, String name, String type, String mana){
		short id1 = Short.parseShort(id);
		this.id = id1;
		this.cardName = name;
		assignType(type);
		this.mana = mana;

	}

	/**
	 * A getter to get the id
	 * 
	 * @return the current id
	 */
	public short getId(){
		return id;
	}

	/**
	 * A getter to get the name
	 * 
	 * @return the current name
	 */
	public String getName(){
		return cardName;
	}

	/**
	 * A helper method to take a given string and match it to a type from
	 * the Type enum
	 * 
	 * @param info the type to be checked
	 */
	private void assignType(String info){
		String first = info.substring(0, 1);
		first.toUpperCase();

		if(first.equals("A")){
			setType(CardType.Type.ARTIFACT);
		}
		else if (first.equals("C")){
			setType(CardType.Type.CREATURE);
		}
		else if (first.equals("B") || first.equals("L")){
			setType(CardType.Type.LAND);
		}
		else if (first.equals("S") || first.equals("E") || first.equals("I")){
			setType(CardType.Type.SPELL);
		}
		else{
			setType(CardType.Type.UNKNOWN);
		}

	}

	/**
	 * A getter for the type
	 * 
	 * @return current type
	 */
	public CardType.Type getType(){
		return type;
	}

	/**
	 * A setter to set the type
	 * 
	 * @param type the new type
	 */
	public void setType(CardType.Type type){
		this.type = type;
	}

	/**
	 * A to string to print out the Card info neatly
	 */
	public String toString(){
		String s = "";
		s = this.id + "\t" + this.cardName + "\t" + this.mana + "\t" + this.type;    	
		return String.format("%30s:%10s (%5s)", this.cardName, this.type, this.mana);
	}
}
