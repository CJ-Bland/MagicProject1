package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CardSource {
	private static ArrayList<Card> deck = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException{

		String line = "";
		String filename = "src/server/cards.csv";					
		
		FileReader fileReader = new FileReader(filename);
		Scanner fileScanner = new Scanner(fileReader);
		
		while(fileScanner.hasNextLine()){
			line = fileScanner.nextLine();
			String[] parsed = line.split(",");
			
			short id = Short.parseShort(parsed[0]);
			Card temp = new Card(id, parsed[1], parsed[2], parsed[3]);
			deck.add(temp);

			
			
			
		}
		for(int i = 0; i < deck.size(); i++){
			System.out.println(deck.get(i).toString());
		}
		
		fileScanner.close();
	}



}
