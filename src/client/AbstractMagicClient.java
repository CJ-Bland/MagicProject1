package client;

import java.io.PrintStream;
import java.net.InetAddress;

/**
 * An abstract class that implements the MagicClient interface.
 * This class includes attributes that are common to all Magic client 
 * implementations, as well as protected methods that enable subclasses 
 * to access those attributes.
 * 
 * @author CJ and Vanessa
 */
public abstract class AbstractMagicClient implements MagicClient {

	/**	The name of the server we will attempt to connect to*/
	private InetAddress host;

	/**	The port on the server we will attempt to connect to*/
	private int port;

	/**	A flag the user specifies, lets the server know how many cards
	 * we want and of what type*/
	private String flag;

	/**
	 * A constructor which sets the fields to given values
	 * @param host the server name
	 * @param port the port on the server
	 * @param flag the instructions
	 */
	public AbstractMagicClient(InetAddress host, int port, String flag) {
		this.host = host;
		this.port = port;
		this.flag = flag;
	}

	/**
	 * A getter for the field host
	 * @return the current host name
	 */
	protected InetAddress getHost(){
		return host;
	}

	/**
	 * A getter for the field port
	 * @return the current port number
	 */
	protected int getPort(){
		return port;
	}

	/**
	 * A getter for the field flag
	 * @return the current flag 
	 */
	protected String getFlag(){
		return flag;
	}

	/**
	 * An abstract method whose implementation depends on which class is extending it
	 */
	public void printToStream(PrintStream out){

	}
}
