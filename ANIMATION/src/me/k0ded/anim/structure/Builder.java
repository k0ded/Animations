package me.k0ded.anim.structure;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.k0ded.anim.animation.Animation;

public class Builder {
	
	Player p;
	Schematic cache;
	
	Block selection1;
	Block selection2;
	
	Animation settingTrigger = null;
	
	public Builder(Player player) {
		this.p = player;
	}
	
	public void settingTrigger(Animation anim) {
		settingTrigger = anim;
	}
	
	public Schematic getSelected() {
		if(selection1 == null) {
			return null;
		}
		if(selection2 == null) {
			return null;
		}
		
		return new Schematic(selection1, selection2);
	}

	public void setSelection1(Block block) {
		selection1 = block;	
	}
	
	public void setSelection2(Block block) {
		selection2 = block;
	}

	public boolean isSettingTrigger() {
		if(settingTrigger == null) {
			return false;
		}else {
			return true;
		}
	}
	
	public Animation getTriggerSet() {
		return settingTrigger;
	}

}
