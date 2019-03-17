package com.Neobots2903.Discord.NeoBot;


import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.Neobots2903.Discord.NeoBot.interfaces.Command;
import com.Neobots2903.Discord.NeoBot.objects.DiscordUser;
import com.Neobots2903.Discord.NeoBot.objects.JSONObject;
import com.Neobots2903.Discord.NeoBot.objects.PendingMessage;
import com.github.axet.vget.VGet;
import com.github.axet.vget.info.VGetParser;
import com.github.axet.vget.vhs.YouTubeMPGParser;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import net.dv8tion.jda.client.exceptions.VerificationLevelException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.InsufficientPermissionException;

public class Commands {

	public static ArrayList<String> reactionMessages = new ArrayList<String>();

	public static void sendMessage(User user, TextChannel mChannel, String msg, boolean isPrivate) {
		try {
			if (isPrivate) {
				user.openPrivateChannel().queue((channel) -> channel
						.sendMessage(msg).queue());
			} else {
				mChannel.sendMessage(msg).queue();
			}
		} catch (InsufficientPermissionException | VerificationLevelException e) {
			user.openPrivateChannel().queue((channel) -> channel
					.sendMessage(String.format(
							"%s currently does not have permission to speak in %s, %s.\n"
									+ "If you feel this is a mistake, please contact the server administrator.",
									NeoBot.jda.getSelfUser().getName(), mChannel.getGuild().getName(), mChannel.getName()))
					.queue());
		} catch (IllegalArgumentException e) {
			if (msg.isEmpty())
				sendMessage(user, mChannel, String.format("%s, I can't send an empty message!", user.getAsMention()),false);
			else
				sendMessage(user, mChannel, String.format("%s, the message is too long!", user.getAsMention()),false);
		}
	}
	
	public static void sendMessage(User user, TextChannel mChannel, MessageEmbed eb, boolean isPrivate) {
		try {
			if (isPrivate) {
				user.openPrivateChannel().queue((channel) -> channel
						.sendMessage(eb).queue());
			} else {
				mChannel.sendMessage(eb).queue();
			}
		} catch (InsufficientPermissionException | VerificationLevelException e) {
			user.openPrivateChannel().queue((channel) -> channel
					.sendMessage(String.format(
							"%s currently does not have permission to speak in %s, %s.\n"
									+ "If you feel this is a mistake, please contact the server administrator.",
									NeoBot.jda.getSelfUser().getName(), mChannel.getGuild().getName(), mChannel.getName()))
					.queue());
		} catch (IllegalArgumentException e) {
			if (eb.isEmpty())
				sendMessage(user, mChannel, String.format("%s, I can't send an empty message!", user.getAsMention()),false);
			else
				sendMessage(user, mChannel, String.format("%s, the message is too long!", user.getAsMention()),false);
		}
	}
	
	public static void sendMessage(MessageReceivedEvent e, String msg, boolean isPrivate) {
		sendMessage(e.getAuthor(),e.getTextChannel(),msg,isPrivate);
	}
	
	public static void sendMessage(MessageReceivedEvent e, MessageEmbed eb, boolean isPrivate) {
		sendMessage(e.getAuthor(),e.getTextChannel(),eb,isPrivate);
	}
	
	public static void sendMessage(Message m, String msg, boolean isPrivate) {
		sendMessage(m.getAuthor(),m.getTextChannel(),msg,isPrivate);
	}

