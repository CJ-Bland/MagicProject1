package client;

import java.io.IOException;
import java.io.PrintStream;

/**
 * An interface to a magic client implementation
 * @author CJ and Vanessa
 */
public interface MagicClient {

	/**A method to be filled in by classes that implement this interface**/
	public void printToStream(PrintStream out);

}
