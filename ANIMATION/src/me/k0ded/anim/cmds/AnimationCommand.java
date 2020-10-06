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
			
		case 2:
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("create")) {
				
				Animation anim = new Animation(args[1]);
				Main.animationFile.save(anim);
				p.sendMessage("§aYou have successfully created a magical animation!");
				return true;
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
						if(anim.run()) {
							p.sendMessage("§aYour magical structure works as intended!");
							return true;
						}else {
							p.sendMessage("§cYour magical structure isnt tuned correctly!");
							return true;
						}
						
					}
					
				}
				p.sendMessage("§cThat animation doesn't exist!");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("setplaylocation")) {
				
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
				p.sendMessage("§aPerfect! Now use your golden wand to set the trigger!");				
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
					if(Integer.parseInt(args[3]) <= 1) {
						animation.setSpeed(1);
						return true;
					}
					animation.setSpeed(Integer.parseInt(args[3]));
					Main.animationFile.save(animation);
					pl.spigot().sendMessage(getEditMessage(args[1]));
					break;
				case "rwait":
					animation.setReverseWait(Integer.parseInt(args[3]));
					Main.animationFile.save(animation);
					pl.spigot().sendMessage(getEditMessage(args[1]));
					break;
				case "setshowtrigger":
					if(args[3].equalsIgnoreCase("true")) {
						animation.setShowTrigger(true);
						Main.animationFile.save(animation);
						pl.spigot().sendMessage(getEditMessage(args[1]));
					}else {
						animation.setShowTrigger(false);
						Main.animationFile.save(animation);
						pl.spigot().sendMessage(getEditMessage(args[1]));
					}
					break;
				case "setreverse":
					if(args[3].equalsIgnoreCase("true")) {
						animation.setReverse(true);
						Main.animationFile.save(animation);
						System.out.println("flipped setreverse");
						pl.spigot().sendMessage(getEditMessage(args[1]));
					}else {
						animation.setReverse(false);
						Main.animationFile.save(animation);
						System.out.println("flipped setreverse");
						pl.spigot().sendMessage(getEditMessage(args[1]));
					}
					break;
				}
				
				
			}
			
		default:
			sender.sendMessage(getHelpMessage());
		}
		
		
		
		
		
		return true;
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
		
		TextComponent headfoot = new TextComponent("");
		TextComponent head = new TextComponent("-===============-");
		headfoot.addExtra(head);
		
		//SPEED-
		TextComponent SPEEDMINUS = new TextComponent("\n«");
		SPEEDMINUS.setColor(ChatColor.GREEN);
		SPEEDMINUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " speed " + (animation.getSpeed() - 1)));
		headfoot.addExtra(SPEEDMINUS);
		
		//SPEED
		TextComponent SPEED = new TextComponent(" SPEED ");
		SPEED.setColor(ChatColor.WHITE);
		SPEED.setClickEvent(null);
		headfoot.addExtra(SPEED);
		
		//SPEED+
		TextComponent SPEEDPLUS = new TextComponent("»");
		SPEEDPLUS.setColor(ChatColor.GREEN);
		SPEEDPLUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " speed " + (animation.getSpeed() + 1)));
		headfoot.addExtra(SPEEDPLUS);
		
		//SPEEDSTAT
		TextComponent SPEEDSTAT = new TextComponent(" (" + animation.getSpeed() + ")");
		SPEEDSTAT.setColor(ChatColor.WHITE);
		SPEEDSTAT.setClickEvent(null);
		headfoot.addExtra(SPEEDSTAT);
		
		//RWAIT-
		TextComponent RWAITMINUS = new TextComponent("\n\n«");
		RWAITMINUS.setColor(ChatColor.GREEN);
		RWAITMINUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " rwait " + (animation.getReverseWait() - 1)));
		headfoot.addExtra(RWAITMINUS);
		
		//RWAIT
		TextComponent RWAIT = new TextComponent(" RWAIT ");
		RWAIT.setColor(ChatColor.WHITE);
		RWAIT.setClickEvent(null);
		headfoot.addExtra(RWAIT);
		
		//RWAIT+
		TextComponent RWAITPLUS = new TextComponent("»");
		RWAITPLUS.setColor(ChatColor.GREEN);
		RWAITPLUS.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " rwait " + (animation.getReverseWait() + 1)));
		headfoot.addExtra(RWAITPLUS);
		
		//RWAIT STAT
		TextComponent RWAITSTAT = new TextComponent(" (" + animation.getReverseWait() + ")");
		RWAITSTAT.setColor(ChatColor.WHITE);
		RWAITSTAT.setClickEvent(null); 
		headfoot.addExtra(RWAITSTAT);
		
		//SHOW TRIGGER
		TextComponent showtrigger = new TextComponent("\n\nSHOW TRIGGER");
		ChatColor color = animation.isShowTrigger() ? ChatColor.GREEN : ChatColor.RED;
		showtrigger.setColor(color);
		showtrigger.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " setshowtrigger " + !animation.isShowTrigger()));
		headfoot.addExtra(showtrigger);
		
		//Reverse
		TextComponent reverse = new TextComponent("\n\nREVERSE\n");
		ChatColor rColor = animation.isReverse() ? ChatColor.GREEN : ChatColor.RED;
		reverse.setColor(rColor);
		reverse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/animation edit " + string + " setreverse " + !animation.isReverse()));
		headfoot.addExtra(reverse);
		
		headfoot.addExtra(head);

		return headfoot;
	}

	public String getHelpMessage() {
		
		
		return "help message";
	}
	
	public String[] getAnimationListMessage() {
		
		String[] message = new String[4];
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("-=-  Animation list  -=-");
		
		for(Animation animation : Main.animations) {
			
			sb.append("\n" + animation.getName());
			sb.append(" : ");
			if(animation.getFrames() == null) {
				sb.append("0F");
			}else {
				sb.append(animation.getFrames().size() + "F");
			}
			if(animation.isReverse()) {
				sb.append(" R");
			}
			
		}
		
		message[0] = sb.toString();
		message[1] = "------------------------";
		message[2] = "<Name>  : Frames Reverse";
		message[3] = "------------------------";
		
		
		
		return message;
	}

}
