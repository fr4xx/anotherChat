package com.fStudio.javachatapp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ChatClientGUI extends JFrame {

	// Attributes
	private JTextArea messageArea;
	private JTextField textField;
	private JButton exitButton;

	private ChatClient client;

	// Constructors
	public ChatClientGUI() {
		super("Chat Application");
		setSize(400, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		messageArea = new JTextArea();
		messageArea.setEditable(false);
		add(new JScrollPane(messageArea), BorderLayout.CENTER);

		// Prompt for user name
		String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry",
				JOptionPane.PLAIN_MESSAGE);
		this.setTitle("Chat Application - " + name); // Set window title to include user name

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.sendMessage("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name
						+ ": " + textField.getText());
				textField.setText("");
			}
		});

		exitButton = new JButton("Exit");
		exitButton.addActionListener(e -> System.exit(0)); // Exit the application
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(textField, BorderLayout.CENTER);
		bottomPanel.add(exitButton, BorderLayout.EAST);
		add(bottomPanel, BorderLayout.SOUTH);

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
