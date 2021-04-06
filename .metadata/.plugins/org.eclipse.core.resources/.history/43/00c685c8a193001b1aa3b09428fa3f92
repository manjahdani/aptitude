package be.uclouvain.aptitude.agents.ui;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

class UDPSender
{
	private DatagramSocket client;
	
	public UDPSender()
	{
		try 
		{
			client = new DatagramSocket();
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
	}
	
	public <T> void SendData(T data, String ip, int port)
	{
		Gson jsonparser = new Gson();
		Type dataType = new TypeToken<T>() {}.getType();
		String msg = jsonparser.toJson(data, dataType);
		
		byte[] buffer = msg.getBytes();
		
		try
		{
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
			packet.setData(buffer);
			
			client.send(packet);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void CloseUDPSender()
	{
		if(client != null)
		{
			client.close();
		}
	}
}