	@Command(Name = "help",
			Summary = "Lists information about available commands",
			Syntax = "help [optional:command]")
	public static void IcanHELPyoU(MessageReceivedEvent e, ArrayList<String> args) {

		String helpMsg = "";

		Method[] commands = Commands.class.getMethods();
		for (Method m : commands) {
			if (m.isAnnotationPresent(Command.class)) {

				String Name = "";
				String formattedAliases = "";
				String Summary = "";
				String SpecialPerms = "";
				String Syntax = "";
				boolean matchingAlias = false;

				Name = m.getAnnotation(Command.class).Name();
				String[] Aliases = m.getAnnotation(Command.class).Aliases();
				for (String a : Aliases) {
					if (args.contains(a)) matchingAlias = true;
				}
				if (args.isEmpty() || args.contains(Name) || matchingAlias) {
					if (m.getAnnotation(Command.class).Aliases().length > 0)
						formattedAliases = " (also " + String.join(", ", Aliases) + ")";
					Summary = m.getAnnotation(Command.class).Summary();
					if (!m.getAnnotation(Command.class).SpecialPerms().isEmpty())
						SpecialPerms = " **<" + m.getAnnotation(Command.class).SpecialPerms() + ">**";
					if (!m.getAnnotation(Command.class).Syntax().isEmpty())
						Syntax = " ~ `" + m.getAnnotation(Command.class).Syntax() + "`";
					helpMsg += String.format("`%s%s%s` - %s%s%s\n",NeoBot.prefix,Name,formattedAliases,Summary,SpecialPerms,Syntax);
				}
			}
		}
		if (helpMsg == "") sendMessage(e,String.format("Sorry %s, the requested command(s) do not exist.", e.getAuthor().getAsMention()),false);
		else sendMessage(e, helpMsg, false);
	}

	@Command(Name = "echo",
			Aliases = {"repeat","copy"},
			Summary = "I, NeoBot, will copy what you say",
			Syntax = "echo [command]")
	public static void wowAnECHOCommand(MessageReceivedEvent e, ArrayList<String> args) {

		sendMessage(e,String.join(" ", args),false);
		if (e.getChannelType() == ChannelType.TEXT) {
			e.getMessage().delete().queue();
		}
	}

	@Command(Name = "test", 
			Summary = "My personal testing command while implementing features. TESTING TESTING 123",
			SpecialPerms = "Only Stickles can run this :P")
	public static void thisIsATEST123(MessageReceivedEvent e, ArrayList<String> args) {

		if (!e.getAuthor().getId().equals("215507031375740928")) return;
        
		JSONObject info = new JSONObject(HttpBlueAllianceGet(
				String.format("http://www.thebluealliance.com/api/v3/team/frc%s/media/%s",args.get(0),LocalDate.now().getYear())));
		StringBuilder list = new StringBuilder();
		list.append(String.format("**Team %s's Media**%s", args.get(0),System.lineSeparator()));
			list.append(String.format("Key - %s%s",
					info.get("0","details","base64Image")[0],
					System.lineSeparator()));
			sendMessage(e,list.toString(),false);
		
		sendMessage(e,"Test code complete!",false);
		e.getMessage().delete().queue();
	}
	
	@Command(Name = "panic",
			Summary = "An urgent message that will audibly relay to the NeoBots. Only use when necessary!")
	public static void WARNINGWARNINGaaHHHHHHH(MessageReceivedEvent e, ArrayList<String> args) {

		//read audio data from whatever source (file/classloader/etc.)
		InputStream audioSrc = NeoBot.class.getClassLoader().getResourceAsStream("Warning.wav");
		InputStream bufferedAlarm = new BufferedInputStream(audioSrc);
		
		Clip alarm = null;
		try {
			alarm = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedAlarm);
		        alarm.open(inputStream);
		} catch (Exception ex) {
			System.out.println("Warning! Alarm failed to load.");
		}
		
	        alarm.start();
	        
	        while(alarm.getMicrosecondLength() != alarm.getMicrosecondPosition())
	        {
	        }
			
	        alarm.stop();
	        alarm.close();
	        
	        try {    
			  Voice voice;
			  System.setProperty("freetts.voices", 
		                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  
			  VoiceManager voiceManager = VoiceManager.getInstance();
			  voice = voiceManager.getVoices()[1];
			  voice.allocate();
			  voice.speak(String.join(" ", args));
			  voice.deallocate();
			  
	      } catch (Exception ex) {
	        System.err.println(ex.getMessage());
	      }
	}
	
