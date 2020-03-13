package com.calenaur.necron.inventory.container;

import com.calenaur.necron.inventory.ItemStackHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHungryMetalFilter extends Container {
	public static final String NAME = "hungry_metal_filter";
	public static final int INVENTORY_SIZE = 27;
	private final IInventory inventory;
	private final ItemStack itemStack;

	public static ContainerHungryMetalFilter fromItemStack(int id, PlayerInventory playerInventory, ItemStack itemStack) {
		return new ContainerHungryMetalFilter(id, playerInventory, ItemStackHelper.readInventory(itemStack, INVENTORY_SIZE), itemStack);
	}

	public ContainerHungryMetalFilter(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, new Inventory(INVENTORY_SIZE), null);
	}

	public ContainerHungryMetalFilter(int id, PlayerInventory playerInventory, IInventory inventory, ItemStack itemStack) {
		super(ContainerTypes.HUNGRY_METAL_FILTER, id);
		this.inventory = inventory;
		this.itemStack = itemStack;
		inventory.openInventory(playerInventory.player);
		for(int k = 0; k < 3; ++k)
			for(int l = 0; l < 9; ++l)
				this.addSlot(new SlotHungryMetalFilter(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));

		for(int i1 = 0; i1 < 3; ++i1)
			for(int k1 = 0; k1 < 9; ++k1)
				this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));

		for(int j1 = 0; j1 < 9; ++j1)
			this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
	}

	@Override
	public boolean canDragIntoSlot(Slot slotIn) {
		return !(slotIn instanceof SlotHungryMetalFilter);
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		ItemStack resultStack = ItemStack.EMPTY;
		PlayerInventory playerInventory = player.inventory;

		if (slotId != -999 && clickTypeIn == ClickType.PICKUP && (dragType == 0 || dragType == 1)) {
			if (slotId < 0) {
				return ItemStack.EMPTY;
			}

			Slot clickedSlot = this.inventorySlots.get(slotId);
			if (clickedSlot != null) {
				ItemStack clickedStack = clickedSlot.getStack();
				ItemStack heldStack = playerInventory.getItemStack();
				if (!clickedStack.isEmpty()) {
					resultStack = clickedStack.copy();
				}

				if (clickedStack.isEmpty()) {
					if (!heldStack.isEmpty() && clickedSlot.isItemValid(heldStack)) {
						int j2 = dragType == 0 ? heldStack.getCount() : 1;
						if (j2 > clickedSlot.getItemStackLimit(heldStack)) {
							j2 = clickedSlot.getItemStackLimit(heldStack);
						}

						if (clickedSlot instanceof SlotHungryMetalFilter) {
							clickedSlot.putStack(new ItemStack(heldStack.getItem()));
						} else
							clickedSlot.putStack(heldStack.split(j2));
					} else {
						resultStack = heldStack;
					}
				} else if (clickedSlot instanceof SlotHungryMetalFilter) {
					ItemStack stack = ItemStack.EMPTY;
					if (!heldStack.isEmpty() && clickedSlot.isItemValid(heldStack))
						stack = new ItemStack(heldStack.getItem());
					resultStack = ItemStack.EMPTY;
					clickedSlot.putStack(stack);
				} else
					resultStack = super.slotClick(slotId, dragType, clickTypeIn, player);
			}
		} else
			resultStack = super.slotClick(slotId, dragType, clickTypeIn, player);

		return resultStack;
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
		return ItemStack.EMPTY;
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
