package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemNecrodermisOre extends BlockItem {
	public ItemNecrodermisOre() {
		super(Blocks.NECRON_ORE, new Properties().group(ItemGroup.NECRON));
		setRegistryName("necrodermis_ore");
	}
}
