package com.Neobots2903.Discord.NeoBot;

import java.awt.Color;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.MessageUpdateEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.RestAction;

public class EventListener extends ListenerAdapter {

	public void logMessage(Message message, boolean edited) {
		if (message.getChannel().getName().equals("message-log") || message.getChannel().getName().equals("join-log"))
			return; // Don't let it log a message from the log channel

		if (message.getGuild().getTextChannelsByName("message-log", false).isEmpty()) {
			try {
				Guild server = NeoBot.jda.getGuildById(NeoBot.guildID);
				GuildController control = server.getController();
				control.createTextChannel("message-log").queue();

			} catch (InsufficientPermissionException ex) {
				Commands.sendMessage(message, "Warning: Give NeoBot `" + ex.getPermission().getName() + "` permission(s)!", false);
				return;
			}
		}
		// char empty = '\u2063';
		try {
			TextChannel logChannel = NeoBot.jda.getGuildById(NeoBot.guildID).getTextChannelsByName("message-log", true)
					.get(0);

			EmbedBuilder eb = new EmbedBuilder();
			DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm:ss a zz");
			String userName = message.getGuild().getMemberById(message.getAuthor().getId()).getNickname() + " ("
					+ message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator() + ")";
			eb.setAuthor(userName, null, message.getAuthor().getEffectiveAvatarUrl());

			if (edited) {
				eb.setColor(Color.orange);
				eb.setDescription(String.format("Edited %s", message.getEditedTime()
						.atZoneSameInstant(ZoneId.of("America/Los_Angeles")).format(outputFormat)));
			} else {
				eb.setColor(Color.red);
				eb.setDescription(String.format("Sent %s", message.getCreationTime()
						.atZoneSameInstant(ZoneId.of("America/Los_Angeles")).format(outputFormat)));
			}

			eb.addField("Message", message.getContentStripped(), false);

			for (Attachment a : message.getAttachments()) {
				if (a.isImage()) {
					eb.setImage(a.getUrl());
				} else {
					eb.addField("Other Attatchment", a.getUrl(), false);
				}
			}

			eb.addField("Channel", "<#" + message.getChannel().getId() + ">", true);
			eb.addField("User ID", message.getAuthor().getId(), true);

			logChannel.sendMessage(eb.build()).queue();

		}catch (InsufficientPermissionException ex) {
			Commands.sendMessage(message, "Warning: Give NeoBot `" + ex.getPermission().getName() + "` permission(s)!", false);
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	@Override
	public void onReady(ReadyEvent e) {
		System.out.println(String.format(
				"%s invite link: https://discordapp.com/oauth2/authorize?client_id=495375587339403264&scope=bot&permissions=8 \n",
				e.getJDA().getSelfUser().getName(), e.getJDA().getSelfUser()));
		System.out.println(String.format("[%s#%s] I'm online!", e.getJDA().getSelfUser().getName(),
				e.getJDA().getSelfUser().getDiscriminator()));
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent e) {
		RestAction<PrivateChannel> c = e.getUser().openPrivateChannel();
		c.queue((channel) -> channel.sendMessage(String.format("Welcome %s! Make sure to read the rules and have fun!",
				NeoBot.jda.getSelfUser().getName())).queue());
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent e) {

		if (e.getAuthor().isBot())
			return;

		if (e.getChannel().getName().equals("message-log") || e.getChannel().getName().equals("join-log")) {
			try {
				e.getMessage().delete();
			} catch (Exception ex) {
			}
		} else {
			logMessage(e.getMessage(), false);
		}
	}

	@Override
	public void onMessageUpdate(MessageUpdateEvent e) {
		if (e.getAuthor().isBot())
			return;

		if (e.getChannel().getName().equals("message-log") || e.getChannel().getName().equals("join-log")) {
			try {
				e.getMessage().delete();
			} catch (Exception ex) {
			}
		} else {
			logMessage(e.getMessage(), true);
		}
	}

	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		if (e.getUser().getId().equals(NeoBot.jda.getSelfUser().getId()))
			return;
		// more code here :)
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent e) {
		if (e.getUser().getId().equals(NeoBot.jda.getSelfUser().getId()))
			return;
		// more code here :)
	}

}