	public static String YouTubeToMP3(String url) 
	{
		String filename = "arg.mp3";
		try { 
            VGet v = new VGet(new URL(url), new File(""));
            //VideoInfo vi = v.getVideo();
            //filename = vi.getTitle().replaceAll(" ","_") + ".mp3";
    		//v.setTarget(new File("music/" + filename));
            VGetParser user = new YouTubeMPGParser();
            v.download(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
		return "music/" + filename;
	}
	
	@Command(Name = "music",
			Summary = "Post a music file and the bot will play epic music",
			Syntax = "music [audio file]")
	public static void WowTHISSOUNDlikeMuSIC(MessageReceivedEvent e, ArrayList<String> args) {
		int suffix = 0;
		String fileName = "";
		if (args.isEmpty())
		for (Attachment a : e.getMessage().getAttachments()) {
			if (a.getFileName().endsWith(".mp3") || a.getFileName().endsWith(".wav")) {
				fileName = a.getFileName();
				File file = new File("music/" + fileName);
				if (file.exists() && file.length() == a.getSize())
					NeoBot.PlaySound("music/" + fileName);
				else if (new File("music/" + fileName).exists())
					fileName = a.getFileName().replace(".", " (" + ++suffix + ").");
				if (a.download(new File("music/" + fileName)))
					NeoBot.PlaySound("music/" + fileName);
				else
					sendMessage(e,"`" + a.getFileName() + "` failed to download :(",false);
				return;
			}
		} else {
			NeoBot.PlaySound(YouTubeToMP3(args.get(0)));
		}
	}
	
	static String HttpBlueAllianceGet(String url) {       
	    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
	        
            HttpGet request = new HttpGet(url);
            request.setHeader("Content-type", "application/json");
	        request.addHeader("X-TBA-Auth-Key", "0NiCsg5pJzCGOVmZTbYk0LdTZOXcDMIQJKThzoqIVBuEWSZ5dXbtTouAspaayL5B");
            HttpResponse response = client.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }

            return builder.toString();
        } catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return "";
	}

	@Command(Name = "team", 
			Summary = "Returns the NeoBot's stats")
	public static void javascriptMORElikeJAVAisBETTERwahhaahaha(MessageReceivedEvent e, ArrayList<String> args) {
		int teamNumber = 2903;
		int switchArg = -1;
		for (int i = 0; i < args.size(); i++)
		try {
			teamNumber = Integer.parseInt(args.get(i));
			switchArg = i;
		} catch (Exception ex) {
		}
		
		if (switchArg == 0)
			switchArg = 1;
		else 
			switchArg = 0;
		
		JSONObject info;
		StringBuilder list;
		String value;
		try {
			value = args.get(switchArg);
		} catch (Exception ex) {
			value = "info";
		}
		
		switch (value) {
			case "awards" :
				info = new JSONObject(HttpBlueAllianceGet(
						String.format("http://www.thebluealliance.com/api/v3/team/frc%s/awards",teamNumber)));
				int total = info.get().length;
				list = new StringBuilder();
				list.append(String.format("**Team %s's Awards**%s", teamNumber,System.lineSeparator()));
				for (int i=0; i < total; i++)
					list.append(String.format("%s - %s %s",info.get(String.valueOf(i),"year")[0],info.get(String.valueOf(i),"name")[0],System.lineSeparator()));
					sendMessage(e,list.toString(),false);
				break;
			case "info" :
				info = new JSONObject(HttpBlueAllianceGet(
						String.format("http://www.thebluealliance.com/api/v3/team/frc%s",teamNumber)));
				if (info.get("team_number")[0].isEmpty()) {
					sendMessage(e,String.format("Sorry, Team '%s' doesn't seem to exist.",teamNumber),false);
					return;
				}
				
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(Color.red);
				eb.setDescription(String.format("**Team %s's Info**", teamNumber));
				
				eb.addField("Nickname", info.get("nickname")[0], true);
				eb.addField("Name", (info.get("name")[0].equals("null")) ? "N/A" : info.get("name")[0], true);
				eb.addField("City", (info.get("city")[0].equals("null")) ? "N/A" : info.get("city")[0], true);
				eb.addField("Country", (info.get("country")[0].equals("null")) ? "N/A" : info.get("country")[0], true);
				eb.addField("State/Providence", (info.get("state_prov")[0].equals("null")) ? "N/A" : info.get("state_prov")[0], true);
				eb.addField("Address", (info.get("address")[0].equals("null")) ? "N/A" : info.get("address")[0], true);
				eb.addField("Google Maps", (info.get("gmaps_url")[0].equals("null")) ? "N/A" : info.get("gmaps_url")[0], false);
				eb.addField("Website", (info.get("website")[0].equals("null")) ? "N/A" : info.get("website")[0], false);
				eb.addField("Rookie Year", (info.get("rookie_year")[0].equals("null")) ? "N/A" : info.get("rookie_year")[0], false);
				eb.addField("Motto", (info.get("motto")[0].equals("null")) ? "N/A" : info.get("motto")[0], false);

				sendMessage(e,eb.build(),false);
				break;
			default :
				sendMessage(e,String.format("Sorry, I have no information on '%s'",args.get(switchArg)),false);
				break;
		}
	}
	
