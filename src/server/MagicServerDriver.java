package server;

import java.io.IOException;
import java.net.SocketException;

/**
 * The entry point of the application
 * @author CJ and Vanessa
 */
public class MagicServerDriver {
	/**
	 * The program must accept either one or two Command Line Arguments (CMA)s. 
	 * The first must be either the string “TCP” or the string “UDP” (case insensitive). 
	 * The program will use this CMA to determine which server implementation to use. 
	 * The second, optional, parameter is the port number on which the server with 
	 * listen for incoming connections. If this parameter is not supplied, fall back 
	 * to a default port.
	 * 
	 * @param args the command line arguments
	 * @throws SocketException
	 */
	public static void main(String[] args) throws SocketException{
		
		/** The default port, not a magic number, it is one we've chosen*/
		int defaultPort = 6789;

		if (args.length > 2 || args.length == 0){
			System.out.println("Usage: \t<Protocol> [<Port Number>]");
			System.exit(1);
		}
		if (args.length == 1){
			args[0] = args[0].toUpperCase(); // Makes the protocol enter go to Upper case
			if(!args[0].equals("TCP") && !args[0].equals("UDP")){
				System.out.println("Please enter in either TCP or UDP for <Protocol>\nUsage: \t<Protocol> [<Port Number>]");
				System.exit(1);
			} 
		}
		if (args.length == 2){
			args[0] = args[0].toUpperCase(); // Makes the protocol enter go to Upper case
			if(!args[0].equals("TCP") && !args[0].equals("UDP")){
				System.out.println("Please enter in either TCP or UDP for <Protocol>\nUsage: \t<Protocol> [<Port Number>]");
				System.exit(1);
			} else {
				defaultPort = Integer.parseInt(args[1]);
			}
		}

		MagicServer ms = null;

		if (args[0].equals("TCP")){
			ms = new TcpMagicServer(defaultPort);

		}
		if (args[0].equals("UDP")){
			ms = new UdpMagicServer(defaultPort);		
		}

		try {
			ms.listen();
		} catch (MagicServerException e) {			
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println("Error, IOException, please try again");
		}

	}
}
