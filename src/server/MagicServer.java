package server;

import java.io.IOException;

/**
 * The interface to a magic server implementation
 * @author CJ and Vanessa
 */
public interface MagicServer{

	/**
	 * A method to be filled in by classes that implement this interface
	 * 
	 * @throws MagicServerException
	 * @throws IOException
	 */
	public void listen()throws MagicServerException, IOException;
}
