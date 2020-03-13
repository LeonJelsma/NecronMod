package com.calenaur.necron.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotHungryMetalFilter extends Slot {
	public SlotHungryMetalFilter(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof BlockItem || stack.getItem() == Items.LAVA_BUCKET || stack.getItem() == Items.WATER_BUCKET;
	}
}
