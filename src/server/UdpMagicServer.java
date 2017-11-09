package server;

import java.util.ArrayList;
//import java.util.Scanner;

import common.Card;

//import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * a class that extends AbstractMagicServer and provides a concrete implementation 
 * of the listen() method using UDP. The method repeatedly waits for a datagram 
 * from a client and responds appropriately
 * 
 * @author CJ and Vanessa
 */
public class UdpMagicServer extends AbstractMagicServer{

	/** Socket to connect to anything that contacts us */
	private DatagramSocket serverSocket; 

	/**
	 * Constructor to create a socket.
	 *
	 * @param port port where the server is listening
	 * 
	 * @throws SocketException if something goes wrong with the socket
	 * @throws IOException if we cannot create a socket.
	 */
	public UdpMagicServer(int port) throws SocketException{
		super(port);		
		serverSocket = new DatagramSocket(port);
	}

	/**
	 * Listens for a connection from a client, gets a datagram packet, gets
	 * the flag out of it, sends that flag to the abstractMagicServer class,
	 * gets a deck of randomly selected cards that fit the criteria of the 
	 * flag, and sends that deck card by card back to the client
	 * 
	 * @throws MagicServerException 
	 * @throws IOException if we have issues with the sockets.
	 */
	public void listen() throws MagicServerException{		
		try{
			//Keeps the server alive for 10 minutes. The try block also works as a checker, so
			//there are no forever loops
			serverSocket.setSoTimeout(600000); 
			
			while(serverSocket.getSoTimeout()>0){				
				byte[] receiveData = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String clientLine = new String(receivePacket.getData());
				
				CardSource test = new CardSource();			
				setCardsReturned(clientLine);
				
				ArrayList<Card> deck = null;
				deck= test.makeDeck(getItemToSend(), getTypes());

				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				
				for(int i = 0; i<deck.size(); i++){
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ObjectOutputStream os = new ObjectOutputStream(outputStream);
					os.writeObject(deck.get(i));			

					byte[] data = outputStream.toByteArray();
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port /*9876*/);			
					serverSocket.send(sendPacket);				
				}

				//sending empty packet so the client knows its over
				byte[] end = new byte[0];
				DatagramPacket sendPacket = new DatagramPacket(end, 0, IPAddress, port /*9876*/);			
				serverSocket.send(sendPacket);

			}
		}catch(SocketException e){
			System.out.println("SocketException, please restart");
		}
		catch(SocketTimeoutException ste){
			throw new MagicServerException("SocketTimeOutException, socket timed out, please restart");						
		}
		catch(IOException ioe){
			System.out.println("IOException, please try again");
		}				
	}
}
