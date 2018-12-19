package server;

import data.CSVSourcer;

/**
 * Class that starts a UDPServer with a CSVSourcer as Data Source
 * @author Hagen Wittlich
 *
 */
public class ServerStart {

	public static void main(String[] args) {
		
		int port = 9000; //default port
		
		if (args.length>0 ) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.out.println(args[0] +" is not a valid portnumber.");
				System.exit(-1);
			}
		}
		
		// pass parameters to server and start new Thread
		UDPServer server = new UDPServer(port, new CSVSourcer());
		new Thread(server).start();
	}

}
