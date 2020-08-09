package me.k0ded.anim;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.k0ded.anim.structure.Builder;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(e.getPlayer().hasPermission("pottercraft.builder")) {
			if(e.getAction().toString() == Action.RIGHT_CLICK_BLOCK.toString()) {
				if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) {
					
					if(Main.builders.containsKey(e.getPlayer())) {
						Builder builder = Main.builders.get(e.getPlayer());
						if(builder.isSettingTrigger()) {
							builder.getTriggerSet().setTrigger(e.getClickedBlock().getLocation());
							builder.settingTrigger(null);
							e.setCancelled(true);
							return;
						}
						
						Main.builders.get(e.getPlayer()).setSelection1(e.getClickedBlock());
						e.getPlayer().sendMessage("§aSelection 1 set.");
						
					}else {
						Builder builder = new Builder(e.getPlayer());
						Main.builders.put(e.getPlayer(), builder);
						builder.setSelection1(e.getClickedBlock());
						e.getPlayer().sendMessage("§aSelection 1 set.");
					}
					e.setCancelled(true);
				}
				
				
			}
			if(e.getAction().toString() == Action.LEFT_CLICK_BLOCK.toString()) {
				if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) {
					
					if(Main.builders.containsKey(e.getPlayer())) {
						Builder builder = Main.builders.get(e.getPlayer());
						if(builder.isSettingTrigger()) {
							builder.getTriggerSet().setTrigger(e.getClickedBlock().getLocation());
							builder.settingTrigger(null);
							e.setCancelled(true);
							return;
						}
						
						
						builder.setSelection2(e.getClickedBlock());
						e.getPlayer().sendMessage("§aSelection 2 set.");
					}else {
						Builder builder = new Builder(e.getPlayer());
						Main.builders.put(e.getPlayer(), builder);
						builder.setSelection2(e.getClickedBlock());
						e.getPlayer().sendMessage("§aSelection 2 set.");
					}
					e.setCancelled(true);
				}
				
				
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
