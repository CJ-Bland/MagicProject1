package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
//import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;

import common.Card;
import common.CardType;

/**
 * A card which creates a deck of magic cards from a file and creates
 * a random subdeck based on which flag it receives
 * 
 * @author CJ and Vanessa 
 */
public class CardSource {
	
	/**	An arrayList which holds all the cards read in from the file*/
	private static ArrayList<Card> deck;
	
	/**	A scanner to read in the file*/
	private Scanner input;
	
	/**	Used to randomly generate numbers*/
	private Random generator;
	
	/**	Since next or valid were not used, neither was this field*/
	private CardType.Type type;

	/**	The name of the file we use to create all the cards*/
	private String filename = "src/server/cards.csv";

	/**
	 * A constructor to initialize the fields and read the file and create
	 * the deck
	 * 
	 * @throws FileNotFoundException if the file we are trying to read doesn't exist
	 */
	public CardSource() throws FileNotFoundException{			
		FileReader fileReader = new FileReader(filename);
		input = new Scanner(fileReader);
		deck = new ArrayList<>();
		createDeck();
		input = new Scanner(System.in);
		generator = new Random();
	}	

	//next() and valid() were not used in my implementation
	
	/**
	 * A setter to set the type of a card to the value of the given parameter
	 * 
	 * @param newType the new type to be set
	 */
	protected void setCardType(CardType.Type newType){
		type = newType;
	}

	/**
	 * Reads in data from the file, creates Card objects, and stores them in 
	 * the deck arrayList
	 * 
	 * @throws FileNotFoundException
	 */
	private void createDeck() throws FileNotFoundException{
		String line = "";
		
		while(input.hasNextLine()){
			line = input.nextLine();
			String[] parsed = line.split(",");
			short id = Short.parseShort(parsed[0]);

			//Card params are id, name, type, mana
			Card temp = new Card(id, parsed[1], parsed[2], parsed[3]);
			deck.add(temp);			
		}
		input.close();
	}

	/**This method takes the place of next. Using the given parameters,
	 * creates a random deck of given size and types. Also, based on the 
	 * specifications in the RFC, no decks will be created with artifact 
	 * or unknown cards
	 * 
	 * @param size the number of cards to be selected
	 * @param subtype the types of cards to be selected
	 * 
	 * @return A randomly generated deck
	 */	
	public ArrayList<Card> makeDeck(int size, ArrayList<CardType.Type> subtype){
			
		ArrayList<Card> subdeck = new ArrayList<Card>();
		
		/*Instead of setting 3 types and searching for cards that match those
		  this part picks cards that aren't artifact (which is basically the 
		  same thing*/
		if(subtype.get(0).equals(CardType.Type.ARTIFACT)){			
			for(int i = 0; i < deck.size(); i++){
				if(!deck.get(i).getType().equals(CardType.Type.ARTIFACT)){
					subdeck.add(deck.get(i));
				}
			}
		}
		else if(subtype.size() == 1){
			CardType.Type subtype1 = subtype.get(0);
			
			for(int i = 0; i < deck.size(); i++){
				if(deck.get(i).getType().equals(subtype1)){
					subdeck.add(deck.get(i));
				}
			}
		}
		else if(subtype.size() == 2){
			CardType.Type subtype1 = subtype.get(0);
			CardType.Type subtype2 = subtype.get(1);			
			
			for(int i = 0; i < deck.size(); i++){
				if(deck.get(i).getType().equals(subtype1) || deck.get(i).getType().equals(subtype2)){
					subdeck.add(deck.get(i));
				}
			}
		}		

		ArrayList<Card> ran = new ArrayList<Card>();
		
		for(int i = 0; i<size; i++){			
			int random = generator.nextInt(subdeck.size());
			ran.add(subdeck.get(random));
		}		
		return ran;
	}
}
