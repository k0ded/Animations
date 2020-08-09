package me.k0ded.anim.structure;

import org.bukkit.block.Block;

public class Schematic {
	
	String[][][] blocks;
	byte[][][] data;
	
	public Schematic(Block block, Block block2) {
		blocks = getStructure(block, block2);
	}
	
	public Schematic(String[][][] blocks, byte[][][] data) {
		this.blocks = blocks;
		this.data = data;
	}
	
	@SuppressWarnings("deprecation")
	public String[][][] getStructure(Block block, Block block2){
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
 
 
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        String[][][] blocks = new String[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
        byte[][][] data = new byte[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
        
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
                    Block b = block.getWorld().getBlockAt(x, y, z);
                    data[x-minX][y-minY][z-minZ] = b.getData();
                    blocks[x-minX][y-minY][z-minZ] = b.getType().name();
                }
            }
        }
        this.data = data;
        return blocks;
 
    }

}
