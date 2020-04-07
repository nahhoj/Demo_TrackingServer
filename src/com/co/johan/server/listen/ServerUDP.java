package com.co.johan.server.listen;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP{
	
	public static void main(String[] args) {
		int port = 5000;
		try {
			DatagramSocket socket = new DatagramSocket(port);
			while (true) {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				System.out.println("Waiting for message UDP for port " + port);
				socket.receive(packet);		
				UDPThread udp=new UDPThread(packet);
				udp.start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
    }
}
