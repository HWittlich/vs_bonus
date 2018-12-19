package client;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class that starts a UDP Client. 
 * @author Hagen
 *
 */
public class ClientStart {

	/**
	 * Method to start a UDPClient
	 * @param args - can be empty, include a portnumber, or include a portnumber and an IP address.
	 */
	public static void main(String[] args) {
		int port = 9000; // default port
		InetAddress IPAddress = null;

		// parse arguments to see if the port has been specified
		if (args.length>0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println(args[0] + " is not a valid portnumber.");
				System.exit(-1);
			}
		}
		
		// parse arguments to see if IP address was specified
		try {
			IPAddress = (args.length>1) ? InetAddress.getByAddress(args[1].getBytes()) : InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// start UDPClient
		new Thread(new UDPClient(port, IPAddress)).start();
	}


}
