package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;

import common.Card;
import common.CardType;

public class CardSource {
	private static ArrayList<Card> deck;
	private Scanner input;
	private Random generator;
	private CardType type;
	
	private Card current;
	private String filename = "src/server/cards.csv";
	
	public CardSource() throws FileNotFoundException{						
		
		FileReader fileReader = new FileReader(filename);
		input = new Scanner(fileReader);
		deck = new ArrayList<>();
		makeDeck();
		input = new Scanner(System.in);
		generator = new Random();	
		current = deck.get(0);
	}
	
	public Card next(){
		Card next;
		if(current.getId() != deck.size()-1){
			next = deck.get(current.getId() + 1);
		}
		else{
			next = deck.get(0);
		}
		return next;
	}
	
	private boolean valid(Card card){
		boolean answer = false;
		
		for(CardType.Type c: CardType.Type.values()){
			if(card.getType().equals(c.name())){
				answer = true;
			}			
		}
		
		return answer;
	}
	
	protected void setCardType(CardType newType){
		type = newType;
	}
	
	private void makeDeck() throws FileNotFoundException{
		String line = "";
		//String filename = "src/server/cards.csv";					
		
		/*FileReader fileReader = new FileReader(filename);
		Scanner fileScanner = new Scanner(fileReader);*/
		
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
	
	private void printDeck(){
		/*System.out.println("Whole Deck \n");
		for(int i = 0; i < deck.size(); i++){
			System.out.println(deck.get(i).toString());
		}*/
		
		CardType.Type subtype1 = CardType.Type.ARTIFACT;
		CardType.Type subtype2 = CardType.Type.SPELL;		
		System.out.println("Type of " + subtype1 + " and " + subtype2);
		
		ArrayList<Card> subdeck = new ArrayList();
		for(int i = 0; i < deck.size(); i++){
			if(deck.get(i).getType().equals(subtype1) || deck.get(i).getType().equals(subtype2)){
				subdeck.add(deck.get(i));
			}
		}
		
		ArrayList<Card> ran = new ArrayList();
		int size = 20;
		for(int i = 0; i<size; i++){			
			int random = generator.nextInt(subdeck.size());
			ran.add(subdeck.get(random));
		}
		
		System.out.println("Random Deck of " + size +"\n");
		for(int i = 0; i < ran.size(); i++){
			System.out.println(ran.get(i).toString());
		}
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException{
		CardSource c = new CardSource();
		c.printDeck();
		
		
		/*
		String line = "";
		String filename = "src/server/cards.csv";					
		
		FileReader fileReader = new FileReader(filename);
		Scanner fileScanner = new Scanner(fileReader);
		
		while(fileScanner.hasNextLine()){
			line = fileScanner.nextLine();
			String[] parsed = line.split(",");
			
			short id = Short.parseShort(parsed[0]);
			
			//Card params are id, name, type, mana
			Card temp = new Card(id, parsed[1], parsed[2], parsed[3]);
			deck.add(temp);

			
			
			
		}
		for(int i = 0; i < deck.size(); i++){
			System.out.println(deck.get(i).toString());
		}
		
		fileScanner.close();
		*/
	}



}
