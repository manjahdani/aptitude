package be.uclouvain.launcher;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class UDPSender {
	private DatagramSocket client;

	public UDPSender() {
		try {
			client = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public <T> void SendData(T data, String ip, int port) {
		Gson jsonparser = new Gson();
		Type dataType = new TypeToken<T>() {
		}.getType();
		String msg = jsonparser.toJson(data, dataType);

		byte[] buffer = msg.getBytes();

		try {
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
			packet.setData(buffer);

			client.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CloseUDPSender() {
		if (client != null) {
			client.close();
		}
	}
}