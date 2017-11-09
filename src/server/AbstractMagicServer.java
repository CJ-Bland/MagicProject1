package server;

import java.io.IOException;
import java.util.ArrayList;

import common.CardType;
import common.CardType.Type;

/**
 * An abstract class that implements the MagicServer interface. This class includes
 * attributes that are common to all magic server implementations, as well as 
 * protected methods that enable subclasses to access those attributes
 * 
 * @author CJ and Vanessa  
 */
public abstract class AbstractMagicServer implements MagicServer{

	/** The port on the server*/
	private int port;
	
	/** A source of cards*/
	private CardSource source;
	
	/** An array list of types to be searched for*/
	private ArrayList<CardType.Type> types = new ArrayList<Type>();
	
	/** The number of cards to be returned*/
	private int numItems;
	
	/**
	 * A default constructor 
	 * 
	 * @param port the port number of the server
	 */
	public AbstractMagicServer(int port){
		this.port = port;
	}

	/**
	 * A getter for the port field
	 * @return port
	 */
	protected int getPort(){
		return port;
	}

	/**
	 * A getter for the source field
	 * @return source
	 */
	protected CardSource getSource(){
		return source;
	}

	/**
	 * A getter for the types field
	 * @return types
	 */
	protected ArrayList<CardType.Type> getTypes(){
		return types;
	}

	/**
	 * A setter for the source field
	 * @param source the new source
	 */
	protected void changeSource(CardSource source){
		this.source = source;
	}

	/**
	 * A setter for the numItems field
	 * @param numItems the new number
	 */
	protected void changeItemsToSend(int numItems){
		this.numItems = numItems;
	}

	/**
	 * A getter for the numItems field
	 * @return numItems
	 */
	protected int getItemToSend(){
		return numItems;
	}

	/**
	 * Using the given flag, this method determines how many cards to 
	 * send and of what type they should be
	 * 
	 * @param command the flag specified by the client
	 */
	protected void setCardsReturned(String command){
		//Have to trim command because udp adds junk characters to the end of the string
		String flag = command.toUpperCase().trim();
		
		if(types.size()!= 0){
			types.clear();
		}

		if(flag.equals("-A")){		
			numItems = 60;
			//Chose to set type to artifact, instead of adding 3 types, in 
			//make deck will check for artifact type and then choose only
			//creature land and spell, since artifact is not used
			types.add(CardType.Type.ARTIFACT);
		}
		else if(flag.equals("-L")){		
			numItems = 20;	
			types.add(CardType.Type.LAND);
			//source.setCardType(CardType.Type.LAND);
		}
		else if (flag.equals("-S")){		
			numItems = 20;	
			types.add(CardType.Type.SPELL);
			//source.setCardType(CardType.Type.SPELL);
		}
		else if (flag.equals("-C")){
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

	/**
	 * The method that listens for requests from clients, implementation depends
	 * on what extends this class
	 */
	public void listen() throws IOException, MagicServerException{

	}
}
