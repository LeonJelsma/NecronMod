package com.calenaur.necron.block.necron;

import net.minecraft.block.StairsBlock;


public class BlockNecronStairs extends StairsBlock {
	private static final BlockNecronPlain template = new BlockNecronPlain();
	public BlockNecronStairs() {
    	super(template::getDefaultState, Properties.from(template));
    	setRegistryName("necron_stairs");
	}
}
