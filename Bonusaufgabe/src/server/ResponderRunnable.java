package server;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import data.WeatherData;

/**
 * Runnable that responds to incoming requests
 * 
 * @author Hagen
 *
 */
public class ResponderRunnable implements Runnable {
	DatagramSocket socket = null;
	DatagramPacket packet = null;

	public ResponderRunnable(DatagramSocket serverSocket, DatagramPacket packet) {
		this.socket = serverSocket;
		this.packet = packet;
	}

	@Override
	public void run() {
		String incomingData;
		try {
			incomingData = new String(packet.getData(), "UTF-8");

			System.out.println("Received: " + incomingData);

			byte[] data = createResponsePacket(incomingData);
			DatagramPacket response = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
			try {
				socket.send(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			System.out.println("Could not read encoding. Please use UTF-8");
		}
	}

	/**
	 * Method that creates a response packet
	 * 
	 * @param packet - incoming packet
	 * @return byte[] of WeatherData if successful or a message if unsuccessful
	 */
	private byte[] createResponsePacket(String date) {
		try {
			WeatherData wdata = UDPServer.database.getData(LocalDate.parse(date));
			if (wdata == null) {
				return "Could not find an entry with complete data for the specified date.".getBytes();
			} else {
				return serializeWeatherData(wdata);
			}
		} catch (DateTimeParseException e) {
			return new String("Could not parse date. Please use ISO_LOCAL_DATE format, like 2016-08-20").getBytes();
		}
	}

	private byte[] serializeWeatherData(WeatherData data) {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo;
		try {
			oo = new ObjectOutputStream(bStream);
			oo.writeObject(data);
			oo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] serializedMessage = bStream.toByteArray();
		return serializedMessage;
	}
}
