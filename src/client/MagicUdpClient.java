package client;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

import common.Card;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class MagicUdpClient extends AbstractMagicClient{

	private DatagramSocket clientSocket;    

	/**
	 * Creates client with a DatagramSocket
	 *
	 * @throws IOException if we cannot create a socket.
	 */
	public MagicUdpClient() throws IOException {
		// Does not initiate a TCP connection. The host does NOT contact the
		// server, hence the lack of hostname or port number. It creates 
		// a 'door' not a 'pipe'.
		clientSocket = new DatagramSocket();
	}

	/**
	 * Get some information from the user, send it to a server, get a
	 * response, and then print out the response. Make many assumptions.
	 * Example purposes only!
	 *
	 * @throws IOException if something goes wrong with out socket.
	 * @throws ClassNotFoundException 
	 */
	public void go(String host, int port) throws IOException, ClassNotFoundException {

		/*Scanner     scanIn      = new Scanner(System.in);  

	        System.out.print("Enter a sentence to send to the server >");
	        String sendLine = scanIn.nextLine();           // data from user
		 */
		String sendLine = "-s";
		// Holds data to send to the client, byte is a primitive type that 
		// holds a byte, surprisingly enough :)
		byte[]      sendData    = new byte[1024];

		// Invoke a DNS query that translates the hostname into an IP address.
		InetAddress IPAddress   = InetAddress.getByName(host);

		// Convert a string into a byte array
		sendData = sendLine.getBytes();

		// Construct the packet to push through the socket: Data, the length
		// of the data, the Dest IP address and the port number.
		DatagramPacket sendPacket = new DatagramPacket(sendData, 
				sendData.length,
				IPAddress, port);

		// Push the data into the socket.
		clientSocket.send(sendPacket);
		byte[] data;

		// Create storage to receive a packet from the server.
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,
				receiveData.length);

		// The client idles until it receives data from the server.
		clientSocket.receive(receivePacket);	        	      
		data = receivePacket.getData();

		do{


			//System.out.println(data.toString());

			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			try {
				Card card = (Card) is.readObject();
				System.out.println(card.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// Create storage to receive a packet from the server.
			receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			// The client idles until it receives data from the server.
			clientSocket.receive(receivePacket);	        	      
			data = receivePacket.getData();
		}
		while(data.length!=0);		

		/*receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			clientSocket.receive(receivePacket);
			data = receivePacket.getData();
			System.out.println(data.toString());
			in = new ByteArrayInputStream(data);
			is = new ObjectInputStream(in);
			try {
				Card card = (Card) is.readObject();
				System.out.println("Card:" + card.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}	*/	
		clientSocket.close(); 
	}

	public static void main(String[] args) throws ClassNotFoundException {

		try {
			MagicUdpClient client = new MagicUdpClient();
			client.go("localhost", 9876);
		} 
		catch (IOException ioe ) {
			ioe.printStackTrace();
		}
	}
}
