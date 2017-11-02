package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpMagicServer {

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
	 * Accept connections from clients, Get data they send and send it back
	 * modified. Makes MANY assumptions. -- <b>For example puposes only!</b>
	 *
	 * @throws IOException if we have problems with our sockets.
	 */
	public void go() throws IOException {

		// THIS IS NOT A VALID WAY TO WAIT FOR SOCKET CONNECTIONS!, You should
		// not have a forever loop or while(true) 

		welcomeSocket.setSoTimeout(10000);
		//DataOutputStream clientOut;
		//Scanner clientIn;    	
		try{
			while(welcomeSocket.getSoTimeout()>0){
				//for (; ;) {
				System.out.println("Waiting for client on port " +
						welcomeSocket.getLocalPort() + "...");
				// Accept a connection, and create a new 'direct' socket
				// This socket has the same port as the welcome socket.
				Socket connectionSocket = welcomeSocket.accept();

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
					break;
				}

				clientOut.writeBytes(message + "\n");
				//clientIn.close();
			}
		}
		catch(SocketTimeoutException stoe){
			System.out.println("The server has timed out, please try again later");
		}
		//System.out.println("Exited loop" + "\n");
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
			server.go();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}
}
