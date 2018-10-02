package com.Neobots2903.Discord.NeoBot;


import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//import java.awt.event.*; 

// An AWT program inherits from the top-level container java.awt.Frame
public class GUI extends JFrame {

	private static final long serialVersionUID = 4792335136316776430L;
	private JLabel comingSoon;	// Declare a Label component 
	public static JTextArea console;	//Console window

	public GUI () {
		super("NeoBot");
		setLayout(new BorderLayout());
		
		comingSoon = new JLabel("GUI coming soon");
		comingSoon.setToolTipText("It'll be pretty legit");
		
		console = new JTextArea();
		//Dimension consoleDim = new Dimension();
		console.setBorder(BorderFactory.createLoweredBevelBorder());
		console.setEditable(false);
		
		add(comingSoon, BorderLayout.NORTH);
		add(new JScrollPane(console), BorderLayout.SOUTH);
	}
}