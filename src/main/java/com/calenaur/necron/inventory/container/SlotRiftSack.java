package com.calenaur.necron.inventory.container;

import com.calenaur.necron.item.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class SlotRiftSack extends Slot {
	public SlotRiftSack(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() != Items.RIFT_SACK;
	}
}
