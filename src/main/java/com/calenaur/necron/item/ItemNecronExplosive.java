package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemNecronExplosive extends BlockItem {
	public ItemNecronExplosive() {
		super(Blocks.NECRON_EXPLOSIVE, new Properties().group(ItemGroup.NECRON));
		setRegistryName("necron_explosive");
	}
}
