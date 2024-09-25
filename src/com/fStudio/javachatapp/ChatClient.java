package com.fStudio.javachatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	// Attributes
	private Socket socket = null;
	private BufferedReader inputConsole = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	// Constructors
	public ChatClient(String address, int port) throws IOException {
		try {
			socket = new Socket(address, port);
			System.out.println("Connected to the chat server!");

			inputConsole = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String line = "";
			while (!line.equals("exit")) {
				line = inputConsole.readLine();
				out.println(line);
				System.out.println(in.readLine());
			}

			socket.close();
			inputConsole.close();
			out.close();
		} catch (UnknownHostException u) {
			System.out.println("Host unknown: " + u.getMessage());
		} catch (IOException i) {
			System.out.println("Unexpected exception: " + i.getMessage());
		}
	}

	// Methods
	public static void main(String args[]) throws IOException {
		ChatClient client = new ChatClient("127.0.0.1", 5000);
	}

}
