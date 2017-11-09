package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
//import java.util.Scanner;

import common.Card;

/**
 * A class that extends AbstractMagicClient and provides a concrete implementation of 
 * the printToStream() method using TCP. Data received from the remote host is printed 
 * to the specified PrintStream.

 * @author CJ and Vanessa
 */
public class MagicTcpClient extends AbstractMagicClient{

	/** The socket that will be our connection to the server */
	Socket clientSocket;

	/**
	 * The constructor which creates the socket according to the given parameters
	 * 
	 * @param clientHost the address of the server
	 * @param clientPort the port on the server
	 * @param clientFlag the flag which tells what cards and how many
	 */
	public MagicTcpClient(InetAddress clientHost, int clientPort, String clientFlag){
		super(clientHost, clientPort, clientFlag);

		try {

			clientSocket = new Socket(clientHost, getPort());

		} catch (IOException e) {
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}
	}

	/**
	 * Get some information from the user, send it to a server, get a response, and then
	 * print out the response. Make many assumptions.  Example purposes only!
	 */
	public void printToStream(PrintStream out){					 
		try {			
			ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
			DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

			toServer.writeBytes(getFlag() + "\n");       // write to the server

			ArrayList<Card> testCard = null;

			/*We typecast to ArrayList since we know that that is what the server
			 * is sending */
			testCard = (ArrayList<Card>) inFromServer.readObject();

			for(int i = 0; i<testCard.size(); i++){
				out.println(testCard.get(i));
			}

			inFromServer.close();
			clientSocket.close();

		} catch(ClassNotFoundException e){
			System.out.println("Class Not Found Exception, Try Again");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}
	}
}
