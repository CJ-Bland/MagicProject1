package server;

import java.io.IOException;
import java.util.ArrayList;

import common.CardType;
import common.CardType.Type;

public abstract class AbstractMagicServer {

	private int port;
	private CardSource source;
	private ArrayList<CardType.Type> types = new ArrayList<Type>();
	private int numItems;

	protected int getPort(){
		return port;
	}

	protected CardSource getSource(){
		return source;
	}

	protected ArrayList<CardType.Type> getTypes(){
		return types;
	}

	protected void changeSource(CardSource source){
		this.source = source;
	}

	protected void changeItemsToSend(int numItems){
		this.numItems = numItems;
	}

	protected int getItemToSend(){
		return numItems;
	}

	protected void setCardsReturned(String command){
		String flag = command.toUpperCase();
		//System.out.println(flag);

		if(types.size()!= 0){
			types.clear();
		}

		if(flag.equals("-A")){
		//if(flag.startsWith("-A")){
			numItems = 60;
			//Chose to set type to artifact, instead of adding 3 types, in 
			//make deck will check for artifact type and then choose only
			//creature land and spell, since artifact is not used
			types.add(CardType.Type.ARTIFACT);
		}
		//else if(flag.equals("-L")){
		else if(flag.startsWith("-L")){
			numItems = 20;	
			types.add(CardType.Type.LAND);
			//source.setCardType(CardType.Type.LAND);
		}
		//else if (flag.equals("-S")){
		else if (flag.startsWith("-S")){
			numItems = 20;	
			types.add(CardType.Type.SPELL);
			//source.setCardType(CardType.Type.SPELL);
		}
		//else if (flag.equals("-C")){
		else if (flag.startsWith("-C")){
			numItems = 20;	
			types.add(CardType.Type.CREATURE);
			//source.setCardType(CardType.Type.CREATURE);
		}
		else if(flag.equals("-LS") || flag.equals("-SL")){
			numItems = 40;
			types.add(CardType.Type.LAND);
			types.add(CardType.Type.SPELL);
		}
		else if(flag.equals("-CL") || flag.equals("-LC")){
			numItems = 40;
			types.add(CardType.Type.CREATURE);
			types.add(CardType.Type.LAND);
		}
		else if(flag.equals("-CS") || flag.equals("-SC")){		
			numItems = 40;
			types.add(CardType.Type.CREATURE);
			types.add(CardType.Type.SPELL);
		}

		

	}

	public void listen() throws IOException{

	}
}
