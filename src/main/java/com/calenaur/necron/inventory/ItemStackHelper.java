package com.calenaur.necron.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;

public class ItemStackHelper {
	public static void writeInventory(ItemStack itemStack, IInventory inventory) {
		CompoundNBT tag;
		if (itemStack.getTag() != null){
			tag = itemStack.getTag();
		} else
			tag = new CompoundNBT();

		NonNullList<ItemStack> items = NonNullList.withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
		for (int i=0; i<items.size(); i++)
			if (i < inventory.getSizeInventory())
				items.set(i, inventory.getStackInSlot(i));

		net.minecraft.inventory.ItemStackHelper.saveAllItems(tag, items);
		itemStack.setTag(tag);
	}

	public static Inventory readInventory(ItemStack itemStack, int size) {
		if (itemStack.getTag() == null)
			return new Inventory(size);

		NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
		net.minecraft.inventory.ItemStackHelper.loadAllItems(itemStack.getTag(), items);
		return new Inventory(items.toArray(new ItemStack[]{}));
	}
}
