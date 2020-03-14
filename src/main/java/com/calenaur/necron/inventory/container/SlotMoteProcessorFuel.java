package com.calenaur.necron.inventory.container;

import com.calenaur.necron.tileentity.TileEntityMoteProcessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotMoteProcessorFuel extends Slot {
	private final Item item;

	public SlotMoteProcessorFuel(IInventory inventoryIn, int index, int xPosition, int yPosition, Item allowed) {
		super(inventoryIn, index, xPosition, yPosition);
		this.item = allowed;
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() == item && TileEntityMoteProcessor.isFuel(stack);
	}
}
