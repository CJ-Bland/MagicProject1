package server;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

import common.Card;
import common.CardType;

/**
 * a class that extends AbstractMagicServer and provides a concrete implementation 
 * of the listen() method using TCP. The method repeatedly waits for a client to 
 * connect and responds appropriately.
 * 
 * @author CJ and Vanessa
 */
public class TcpMagicServer extends AbstractMagicServer{

	/** A socket to listen for clients*/
	ServerSocket welcomeSocket;

	/**
	 * Create a Server with a listening socket to accept client connections
	 *
	 * @param port port where the server is listening
	 */	
	public TcpMagicServer(int port) {
		super(port);

		// socket listens for incoming connections.
		try {
			welcomeSocket = new ServerSocket(getPort());				
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException, method was passed an illegal or ina"
					+ "ppropriate argument, Try Again");
			System.exit(1);
		} catch (IOException e){
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}

	}

	/**
	 * Listens to requests from clients
	 * 
	 * @throws MagicServerException 
	 */
	public void listen() throws MagicServerException{ 
		try{
			//Lots of things can go wrong, so the try block and checking to see 
			//if the socket is closed are the conditions to keep running, so
			//there are no forever loops
			while(!welcomeSocket.isClosed()){				
				Socket connectionSocket = welcomeSocket.accept();
				ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
				Scanner clientIn = new Scanner(connectionSocket.getInputStream());
				String clientLine = clientIn.nextLine();

				CardSource test = new CardSource();
				ArrayList<Card> deck = null;
				setCardsReturned(clientLine);

				//make deck takes the place of next for connections
				deck = test.makeDeck(getItemToSend(), getTypes());						

				outToClient.writeObject(deck);

				DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());
				clientOut.writeBytes("\n");

			}
		} catch (InterruptedIOException e){
			throw new MagicServerException("Server Terminated due to Inactivity, try again");
		} catch (SocketException e) {
			System.out.println("SocketException, Error creating/accessing Socket, Try Again");
			System.exit(1);
		} catch (FileNotFoundException e) {
			throw new MagicServerException("SocketException, Specified pathname does not exist, Try Again");						
		} catch (IOException e) {
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}			 
	}
}
