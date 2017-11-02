package server;

public abstract class AbstractMagicServer {

	private int port;
	private CardSource source;
	private int numItems;
	
	protected int getPort(){
		return port;
	}
	
	protected CardSource getSource(){
		return source;
	}
	
	protected void changeSource(CardSource source){
		
	}
	
	protected void changeItemsToSend(int numItems){
		
	}
	
	protected int getItemToSend(){
		return numItems;
	}
	
	protected void setCardsReturned(String command){
		
	}
	
	public void listen(){
		
	}
}
