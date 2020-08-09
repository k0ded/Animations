package me.k0ded.anim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.k0ded.anim.animation.Animation;
import me.k0ded.anim.animation.AnimationFile;
import me.k0ded.anim.cmds.AnimationCommand;
import me.k0ded.anim.structure.Builder;
import me.k0ded.anim.structure.StructureAPI;

public class Main extends JavaPlugin {

	public static StructureAPI structureAPI;
	public static HashMap<Player, Builder> builders = new HashMap<Player, Builder>();
	public static List<Animation> animations = new ArrayList<Animation>();
	public static AnimationFile animationFile;
	public static Main instance;
	
	@Override
	public void onEnable() {
		if(!this.getDataFolder().exists()) {
			this.getDataFolder().mkdirs();
		}
			
		
		Main.instance = this;
		Main.structureAPI = new StructureAPI(this);
		AnimationFile afile = new AnimationFile(this);
		//Loads animations that put themselves in the animations list!
		afile.loadAnimations();
		Main.animationFile = afile;
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginCommand("animation").setExecutor(new AnimationCommand());
		
	}
	
}
