package server;

import java.util.ArrayList;
import java.util.Scanner;

import common.Card;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UdpMagicServer extends AbstractMagicServer{

	/** Socket to the known universe (A.K.A. The Internet). */
	private DatagramSocket serverSocket; 


	/**
	 * Create a Server with a socket.
	 *
	 * @param port port where the server is listening
	 *
	 * @throws IOException if we cannot create a socket.
	 */
	public UdpMagicServer(int port) throws IOException {
		/* 
		 * Note there is no listening socket, just a nice all purpose socket through which
		 * data, from possibly multiple different clients, will come through.
		 */
		serverSocket = new DatagramSocket(port);
	}

	/**
	 * Get data from a client, modify it and send it back modified. Makes MANY assumptions. --
	 * example puposes only!
	 *
	 * @throws IOException if we have problems with our sockets.
	 */
	public void go() throws IOException {
		boolean checker = true;
		int cards = 0;
		//for (; ;) { // SADNESS :(
		while(checker){
			//Card card = new Card("1", "Name", "Spell", "Mana");

			// create a packet to get some tasty data.
			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// get some data through the socket
			serverSocket.receive(receivePacket);

			// change the data portion byte array, into a string, I assume that this is legal.
			String clientLine = new String(receivePacket.getData());
			//System.out.println(clientLine);

			CardSource test = new CardSource();			
			setCardsReturned(clientLine);
			//System.out.println(getItemToSend() + " "+ getTypes().toString());
			ArrayList<Card> deck = null;
			deck= test.makeDeck(getItemToSend(), getTypes());

			/*for(int i = 0; i<deck.size(); i++){
				System.out.println(deck.get(i));
			}*/
			//System.out.println(deck.size());


			//byte[] data = receivePacket.getData();

			//ByteArrayInputStream in = new ByteArrayInputStream(data);
			//ObjectInputStream is = new ObjectInputStream(in);

			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			byte[] incomingData = new byte[1024];
			//Card card = (Card) is.readObject();				
			
			for(int i = 0; i<deck.size(); i++){
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(deck.get(i));			

				byte[] data = outputStream.toByteArray();
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port /*9876*/);			
				serverSocket.send(sendPacket);
				//System.out.println("Sent:" + deck.get(i).toString());
				cards++;
			}
			if(cards == deck.size()){
				checker = false;
			}
			//sending empty packet so the client knows its over
			byte[] end = new byte[0];
			DatagramPacket sendPacket = new DatagramPacket(end, 0, IPAddress, port /*9876*/);			
			serverSocket.send(sendPacket);
			
			// Modify the data and send it back though the socket.
			/*String modLine  = clientLine.toUpperCase();
			byte[] sendData = modLine.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 
					port);
			serverSocket.send(sendPacket);	  */          
		}
	}

	public static void main(String[] args){

		try {
			UdpMagicServer server = new UdpMagicServer(9876);
			server.go();
		}
		catch(IOException ioe) {
			ioe.printStackTrace(); // Only for debugging purposes. Remove these and put nice
			// informative error messages.
		}
	}
}
