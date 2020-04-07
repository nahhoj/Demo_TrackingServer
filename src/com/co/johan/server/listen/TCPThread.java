package com.co.johan.server.listen;

import java.io.InputStream;
import java.net.Socket;
import java.util.Date;

import org.apache.log4j.Logger;

public class TCPThread extends Thread {

	static Logger log = Logger.getLogger(TCPThread.class.getName());
	private Socket socket;

	public TCPThread(Socket socket) {
		this.socket = socket;
	}

	public void run(){
		String ip=socket.getInetAddress().toString();
		System.out.println("Start thread TCP with client " + ip);
		log.debug(new Date() + " - Start thread TCP with client " + ip);
		try {
			InputStream in = socket.getInputStream();
			//OutputStream out = socket.getOutputStream();
			byte[] byteRead = new byte[512];
			in.read(byteRead);
			String message=new String(byteRead);						
			socket.close();
			Tracking.insert(message,ip,null);
			log.debug(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("End thread TCP with client " + ip);
		log.debug(new Date() + " - End thread TCP with client " + ip);
	}
}
