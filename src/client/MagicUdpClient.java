package client;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

import common.Card;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;

/**
 * MagicTcpClient is a class that extends AbstractMagicClient and provides 
 * a concrete implementation of the printToStream() method using UDP. Data 
 * received from the remote host is printed to the specified PrintStream.
 * @author CJ and Vanessa
 *  */
public class MagicUdpClient extends AbstractMagicClient{

	/** The socket that will be used to send data to the server*/
	private DatagramSocket clientSocket;

	/** The address of the server*/
	private InetAddress host;

	/** The port number on the server*/
	private int port;

	/**
	 * Creates client with a DatagramSocket and initialized the fields using
	 * the given parameters
	 *
	 * @throws IOException if we can't make a socket.
	 * 
	 * @param host the address of the server
	 * @param port the port number of the server
	 * @param flag the flag which tells the server how many cards to send and 
	 * 	of what type 
	 */
	public MagicUdpClient(InetAddress host, int port, String flag) throws IOException {	
		super(host, port, flag);

		clientSocket = new DatagramSocket();
		this.host = host;
		this.port = port;
	}

	/**
	 * Sends the flag to the server, receives the cards, and prints
	 * them out to the screen
	 */
	public void printToStream(PrintStream out) {

		try{		
			String sendLine = getFlag();		
			byte[]      sendData    = new byte[1024];
			sendData = sendLine.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, 
					sendData.length,
					host, port);

			clientSocket.send(sendPacket);
			//byte[] data;
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			clientSocket.receive(receivePacket);	        	      
			byte[] data = receivePacket.getData();

			do{
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);

				try {
					Card card = (Card) is.readObject();
					System.out.println(card.toString());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);

				clientSocket.receive(receivePacket);	        	      
				data = receivePacket.getData();
			}
			while(receivePacket.getLength()!=0);		

			clientSocket.close(); 		
		} catch (IOException e) {
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}
	}
}
