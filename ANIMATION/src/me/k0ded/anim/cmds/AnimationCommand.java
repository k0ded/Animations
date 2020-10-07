package me.k0ded.anim.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.k0ded.anim.Main;
import me.k0ded.anim.animation.Animation;
import me.k0ded.anim.structure.Builder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class AnimationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args) {
		
		if((!(sender instanceof Player)) && args.length >= 2) {
			
			System.out.println(args.length);
			return true;
		}
			
		if(!sender.hasPermission("pottercraft.builder")) {
			sender.sendMessage("§cYou have insufficient permissions to perform this command");
			return true;
		}
		
		switch(args.length) {
		
		case 1:
			if(args[0].equalsIgnoreCase("help")) {
				
				sender.sendMessage(getHelpMessage());
				break;
			}
			
			if(args[0].equalsIgnoreCase("list")) {
				
				sender.sendMessage(getAnimationListMessage());
				break;
			}
			sender.sendMessage(getHelpMessage());
			return true;
		case 2:
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("create")) {
				
				if(findAnimation(args[1]) == null) {
					Animation anim = new Animation(args[1]);
					Main.animationFile.save(anim);
					p.sendMessage("§aYou have successfully created a magical animation!");
					return true;
				}else {
					p.sendMessage("§cThat animation already exists!");
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("edit")) {
				
				p.spigot().sendMessage(getEditMessage(args[1]));
				return true;
			}
			
			if(args[0].equalsIgnoreCase("addframe") || args[0].equalsIgnoreCase("af")) {
				
				Animation animation = findAnimation(args[1]);

				if(animation == null) {
					p.sendMessage("§cThat animation doesn't exist!");
					return true;
				}
				
				if(Main.builders.containsKey(p)) {
					Builder b = Main.builders.get(p);
					if(b.getSelected() == null) {
						p.sendMessage("§cYou must make a selection before adding a frame!");
						return true;
					}
					
					animation.addFrame(b.getSelected());
					p.sendMessage("§aYour magical structure has been added!");
					
				}else{
					p.sendMessage("§cYou must make a selection before adding a frame!");
				}
				return true;
			}
			
			if(args[0].equalsIgnoreCase("run")) {
				
				for(Animation anim : Main.animations) {
					
					if(anim.getName().contentEquals(args[1])) {
						if(!anim.run()) {
							p.sendMessage("§cYour magical structure isnt tuned correctly!");
						}
						return true;
					}
					
				}
				p.sendMessage("§cThat animation doesn't exist!");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("setplayout")) {
				
				for(Animation anim : Main.animations) {
					if(anim.getName().contentEquals(args[1])) {
						anim.setAnimationLocation(p.getLocation().getBlock().getLocation());
						p.sendMessage("§aYour magical playpoint has been set!");
						return true;
					}
				}
				p.sendMessage("§cThat animation doesn't exist!");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase("settrigger")) {
				if(!Main.builders.containsKey(p)) {
					Main.builders.put(p, new Builder(p));
				}
				
				Animation animation = findAnimation(args[1]);
				Builder builder = Main.builders.get(p);
				
				if(animation == null) {
					p.sendMessage("§cThat animation doesn't exist!");
					return true;
				}
				
				builder.settingTrigger(animation);
				p.sendMessage("§aPerfect! Now punch the block you want to be as trigger for the animation!");				
				return true;
			}
			
		case 4:
			Player pl = (Player)sender;
			if(args[0].equalsIgnoreCase("edit")) {
				
				Animation animation = findAnimation(args[1]);
				
				if(animation == null) {
					pl.sendMessage("§cThat animation doesn't exist!");
					return true;
				}
				
				switch(args[2]) {
				
				case "speed":
					int speed = Integer.parseInt(args[3]) <= 1 ? 1 : Integer.parseInt(args[3]);
					animation.setSpeed(speed);  
					Main.animationFile.save(animation);
					pl.spigot().sendMessage(getEditMessage(args[1]));
					break;
				case "rwait":
					animation.setReverseWait(Integer.parseInt(args[3]));
					Main.animationFile.save(animation);
					pl.spigot().sendMessage(getEditMessage(args[1]));
					break;
				case "setreverse":
					boolean reverse = args[3].equalsIgnoreCase("true") ? true : false;
					
					animation.setReverse(reverse);
					Main.animationFile.save(animation);
					pl.spigot().sendMessage(getEditMessage(args[1]));
					break;
				
				}
			}
			break;
		default:
			sender.sendMessage(getHelpMessage());
			break;
		}
		return true;
	}
	
	private String getHelpMessage() {
		
		String message = 
				  "-=+=- &d&l&oPottercraft Animations&f -=+=-\n"
				+ "&l&nCommands\n&f"
				+ "&o/animation\n&f"
				+ "      - &olist\n&f"
				+ "      - &ocreate <animationName>\n&f"
				+ "      - &oedit <animationName>\n&f"
				+ "      - &orun <animationName>\n&f"
				+ "      - &osetTrigger <animationName>\n&f"
				+ "      - &osetPlayout\n&f"
				+ "-=+=-                                    -=+=-";
				
		String colorMessage = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
		
		return colorMessage;
	}
	
	private Animation findAnimation(String string) {
		for(Animation anim : Main.animations) {
			if(anim.getName().contentEquals(string)) {
				return anim;
			}
		}
		return null;
	}
	
	private BaseComponent getEditMessage(String string) {
		
		Animation animation = findAnimation(string);
		
		if(animation == null) {
			
			TextComponent component = new TextComponent("That animation doesn't exist!");
			component.setColor(ChatColor.RED);
			return component;
		}
		
		TextComponent headfoot = new TextComponent("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		TextComponent head = new TextComponent("-=+=- §d§l§oPottercraft Animations§f -=+=-\n");
		headfoot.addExtra(head);
		
		//SPEEDSTAT
		TextComponent SPEEDSTAT = new TextComponent("                (" + animation.getSpeed() + ") ");
		SPEEDSTAT.setColor(ChatColor.WHITE);
		SPEEDSTAT.setClickEvent(null);
		headfoot.addExtra(SPEEDSTAT);
		
		//SPEED-
		TextComponent SPEEDMINUS = new TextComponent("«");
		SPEEDMINUS.setColor(ChatColor.GREEN);
		SPEEDMINUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " speed " + (animation.getSpeed() - 1)));
		headfoot.addExtra(SPEEDMINUS);
		
		//SPEED
		TextComponent SPEED = new TextComponent(" SPEED ");
		SPEED.setColor(ChatColor.WHITE);
		SPEED.setClickEvent(null);
		headfoot.addExtra(SPEED);
		
		//SPEED+
		TextComponent SPEEDPLUS = new TextComponent("»\n\n");
		SPEEDPLUS.setColor(ChatColor.GREEN);
		SPEEDPLUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " speed " + (animation.getSpeed() + 1)));
		headfoot.addExtra(SPEEDPLUS);
		
		//RWAIT STAT
		TextComponent RWAITSTAT = new TextComponent("                (" + animation.getReverseWait() + ") ");
		RWAITSTAT.setColor(ChatColor.WHITE);
		RWAITSTAT.setClickEvent(null); 
		headfoot.addExtra(RWAITSTAT);
		
		//RWAIT-
		TextComponent RWAITMINUS = new TextComponent("«");
		RWAITMINUS.setColor(ChatColor.GREEN);
		RWAITMINUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " rwait " + (animation.getReverseWait() - 1)));
		headfoot.addExtra(RWAITMINUS);
		
		//RWAIT
		TextComponent RWAIT = new TextComponent(" RWAIT ");
		RWAIT.setColor(ChatColor.WHITE);
		RWAIT.setClickEvent(null);
		headfoot.addExtra(RWAIT);
		
		//RWAIT+
		TextComponent RWAITPLUS = new TextComponent("»\n\n");
		RWAITPLUS.setColor(ChatColor.GREEN);
		RWAITPLUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " rwait " + (animation.getReverseWait() + 1)));
		headfoot.addExtra(RWAITPLUS);
		
		//Reverse
		TextComponent reverse = new TextComponent("                     REVERSE\n");
		ChatColor rColor = animation.isReverse() ? ChatColor.GREEN : ChatColor.RED;
		reverse.setColor(rColor);
		reverse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " setreverse " + !animation.isReverse()));
		headfoot.addExtra(reverse);
		
		headfoot.addExtra(new TextComponent("-=+=-                                    -=+=-"));

		return headfoot;
	}

	private String[] getAnimationListMessage() {
		
		String[] message = new String[4];
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("-=+=- §d§l§oPottercraft Animations§f -=+=-");
		
		for(Animation animation : Main.animations) {
			
			ChatColor color = animation.canRun() ? ChatColor.GREEN : ChatColor.RED;
			
			sb.append("\n                   " + color + animation.getName() + ChatColor.RESET);
			sb.append(" : " + ChatColor.ITALIC);
			if(animation.getFrames() == null) {
				sb.append("0F");
			}else {
				sb.append(animation.getFrames().size() + "F");
			}
			if(animation.isReverse()) {
				sb.append(" R");
			}
		}
		sb.append("\n");
		
		message[0] = sb.toString();
		message[1] = "-=+=-                                    -=+=-";
		message[2] = "           <Name>  : Frames Reverse";
		message[3] = "-=+=-                                    -=+=-";
		
		
		
		return message;
	}

}
