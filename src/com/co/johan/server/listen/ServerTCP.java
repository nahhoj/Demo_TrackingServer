package com.co.johan.server.listen;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ServerTCP extends Thread {
			
	public static void main(String[] args) {
		int port = 5001;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
				System.out.println("Waiting for message TCP for port " + port);
				Socket socket = serverSocket.accept();
				TCPThread tcp=new TCPThread(socket);
				tcp.start();								
			}
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
		}
	}
}
