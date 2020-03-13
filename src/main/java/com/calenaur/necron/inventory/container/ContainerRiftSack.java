package com.calenaur.necron.inventory.container;

import com.calenaur.necron.inventory.ItemStackHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRiftSack extends Container {
	public static final String NAME = "rift_sack";
	public static final int INVENTORY_SIZE = 27;
	private final IInventory inventory;
	private final ItemStack itemStack;

	public static ContainerRiftSack fromItemStack(int id, PlayerInventory playerInventory, ItemStack itemStack) {
		return new ContainerRiftSack(id, playerInventory, ItemStackHelper.readInventory(itemStack, INVENTORY_SIZE), itemStack);
	}

	public ContainerRiftSack(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, new Inventory(INVENTORY_SIZE), null);
	}

	public ContainerRiftSack(int id, PlayerInventory playerInventory, IInventory inventory, ItemStack itemStack) {
		super(ContainerTypes.RIFT_SACK, id);
		this.inventory = inventory;
		this.itemStack = itemStack;
		inventory.openInventory(playerInventory.player);
		for(int k = 0; k < 3; ++k)
			for(int l = 0; l < 9; ++l)
				this.addSlot(new SlotRiftSack(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));

		for(int i1 = 0; i1 < 3; ++i1)
			for(int k1 = 0; k1 < 9; ++k1)
				this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));

		for(int j1 = 0; j1 < 9; ++j1)
			this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.inventory.isUsableByPlayer(playerIn);
	}

	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack targetStack = slot.getStack();
			itemstack = targetStack.copy();
			if (index < this.inventory.getSizeInventory()) {
				if (!this.mergeItemStack(targetStack, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
					return ItemStack.EMPTY;
			} else if (!this.mergeItemStack(targetStack, 0, this.inventory.getSizeInventory(), false))
				return ItemStack.EMPTY;

			if (targetStack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else
				slot.onSlotChanged();
		}

		return itemstack;
	}

	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		if (itemStack != null)
			ItemStackHelper.writeInventory(itemStack, inventory);

		this.inventory.closeInventory(playerIn);
	}
}
