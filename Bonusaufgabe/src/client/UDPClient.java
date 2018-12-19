package client;
import java.io.*;
import java.net.*;

import data.WeatherData;

class UDPClient implements Runnable {
	InetAddress IPAddress;
	int port;

	public UDPClient(int port, InetAddress address) {
		IPAddress = address;
		this.port = port;
	}

	@Override
	public void run() {
		byte[] outboundData = new byte[1024];
		byte[] incomingData = new byte[1024];
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			// Read user input
			System.out.println("Please enter a date in the following format: 2019-01-01");
			String userInput = inFromUser.readLine();
			outboundData = userInput.getBytes();
			
			// Send packet
			DatagramPacket sendPacket = new DatagramPacket(outboundData, outboundData.length, IPAddress, port);
			DatagramSocket clientSocket = new DatagramSocket();
			clientSocket.send(sendPacket);
			
			// Handle incoming Packet
			DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
			clientSocket.receive(incomingPacket);
			System.out.println();
			try {
				WeatherData data = deserializeWeatherData(incomingPacket.getData());
				System.out.println("Received data from Server. Output below.");
				System.out.println();
				WeatherDataFormatter.printFormattedOutput(data);
			} catch (IOException | ClassNotFoundException e) {
				String errorMessage = new String(incomingPacket.getData());
				System.out.println("Error from Server: " + errorMessage);
			}
			clientSocket.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * method for deserializing a WeatherData object from a byte[]
	 * @param input - serialized WeatherData object
	 * @return WeatherData object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static WeatherData deserializeWeatherData(byte[] input) throws IOException, ClassNotFoundException {
		ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(input));
		WeatherData data = (WeatherData) iStream.readObject();
		iStream.close();
		return data;
	}
}
