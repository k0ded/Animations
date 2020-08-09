package me.k0ded.anim.animation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.k0ded.anim.Main;
import me.k0ded.anim.structure.Schematic;

public class Animation {
	
	String name;
	Location animationLocation;
	boolean showTrigger = true;
	List<Schematic> frames;
	Location trigger;
	int speed = 1;
	int reverseWait = 20;
	boolean reverse = true;
	
	public Animation(String name) {
		this.name = name;
		Main.animations.add(this);
	}
	
	public Animation(String name, Location animationLocation, boolean showTrigger, List<Schematic> frames, Location trigger, int speed, boolean reverse, int reverseWait) {
		this.name = name;
		this.animationLocation = animationLocation;
		this.showTrigger = showTrigger;
		this.frames = frames;
		this.trigger = trigger;
		this.speed = speed;
		this.reverse = reverse;
		this.reverseWait = reverseWait;
		Main.animations.add(this);
	}
	
	public boolean run() {
		if(!canRun())
			return false;
		
		pasteFrames(frames, 0, isReverse());

		return true;
	}
	
	private void pasteFrames(List<Schematic> f, int i, boolean reverse) {
		Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
			
			@Override
			public void run() {
				
				if(f.size() == i) {
					if(reverse) {
						Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
							
							@Override
							public void run() {
								pasteFramesBackwards(frames, frames.size() );
							}
						}, reverseWait);
					}
					return;
				}
				pasteFrames(f, i + 1, reverse);
				
				Main.structureAPI.paste(f.get(i), animationLocation);
				
			}
		}, speed);
	}
	
	private void pasteFramesBackwards(List<Schematic> f, int i) {
	
		Bukkit.getServer().getScheduler().runTaskLater(Main.instance, new Runnable() {
			
			@Override
			public void run() {
				
				if(i == 0) {
					return;
				}
				pasteFramesBackwards(f, i - 1); 
				
				Main.structureAPI.paste(f.get(i - 1), animationLocation);
				
			}
		}, speed);
	}
	
	public boolean canRun() {
		boolean canrun = true;
		if(frames == null) {
			System.out.println(name + " doesn't have any frames!");
			canrun = false;
		}
		
		if(animationLocation == null) {
			System.out.println(name + " doesn't have an animation location!");
			canrun = false;
		}
		return canrun;
	}
	
	//Getters and Setters
 	public Location getAnimationLocation() {
		return animationLocation;
	}

	public void setAnimationLocation(Location animationLocation) {
		this.animationLocation = animationLocation;
	}

	public boolean isShowTrigger() {
		return showTrigger;
	}

	public void setShowTrigger(boolean showTrigger) {
		this.showTrigger = showTrigger;
	}

	public List<Schematic> getFrames() {
		return frames;
	}

	public void addFrame(Schematic frame) {
		if(frames == null)
			frames = new ArrayList<Schematic>();
		frames.add(frame);
		Main.structureAPI.save(this.name + frames.size(), frame);
	}
	
	public void removeFrame() {
		if(frames == null)
			return;
		Main.structureAPI.remove(this.name + frames.size());
		frames.remove(frames.size());
		
	}

	public Location getTrigger() {
		return trigger;
	}

	public void setTrigger(Location trigger) {
		this.trigger = trigger;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getReverseWait() {
		return reverseWait;
	}

	public void setReverseWait(int reverseWait) {
		this.reverseWait = reverseWait;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public String getName() {
		return name;
	}
}
