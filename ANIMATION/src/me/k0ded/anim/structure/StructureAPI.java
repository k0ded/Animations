package me.k0ded.anim.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
 
public class StructureAPI {
 
    Plugin plugin;
 
    public StructureAPI(Plugin pl){
        this.plugin = pl;
    }
 
    /**
    * Pastes a structure to a desired location
    */
 
    public void paste(Schematic schem, Location l){
    	for(int x = 0; x < schem.blocks.length; x++){
 
            for(int y = 0; y < schem.blocks[x].length; y++){
 
                for(int z = 0; z < schem.blocks[x][y].length; z++){
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    b.setBlockData(Bukkit.createBlockData(schem.blocks[x][y][z]));
                    b.getState().update(true);
                }
 
            }
        }
    }
 
    /**
    * Save a structure with a desired name
    */
 
    public void save(String name, Schematic schem){
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
        File dir = new File(plugin.getDataFolder() + "/schematics");
 
        try {
                dir.mkdirs();
                f.createNewFile();
        } catch (IOException e1) {
                e1.printStackTrace();
            }
 
        try{
               fout = new FileOutputStream(f);
               oos = new ObjectOutputStream(fout);
               oos.writeObject(schem.blocks);
        } catch (Exception e) {
               e.printStackTrace();
        }finally {
               if(oos  != null){
                   try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    
    /**
     * Remove file from filesystem if not used to save space.
     */
    
    public void remove(String name){
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
        if(!f.exists())
        	return;
        f.delete();
    }
 
    /**
    * Load structure from file
    */
 
    public Schematic load(String name){
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
 
        String[][][] loadedBlocks = new String[0][0][0];
 
        try {
            FileInputStream streamIn = new FileInputStream(f);
           ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
           
           loadedBlocks = (String[][][])objectinputstream.readObject();
 
           objectinputstream.close();
 
       } catch (Exception e) {
 
           e.printStackTrace();
    }
 
        return new Schematic(loadedBlocks);
    }

}