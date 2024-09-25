package com.fStudio.javachatapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatClientGUI extends JFrame {

	// Attributes
	private JTextArea messageArea;
	private JTextField textField;
	private ChatClient client;

	// Constructors
	public ChatClientGUI() {
		super("Chat Application");
		setSize(400, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		messageArea = new JTextArea();
		messageArea.setEditable(false);
		add(new JScrollPane(messageArea), BorderLayout.CENTER);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendMessage(textField.getText());
				textField.setText("");
			}
		});
		add(textField, BorderLayout.SOUTH);

		// Initialize and start the ChatClient
		try {
			this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
			client.startClient();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	// Methods
	private void onMessageReceived(String message) {
		SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new ChatClientGUI().setVisible(true);
		});
	}

}
