package com.calenaur.necron.inventory.container;

import com.calenaur.necron.tileentity.TileEntityMoteProcessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotMoteProcessorFuel extends Slot {
	private Item item;
	public SlotMoteProcessorFuel(IInventory inventoryIn, int index, int xPosition, int yPosition, Item allowed) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item = allowed;
	}

	/**
	 * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
	 */
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == item && TileEntityMoteProcessor.isFuel(stack);
	}

	public int getItemStackLimit(ItemStack stack) {
		return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
	}

	public static boolean isBucket(ItemStack stack) {
		return stack.getItem() == Items.BUCKET;
	}
}
