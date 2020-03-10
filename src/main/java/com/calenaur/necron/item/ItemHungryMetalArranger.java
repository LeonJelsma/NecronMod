package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemHungryMetalArranger extends BlockItem {
	public ItemHungryMetalArranger() {
		super(Blocks.HUNGRY_METAL_ARRANGER, new Properties().group(ItemGroup.NECRON));
		setRegistryName("hungry_metal_arranger");
	}
}
