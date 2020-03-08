package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemMoteProcessor extends BlockItem {
	public ItemMoteProcessor() {
		super(Blocks.MOTE_PROCESSOR, new Properties().group(ItemGroup.NECRON));
		setRegistryName("mote_processor");
	}
}
