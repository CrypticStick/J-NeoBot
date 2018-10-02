package com.Neobots2903.Discord.NeoBot;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.security.auth.login.LoginException;
import javax.swing.JFrame;

import com.Neobots2903.Discord.NeoBot.GUI;
import com.Neobots2903.Discord.NeoBot.objects.TextAreaConsole;

import java.io.InputStream;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class NeoBot {

	public static JDA jda;

	static InputStream tokenIS = NeoBot.class.getClassLoader().getResourceAsStream("token"); 
	static boolean interactive = true;
	static String guildID = "323584029930553347";
	static String token;
	static String prefix;
	static String mention;
	static String botMessage;
	static boolean running = true;
	static int timeoutSeconds = 10;
	
	public static void consoleLog(String message) {
		String oldText = GUI.console.getText();
		GUI.console.setText(oldText + System.lineSeparator() + message);
	}

	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
		
    	GUI window = new GUI();
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	window.setSize(500, 300);
    	window.setVisible(true);
    	
		PrintStream printStream = new PrintStream(new TextAreaConsole(GUI.console));
		System.setOut(printStream);
		System.setErr(printStream);
		
		prefix = ">";
		botMessage = "NEOBOT IS HERE!!";
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(tokenIS));
			token = reader.readLine();
		} catch (Exception e) {
			System.err.println("The token file does not exist or is corrupt. This means code broke. Woops.");
			System.exit(1);
		}

		Console c = System.console();
		if (c == null) {
			System.err.println("No console.");
			System.err.println("This bot will continue to run, but no commands can be entered from this window.");
			interactive = false;
		}

		System.out.println("Starting up bot...");

		jda = new JDABuilder(AccountType.BOT) // Configure the bot before starting it
				.setToken(token).setStatus(OnlineStatus.ONLINE)
				.setGame(Game.of(GameType.DEFAULT, String.format("%s (Type %shelp)", botMessage, prefix)))
				.addEventListener(new EventListener())
				.addEventListener(new CommandHandler())
				.build();

		long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000);
		while (jda.getStatus() != Status.CONNECTED) { // Attempts to connect for 10 seconds - otherwise TIMEOUT
			if (System.currentTimeMillis() < endTime) {
				System.out.print("\r" + jda.getStatus().toString());
				Thread.sleep(500);
			}
			else {
				System.out.println(
						"Error: the bot failed to connect. This could either be because the token is incorrect, there's no internet, or because Stickles completely broke the code :P");
				throw new InterruptedException("Connection timeout.");
			}
		}
		
		mention = String.format("<@%s>", jda.getSelfUser().getId());

		//// NEOBOT LOCAL TERMINAL ////

		if (interactive) {
			while (running) {
				
				String command = c.readLine(String.format("%s>", jda.getSelfUser().getName()));

				switch (command) {

				case "shutdown":
				case "quit":
				case "stop":
				case "end":
				case "exit":
				case "close":
					jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB,
							Game.of(GameType.DEFAULT, "Shutting down..."));
					System.out.println("Shutting down...\n");
					System.exit(0);
					break;

				default:
					System.out.println(String.format("command '%s' not recognized.", command));

				}

			}
		}
	}
}
