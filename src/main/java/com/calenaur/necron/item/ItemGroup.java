package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.ItemStack;

public class ItemGroup {
	public static final net.minecraft.item.ItemGroup NECRON = new net.minecraft.item.ItemGroup("necron") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Blocks.NECRON_ORE);
		}
	};
}
