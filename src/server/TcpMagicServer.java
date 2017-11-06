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
			deck = test.makeDeck(getItemToSend(), getTypes());
			
			/*if(clientLine.toUpperCase().equals("-A")){
				deck = test.makeDeck(60, null);
			}
			else if(clientLine.toUpperCase().equals("-L")){
				deck = test.makeDeck(20, CardType.Type.LAND);
			}
			else if(clientLine.toUpperCase().equals("-C")){
				deck = test.makeDeck(20, CardType.Type.CREATURE);
			}
			else if(clientLine.toUpperCase().equals("-S")){
				deck = test.makeDeck(20, CardType.Type.SPELL);
			}
			else {
				deck = test.makeDeck(20, CardType.Type.UNKNOWN);
			}*/

			outToClient.writeObject(deck);

			DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());
			clientOut.writeBytes("\n");

			//outToClient.close();
			//clientOut.close();
		}
		/*

				// create a Scanner (stream) connected to the client's socket
				Scanner clientIn = new Scanner(connectionSocket.getInputStream());
				DataOutputStream clientOut = new DataOutputStream(connectionSocket.getOutputStream());

				// read from the socket
				String clientLine = clientIn.nextLine();

				// modify the data and send it back though the socket.
				// don't forget the newline, the client expects one!
				String modLine = clientLine.toUpperCase();
				String message = "";


				if(modLine.equals("-A")){
					message = "Will return 60 cards of all types";
				}
				else if(modLine.equals("-L")){
					message = "Will return 20 cards of Land type";
				}
				else if(modLine.equals("-C")){
					message = "Will return 20 cards of Creature type";
				}
				else if(modLine.equals("-S")){
					message = "Will return 20 cards of Spell type";
				}
				else{
					message = "Error, not an acceptable param";
					//break;
				}

				clientOut.writeBytes(message + "\n");
				//clientIn.close();
			}


		//System.out.println("Exited loop" + "\n");
		//clientIn.close();
		 */
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
