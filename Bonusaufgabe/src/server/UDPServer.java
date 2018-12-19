package server;
import java.io.*;
import java.net.*;

import data.DataSource;

/**
 * UDPServer class that listens on a specified port and keeps a DataSource.
 * It starts worker threads to handle incoming requests.
 * @author Hagen
 *
 */
class UDPServer implements Runnable {

	DatagramSocket serverSocket;
	public static DataSource database;
	byte[] incomingData = new byte[10];

	public UDPServer(int port, DataSource datasource) {
		database = datasource;
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Successfully started Server on Port "+serverSocket.getLocalPort());
		while (true) {
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			try {
				serverSocket.receive(incomingPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(new ResponderRunnable(serverSocket, incomingPacket)).start();
		}
	}
}