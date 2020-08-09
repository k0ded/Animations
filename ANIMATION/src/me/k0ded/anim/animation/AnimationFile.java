package me.k0ded.anim.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.k0ded.anim.Main;
import me.k0ded.anim.structure.Schematic;

public class AnimationFile extends AbstractFile {

	public AnimationFile(Main main) {
		super(main, "animations.yml");
	}
	
	public void save(Animation anim) {
		try {
			config.set(anim.name + ".animlocx", anim.getAnimationLocation().getBlockX());
			config.set(anim.name + ".animlocy", anim.getAnimationLocation().getBlockY());
			config.set(anim.name + ".animlocz", anim.getAnimationLocation().getBlockZ());
			config.set(anim.name + ".animlocworld", anim.getAnimationLocation().getWorld().getName());
			config.set(anim.name + ".showtrigger", anim.showTrigger);
			config.set(anim.name + ".frames", anim.frames.size());
			config.set(anim.name + ".triggerlocx", anim.getTrigger().getBlockX());
			config.set(anim.name + ".triggerlocy", anim.getTrigger().getBlockY());
			config.set(anim.name + ".triggerlocz", anim.getTrigger().getBlockZ());
			config.set(anim.name + ".triggerlocworld", anim.getTrigger().getWorld().getName());
			config.set(anim.name + ".speed", anim.getSpeed());
			config.set(anim.name + ".reversewait", anim.getReverseWait());
			config.set(anim.name + ".reverse", anim.isReverse());
		} catch (NullPointerException e) {
			
		}
		save();
	}
	
	public void loadAnimations() {
		for(String key : config.getKeys(false)) {
			
			Location animationLocation = new Location(
					Bukkit.getWorld(config.getString(key + ".animlocworld")), 
					config.getInt(key + ".animlocx"), 
					config.getInt(key + ".animlocy"), 
					config.getInt(key + ".animlocz"));
			
			Location trigger;
			
			if(config.getString(key + ".triggerlocworld") == null) {
				trigger = null;
			}else {
				trigger = new Location(
						Bukkit.getWorld(config.getString(key + ".triggerlocworld")),
						config.getInt(key + ".triggerlocx"),
						config.getInt(key + ".triggerlocy"),
						config.getInt(key + ".triggerlocz"));
			}
			
			
			
			List<Schematic> frames = new ArrayList<Schematic>();
			
			if(config.getInt(key + ".frames") == 0) {
				
			}else {
				int abc = config.getInt(key + ".frames") + 1;
				for(int i = 1;i < abc; i++) {
					frames.add(Main.structureAPI.load(key + i));
				}
			}
			
			new Animation(
					key, 
					animationLocation, 
					config.getBoolean(key + ".showtrigger"), 
					frames, 
					trigger, 
					config.getInt(key + ".speed"), 
					config.getBoolean(key + ".reverse"), 
					config.getInt(key + ".reversewait"));
			
		}
	}

}
