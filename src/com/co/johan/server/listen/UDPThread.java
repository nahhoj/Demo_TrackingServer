package com.co.johan.server.listen;

import java.net.DatagramPacket;
import java.util.Date;

import org.apache.log4j.Logger;

public class UDPThread extends Thread{
	
	static Logger log = Logger.getLogger(UDPThread.class.getName());	
	private DatagramPacket packet;
	
	public UDPThread(DatagramPacket packet) {
		this.packet=packet;
	}	
	
	public void run() {		
		String ip=packet.getAddress().toString();
		String port=packet.getPort() +"";
		System.out.println("Start thread TCP with client " + ip);
		log.debug(new Date() + " - Start thread TCP with client " + ip);
		try {			
			String message = new String(packet.getData());
			Tracking.insert(message,ip,port);
			log.debug(message);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("End thread TCP with client " + ip);
		log.debug(new Date() + " - End thread TCP with client " + ip);
	}
}
