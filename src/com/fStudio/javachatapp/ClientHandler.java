package com.fStudio.javachatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

class ClientHandler implements Runnable {
	private Socket clientSocket;
	private List<ClientHandler> clients;
	private PrintWriter out;
	private BufferedReader in;

	public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
		this.clientSocket = socket;
		this.clients = clients;
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void run() {
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// Broadcast message to all clients
				for (ClientHandler aClient : clients) {
					aClient.out.println(inputLine);
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred: " + e.getMessage());
		} finally {
			try {
				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}