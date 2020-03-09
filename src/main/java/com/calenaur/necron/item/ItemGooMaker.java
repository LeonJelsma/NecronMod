package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemGooMaker extends BlockItem {
	public ItemGooMaker() {
		super(Blocks.GOO_MAKER, new Properties().group(ItemGroup.NECRON));
		setRegistryName("goo_maker");
	}
}
