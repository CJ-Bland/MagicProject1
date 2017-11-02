package client;

import java.io.PrintStream;
import java.net.InetAddress;

public abstract class AbstractMagicClient implements MagicClient {

	InetAddress host;
	int port;
	String flag;
	
	protected InetAddress getHost(){
		return host;
	}
	
	protected int getPort(){
		return port;
	}
	
	protected String getFlag(){
		return flag;
	}
	
	public void printToStream(PrintStream out){
		
	}
}