	@Command(Name = "random", 
			Summary = "Provides a random number between 0 and 100")
	public static void superRanDOmLOL(MessageReceivedEvent e, ArrayList<String> args) {

		Random rnd = new Random();
		Integer randomInt = rnd.nextInt(100);
		sendMessage(e,randomInt.toString(),false);
	}
	
	@Command(Name = "everyone", 
			Summary = "Sends message that pings everyone (Requires admin approval)")
	public static void XDwowGOVERNMENTcensorSHIPWHAHAHA(MessageReceivedEvent e, ArrayList<String> args) {

		DiscordUser user = NeoBot.GetDiscordUser(e.getAuthor().getId());
		user.getPendingMessages().getMessageList().add(
				new PendingMessage(String.join(" ", args),e.getChannel().getId(),user));
		NeoBot.SaveDiscordUser(user);
		sendMessage(e,e.getAuthor().getAsMention() + " Your ping request is pending. Please wait for a moderator to approve.", false);
	}
	
	@Command(Name = "pending", 
			Summary = "Checks pending user requests",
			SpecialPerms = "Moderators only")
	public static void whoAthisisEPIC(MessageReceivedEvent e, ArrayList<String> args) {
		
		int index = 0;
		boolean isArgNumber = false;
		int argNumber = 1;
		
		for(String arg : args) {
			try 
			{ 
				argNumber = Integer.parseInt(arg);
				isArgNumber = true;
				break;
			} 
			catch (Exception ex) 
			{
				continue;
			}
		}
		
		if (args.contains("list")) {
			if(isArgNumber) {
				if (argNumber < 1) {
					sendMessage(e, e.getAuthor().getAsMention() + " Please provide a positive page number value.", false);
					return;
				}
			}
			String finalMessage = "```Pending messages:" + "\n";
			
			for (DiscordUser user : NeoBot.database.getUserList().getUserList()) {
				for (PendingMessage message : user.getPendingMessages().getMessageList()) {
					
					++index;
					if ((argNumber-1)*10 > index || index > argNumber*10) continue;
					
					String preview = message.getMessage().replaceAll("\n", "---");
					boolean expandable = false;
					if (preview.length() > 40) {
						preview = preview.substring(0,40);
						expandable = true;
					}
					
					finalMessage += message.getId() + 
							". [" + NeoBot.jda.getTextChannelById(message.getChannelId()).getName() + "] " + 
							". <" + NeoBot.jda.getUserById(message.getAuthorId()).getName() + "> " + 
							preview + ((expandable) ? "[...]" : "") + "\n";		
				}
			}
			
			if (argNumber > (int)Math.ceil(index/10) && argNumber != 1) {
				sendMessage(e, "There is no page " + argNumber, false);
				return;
			}
			
			if (index < 10) index = 10;
			finalMessage += "Page " + argNumber + "/" + ((int)Math.ceil(index/10)) + "```";
			
			sendMessage(e, finalMessage, false);
		} else if (args.contains("approve")) {
			if (!isArgNumber) { 
				sendMessage(e,"Please enter message # you wish to approve.",false);
				return;
			}
			for (DiscordUser user : NeoBot.database.getUserList().getUserList()) {
				for (PendingMessage message : user.getPendingMessages().getMessageList()) {
					if (message.getId() == argNumber) {
						sendMessage(e,"Message approved!",false);
						sendMessage(
								NeoBot.jda.getUserById(message.getAuthorId()),
								NeoBot.jda.getTextChannelById(message.getChannelId()),
								message.getMessage(),
								false
								);
						user.getPendingMessages().getMessageList().remove(message);
						NeoBot.SaveDiscordUser(user);
					}
				}
			}
		}
	}
}
