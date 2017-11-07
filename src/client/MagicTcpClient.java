package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
//import java.util.Scanner;

import common.Card;


public class MagicTcpClient extends AbstractMagicClient{
	/** Socket will be our connection to a server */
	Socket clientSocket;

	/**
	 * Creates client with a socket connected to a waiting server
	 *
	 * @param host hostname where the server lives.
	 * @param port port where the server is listening
	 *
	 * @throws IOException if we cannot create a socket.
	 */
	public MagicTcpClient(String host, int port) throws IOException {
		/* 
		 * Create a Socket and initiate the TCP connection between the client and
		 * server. A DNS lookup will actually be performed before the TCP connection is
		 * initiated, but that is done for us.
		 */
		clientSocket = new Socket(host, port);
	}

	/**
	 * Get some information from the user, send it to a server, get a response, and then
	 * print out the response. Make many assumptions.  Example purposes only!
	 *
	 * @throws IOException if something goes wrong with out socket.
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public void go() throws IOException, ClassNotFoundException {
		
		 ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
		 DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
		 toServer.writeBytes("-a" + "\n");       // write to the server
			


		 ArrayList<Card> testCard = null;
		 testCard = (ArrayList<Card>) inFromServer.readObject();
		 
		 //System.out.println(testCard.toString());
		 for(int i = 0; i<testCard.size(); i++){
			 System.out.println(testCard.get(i));
		 }
		 
		 inFromServer.close();
		 clientSocket.close();
		 
		/*
		// Create a 'stream' connected to the keyboard for user input.
		Scanner scanIn = new Scanner(System.in);

		// Create a 'stream' connected to the server to write data.
		DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

		// Create a 'stream' connected to the server to read data.
		Scanner clientIn = new Scanner(clientSocket.getInputStream());

		
		//while(!sendLine.equals("end")){
			System.out.print("Enter a sentence to send to the server >");
			String sendLine = scanIn.nextLine();        // data from user

			// Why am I sending a newline?
			toServer.writeBytes(sendLine + "\n");       // write to the server
			String recdLine = clientIn.nextLine();      // read from server

			System.out.println("The server sent back:");
			System.out.println("\t" + recdLine);
		//}
		clientSocket.close();                       // close the TCP connection.
		clientIn.close();
		*/
	}


	/**
	 * Create the client, at a default port, and go.
	 * @param args not used.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {

		// localhost because the Server is running on the local machine. A 'client' and
		// 'server' can run on the same host.
		try {
			// What is a port?
			MagicTcpClient client = new MagicTcpClient("localhost", 6789); // What kind of port is this?
			client.go();
		} 
		// Do not turn in code with stack traces turned on. This is for your debugging
		// purposes only!
		catch (IOException ioe ) {
			ioe.printStackTrace();
		}
	}
}
