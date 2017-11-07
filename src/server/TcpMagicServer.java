package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketTimeoutException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import common.Card;
import common.CardType;

public class TcpMagicServer extends AbstractMagicServer{

	/** Listen for clients knocking at the door (port) */
	ServerSocket welcomeSocket;
	

	/**
	 * Create a Server with a listening socket to accept client connections
	 *
	 * @param port port where the server is listening
	 *
	 * @throws IOException if we cannot create a socket.
	 */
	public TcpMagicServer(int port) throws IOException {
		// socket listens for incoming connections.
		welcomeSocket = new ServerSocket(port);
	}

	/**
	 * Listens to requests from clients
	 * @throws IOException 
	 */
	public void listen() throws IOException {

		// THIS IS NOT A VALID WAY TO WAIT FOR SOCKET CONNECTIONS!, You should
		// not have a forever loop or while(true) 

		//while(welcomeSocket.getSoTimeout()>0){
		for (; ;) {
			System.out.println("Waiting for client on port " +
					welcomeSocket.getLocalPort() + "...");
			// Accept a connection, and create a new 'direct' socket
			// This socket has the same port as the welcome socket.
			Socket connectionSocket = welcomeSocket.accept();

			ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
			Scanner clientIn = new Scanner(connectionSocket.getInputStream());
			String clientLine = clientIn.nextLine();
			System.out.println(clientLine);

			CardSource test = new CardSource();
			ArrayList<Card> deck = null;
			//Card card = new Card("1", "Name", "Spell", "Mana");
			//deck = test.makeDeck(60, null);
			setCardsReturned(clientLine);
			
			//make deck takes the place of next for connections
			deck = test.makeDeck(getItemToSend(), getTypes());						

			outToClient.writeObject(deck);

			DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());
			clientOut.writeBytes("\n");

			//outToClient.close();
			//clientOut.close();
		}		
		
		//clientIn.close();		 
	}

	/**
	 * Main is a method that should be fully commented!
	 *
	 * @param args not used.
	 */
	public static void main(String[] args){

		try {
			TcpMagicServer server = new TcpMagicServer(6789);
			server.listen();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}
}
