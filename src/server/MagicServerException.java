package server;

/**
 * A wrapper exception to catch protocol-specific exceptions
 * @author CJ and Vanessa
 */
public class MagicServerException extends Exception {
	
	MagicServerException(String s){  
		  super(s);  
		 } 
	
}
