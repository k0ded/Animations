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
	boolean isRunning;
	List<Schematic> frames;
	Trigger trigger;
	
	
	int speed = 1;
	int reverseWait = 20;
	boolean reverse = true;
	
	public Animation(String name) {
		this.name = name;
		Main.animations.add(this);
	}
	
	public Animation(String name, Location animationLocation, List<Schematic> frames, Trigger trigger, int speed, boolean reverse, int reverseWait) {
		this.name = name;
		this.animationLocation = animationLocation;
		this.frames = frames;
		this.trigger = trigger;
		this.speed = speed;
		this.reverse = reverse;
		this.reverseWait = reverseWait;
		Main.animations.add(this);
		if(frames.size() > 0) {
			Main.structureAPI.paste(frames.get(0), animationLocation);
		}
	}
	
	public void delete() {
		for(int i = 1;i < (frames.size() + 1) * 2; i++) {
			Main.structureAPI.remove(name + i);
		}
		frames.clear();
		Main.animationFile.delete(this);
		Main.animations.remove(this);
	}
	
	public boolean run() {
		if(!canRun())
			return false;
		
		if(isRunning)
			return true;
		pasteFrames(frames, 0, isReverse());
		return true;
	}
	
	private void pasteFrames(List<Schematic> f, int i, boolean reverse) {
		isRunning = true;
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
					isRunning = false;
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
				
				if(i <= 0) {
					isRunning = false;
					return;
				}
				pasteFramesBackwards(f, i - 1); 
				Main.structureAPI.paste(f.get(i - 1), animationLocation);
				
			}
		}, speed);
		
	}
	
	public boolean canRun() {
		if(frames == null) {
			return false;
		}
		if(animationLocation == null) {
			return false;
		}
		return true;
	}

	public void addFrame(Schematic frame) {
		if(frames == null)
			frames = new ArrayList<Schematic>();
		frames.add(frame);
		if(animationLocation == null)
			animationLocation = frame.animationLocation;
		Main.structureAPI.save(this.name + frames.size(), frame);
	}
	
	public void removeFrame() {
		if(frames == null)
			return;
		Main.structureAPI.remove(this.name + frames.size());
		frames.remove(frames.size());
		
	}
	
	
	
	
	
	
	//Getters and Setters
	 public Location getAnimationLocation() {
		return animationLocation;
	}

	public void setAnimationLocation(Location animationLocation) {
		this.animationLocation = animationLocation;
	}

	public List<Schematic> getFrames() {
		return frames;
	}
	
	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
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
