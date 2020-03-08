package com.calenaur.necron.item.necron;

import com.calenaur.necron.item.ItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlockNecron extends BlockItem {
	public ItemBlockNecron(Block block, String registryName) {
		super(block, new Properties().group(ItemGroup.NECRON));
		setRegistryName(registryName);
	}
}
