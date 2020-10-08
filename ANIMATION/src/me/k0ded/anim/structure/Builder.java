package me.k0ded.anim.structure;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.k0ded.anim.animation.Animation;
import me.k0ded.anim.animation.Trigger;

public class Builder {
	
	Player p;
	Schematic cache;
	
	Block selection1;
	Block selection2;
	
	public Builder(Player player) {
		this.p = player;
	}
	
	public void setTrigger(Animation anim) {
		if(selection1 == null) {
			return;
		}
		if(selection2 == null) {
			return;
		}
		Schematic schem = new Schematic(selection1, selection2);
		schem.getStructure(selection1, selection2);
		anim.setTrigger(new Trigger(schem.getTriggerParam()));
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
	
	public Block getSelection1() {
		return selection1;	
	}
	
	public Block getSelection2() {
		return selection2;
	}

}
