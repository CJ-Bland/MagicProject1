package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The entry point of the application 
 * @author CJ and Vanessa
 */
public class MagicClientDriver{

	/**The program must accept either two, three, or four Command Line 
	 * Arguments (CLA)s. The first CLA must be either the string “TCP” or 
	 * the string “UDP” (case insensitive). The program will use this CLA to 
	 * determine which of the two client implementations to use. The second 
	 * CLA must be either the IP address or host name of the server to which 
	 * to connect. The third, optional, CLA is either the port number or a 
	 * valid flag. If the port number is not supplied, a default value should 
	 * be used. The fourth, optional, CLA is a flag to send to your version of 
	 * the Magic server. If the fourth CLA is present, the third CLA should be 
	 * assumed to be a port number.
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args){
		
		/**	The name of the server*/
		InetAddress clientHost = null;
		
		/**	The port on the server*/
		int defaultPort = 6789;
		
		/**	A field to determine if a flag was given or if the program should 
		 * use the default flag, -A*/
		boolean isFlag = false;

		if(args.length == 0 || args.length == 1){ // If the use enters in the wrong # of arguments
			System.out.println("Usage: \t<Protocol> <IP Address or Hostname> [<Port Number or Flag>] [<Flag>]");
			System.exit(1);
		} else {
			args[0] = args[0].toUpperCase(); // Makes the protocol upper case
			if(!args[0].equals("TCP") && !args[0].equals("UDP")){
				System.out.println("Please enter in either TCP or UDP for <Protocol>\nUsage: \t<Protocol> <IP Address or Hostname> [<Port Number>] [<Flag>]");
				System.exit(1);
			} else {
				try {
					InetAddress hostAddress = InetAddress.getByName(args[1]);
					clientHost = hostAddress;

				} catch (UnknownHostException e) {
					System.out.println("Unknown Host Exception");
					System.exit(1);
				} 

				if (args.length == 3){ // If user enters in 3 CMA
					if(args[2].startsWith("-")){ // If the 3rd CMA is a flag i.s -A -L
						String upper = args[2].toUpperCase();
						if(upper.equals("-A") || upper.equals("-L") || upper.equals("-C")
								|| upper.equals("-S") || upper.equals("-LC") || upper.equals("-CL") 
								|| upper.equals("-SL") || upper.equals("-LS") || upper.equals("-SC") || upper.equals("-CS")){
							isFlag = true;
						} else {
							System.out.println("Please enter a valid flag: -A, -L, -C, -S, -LC, -CL, -LS, -SL, -SC, -CS\n"
									+ "Usage: \t<Protocol> <IP Address or Hostname> [<Port Number or Flag>] [<Flag>]");
							System.exit(1);
						}
					} else { // If its not a flag then its a port.
						defaultPort = Integer.parseInt(args[2]);
					}
				}

				if(args.length == 4){ // If the user enters in 4 CMA
					defaultPort = Integer.parseInt(args[2]); // Port is assumed to be the 3rd CMA
					String upper = args[3].toUpperCase();
					if(upper.equals("-A") || upper.equals("-L") || upper.equals("-C")
							|| upper.equals("-S") || upper.equals("-LC") || upper.equals("-CL") 
							|| upper.equals("-SL") || upper.equals("-LS") || upper.equals("-SC") || upper.equals("-CS")){
						isFlag = true;
					} else {
						System.out.println("Please enter a valid flag: -A, -L, -C, -S, -LC, -CL, -LS, -SL, -SC, -CS\n"
								+ "Usage: \t<Protocol> <IP Address or Hostname> [<Port Number or Flag>] [<Flag>]");
						System.exit(1);
					}
				}
			}
		}



		try {
			if (args[0].equals("TCP")){
				if (args.length == 2){
					MagicClient mc = new MagicTcpClient(clientHost, defaultPort, "-A");
					mc.printToStream(System.out);

				}
				if (args.length == 3){
					if (isFlag == true){ // If User enters in just a flag
						MagicClient mc = new MagicTcpClient(clientHost, defaultPort, args[2]);
						mc.printToStream(System.out);

					} else { // If User enters in just a port number
						int tmp = Integer.parseInt(args[2]);
						MagicClient mc = new MagicTcpClient(clientHost, tmp, "-A");
						mc.printToStream(System.out);
					}
				}
				if (args.length == 4){
					MagicClient mc = new MagicTcpClient(clientHost, defaultPort, args[3]);
					mc.printToStream(System.out);
				}
			}

			if (args[0].equals("UDP")){
				if (args.length == 2){
					MagicClient mc = new MagicUdpClient(clientHost, defaultPort, "-A");
					mc.printToStream(System.out);

				}
				if (args.length == 3){
					if (isFlag == true){ // If User enters in just a flag
						MagicClient mc = new MagicUdpClient(clientHost, defaultPort, args[2]);
						mc.printToStream(System.out);

					} else { // If User enters in just a port number
						int tmp = Integer.parseInt(args[2]);
						MagicClient mc = new MagicUdpClient(clientHost, tmp, "-A");
						mc.printToStream(System.out);
					}
				}
				if (args.length == 4){
					MagicClient mc = new MagicUdpClient(clientHost, defaultPort, args[3]);
					mc.printToStream(System.out);
				}
			}
		}
		catch (IOException e) {
			System.out.println("I/O Exception, Can not create Socket, Try Again");
			System.exit(1);
		}

	}
}
