package me.k0ded.anim.structure;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Schematic {
	
	public Location animationLocation;
	String[][][] blocks;
	
	public Schematic(Block block, Block block2) {
		blocks = getStructure(block, block2);
	}
	
	public Schematic(String[][][] blocks) {
		this.blocks = blocks;
	}
	
	int minX, minZ, minY;
	int maxX, maxZ, maxY;
	World world;
	
	public String[][][] getStructure(Block block, Block block2){
 
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        String[][][] blocks = new String[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
        world = block.getWorld();
        animationLocation = new Location(world, minX, minY, minZ);
        
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
                    Block b = world.getBlockAt(x, y, z);
                    blocks[x-minX][y-minY][z-minZ] = b.getBlockData().getAsString();
                }
            }
        }
        return blocks;
    }
	
	public Location[] getTriggerParam() {
		Location[] locArr = new Location[2];
		locArr[0] = new Location(world, minX, minY, minZ);
		locArr[1] = new Location(world, maxX, maxY, maxZ);
		return locArr;
	}

}
