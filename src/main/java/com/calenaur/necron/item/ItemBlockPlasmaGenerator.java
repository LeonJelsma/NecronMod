package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlockPlasmaGenerator extends BlockItem {
	public ItemBlockPlasmaGenerator() {
		super(Blocks.PLASMA_GENERATOR, new Properties().group(ItemGroup.NECRON));
		setRegistryName("plasma_generator");
	}
}
