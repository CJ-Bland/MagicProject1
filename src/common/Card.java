package common;

import common.CardType.Type;

public class Card {

	  //Testing
    public String mana; 
    public String cardName;
    public short id; 
    public CardType.Type type;
    
    //Testing
    public Card(short id, String name, CardType.Type type, String mana){
        this.id = id;
        this.cardName = name;
        this.type = type;
        this.mana = mana;
    }
    
    public Card(short id, String name, String type, String mana){
        this.id = id;
        this.cardName = name;
        assignType(type);
        this.mana = mana;
    }
    
    public short getId(){
        return id;
    }

    public String getName(){
        return cardName;
    }
    
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
    
    public CardType.Type getType(){
        return type;
    }
    
    public void setType(CardType.Type type){
        this.type = type;
    }
    
    public String toString(){
    	String s = "";
    	s = this.id + "\t" + this.cardName + "\t" + this.mana + "\t" + this.type;    	
    	return String.format("%30s:%10s (%5s)", this.cardName, this.type, this.mana);
    }
    
	public static void main(String[] args){
		//System.out.println(CardType.Type.SPELL);
		
		
		
	}
}
