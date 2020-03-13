package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlockPlasmaGeneratorPart extends BlockItem {
	public ItemBlockPlasmaGeneratorPart() {
		super(Blocks.PLASMA_GENERATOR_PART, new Properties().group(ItemGroup.NECRON));
		setRegistryName("plasma_generator_part");
	}
}
