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
			config.set(anim.name + ".frames", anim.frames.size());
			config.set(anim.name + ".speed", anim.getSpeed());
			config.set(anim.name + ".reversewait", anim.getReverseWait());
			config.set(anim.name + ".reverse", anim.isReverse());
			config.set(anim.name + ".triggerlocxmin", anim.getTrigger().getMinX());
			config.set(anim.name + ".triggerlocxmax", anim.getTrigger().getMaxX());
			config.set(anim.name + ".triggerlocymin", anim.getTrigger().getMinY());
			config.set(anim.name + ".triggerlocymax", anim.getTrigger().getMaxY());
			config.set(anim.name + ".triggerloczmin", anim.getTrigger().getMinZ());
			config.set(anim.name + ".triggerloczmax", anim.getTrigger().getMaxZ());
			config.set(anim.name + ".triggerlocworld", anim.getTrigger().middleLocation().getWorld().getName());
		} catch (NullPointerException e) {}
		save();
	}
	
	public void loadAnimations() {
		for(String key : config.getKeys(false)) {
			
			Location animationLocation = new Location(
					Bukkit.getWorld(config.getString(key + ".animlocworld")), 
					config.getInt(key + ".animlocx"), 
					config.getInt(key + ".animlocy"), 
					config.getInt(key + ".animlocz"));
			
			Trigger trigger;
			
			if(config.getString(key + ".triggerlocworld") == null) {
				trigger = null;
			}else {
				Location minLoc = new Location(Bukkit.getWorld(config.getString(key + ".triggerlocworld")),
						config.getInt(key + ".triggerlocxmin"),
						config.getInt(key + ".triggerlocymin"),
						config.getInt(key + ".triggerloczmin"));
				Location maxLoc = new Location(Bukkit.getWorld(config.getString(key + ".triggerlocworld")),
						config.getInt(key + ".triggerlocxmax"),
						config.getInt(key + ".triggerlocymax"),
						config.getInt(key + ".triggerloczmax"));
				
				Location[] locs = new Location[2];
				locs[0] = minLoc;
				locs[1] = maxLoc;
				
				trigger = new Trigger(locs);
			}
			
			
			
			List<Schematic> frames = new ArrayList<Schematic>();
			
			if(config.getInt(key + ".frames") != 0){
				int abc = config.getInt(key + ".frames") + 1;
				for(int i = 1;i < abc; i++) {
					frames.add(Main.structureAPI.load(key + i));
				}
			}
			
			new Animation(
					key, 
					animationLocation,  
					frames, 
					trigger, 
					config.getInt(key + ".speed"), 
					config.getBoolean(key + ".reverse"), 
					config.getInt(key + ".reversewait"));
			
		}
	}

	public void delete(Animation anim) {
		config.set(anim.name, null);
		save();
	}

}
