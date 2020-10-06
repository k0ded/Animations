package me.k0ded.anim;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.k0ded.anim.animation.Animation;
import me.k0ded.anim.structure.Builder;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		
		/// Builder type stuff
		if(e.getPlayer().hasPermission("pottercraft.builder")) {
			if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) {
				
				int selection = e.getAction().toString() == Action.RIGHT_CLICK_BLOCK.toString() ? 1 : 
					e.getAction().toString() == Action.LEFT_CLICK_BLOCK.toString() ? 2 : 0;
					
				
				setSelection(selection, e.getPlayer(), e.getClickedBlock());
				e.setCancelled(true);
				return;	
			} 
			
			Builder builder = getBuilder(e.getPlayer());
			
			if(builder.isSettingTrigger()) {
				builder.getTriggerSet().setTrigger(e.getClickedBlock().getLocation());
				builder.settingTrigger(null);
			}
		}
		
		/// Player type stuff
		for (Animation anim : Main.animations) {
			if(anim.getTrigger() == null)
				continue;
			if(anim.getTrigger().getBlock().equals(e.getClickedBlock())) {
				anim.run();
			}
		}
		
	}
	
	private Builder getBuilder(Player player) {
		Builder builder = Main.builders.containsKey(player) ? Main.builders.get(player) : new Builder(player);
		Main.builders.put(player, builder);
		return builder;
	}
	
	/// Sets either selection 1 or 2
	private void setSelection(int i, Player player, Block clickedBlock) {
		
		Builder builder = getBuilder(player);
			
		if(i == 1) {
			if(builder.getSelection1() != null && builder.getSelection1().equals(clickedBlock))
				return;
			builder.setSelection1(clickedBlock);
		}else if(i == 2){
			if(builder.getSelection2() != null && builder.getSelection2().equals(clickedBlock))
				return;
			builder.setSelection2(clickedBlock);
		}else
			return;
			
		player.sendMessage("§aSelection " + i + " set.");
	}

}
