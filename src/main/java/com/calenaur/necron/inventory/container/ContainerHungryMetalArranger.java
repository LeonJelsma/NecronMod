package com.calenaur.necron.inventory.container;

import com.calenaur.necron.item.ItemRiftSack;
import com.calenaur.necron.item.Items;
import com.calenaur.necron.tileentity.TileEntityHungryMetalArranger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerHungryMetalArranger extends Container {
	public static final String NAME = "hungry_metal_arranger";

	private IInventory hungryMetalArrangerinventory;
	private World world;
	private TileEntityHungryMetalArranger tileEntityHungryMetalArranger;
	public static final int SLOT_HUNGRY_METAL_IN = 0;
	public static final int SLOT_RIFT_SACK = 1;
	public static final int SLOT_NECRON_INGOT = 2;
	public static final int SLOT_OUTPUT = 3;
	private static final int MINECRAFT_INVENTORY_SIZE = 27;
	private static final int MINECRAFT_HOTBAR_SIZE = 9;

	public ContainerHungryMetalArranger(int id, PlayerInventory playerInventory) {
		this(ContainerTypes.HUNGRY_METAL_ARRANGER, id, playerInventory, new Inventory(4), new TileEntityHungryMetalArranger());
	}

	public ContainerHungryMetalArranger(int id, PlayerInventory playerInventory, IInventory processorInventory, TileEntityHungryMetalArranger tileEntityHungryMetalArranger) {
		this(ContainerTypes.HUNGRY_METAL_ARRANGER, id, playerInventory, processorInventory, tileEntityHungryMetalArranger);
	}

	public ContainerHungryMetalArranger(ContainerType<ContainerHungryMetalArranger> containerTypeIn, int windowId, PlayerInventory playerInventoryIn, IInventory processorInventory, TileEntityHungryMetalArranger tileEntityHungryMetalArranger) {
		super(containerTypeIn, windowId);
		this.hungryMetalArrangerinventory = processorInventory;
		this.world = playerInventoryIn.player.world;
		this.tileEntityHungryMetalArranger = tileEntityHungryMetalArranger;
		addSlot(new Slot(processorInventory, 0, 32, 22));
		addSlot(new Slot(processorInventory, 1, 68, 22));
		addSlot(new Slot(processorInventory, 2, 32, 48));
		addSlot(new SlotGooMakerResult(processorInventory, 3, 114, 22, this));

		layoutPlayerInventorySlots(playerInventoryIn);
	}

	private void layoutPlayerInventorySlots(PlayerInventory playerInventoryIn){
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInventoryIn, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(playerInventoryIn, k, 8 + k * 18, 142));
		}
	}

    @Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return this.hungryMetalArrangerinventory.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex){
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(slotIndex);
		if (slot == null || !slot.getHasStack())
			return itemStack;

		ItemStack originItemStack = slot.getStack();
		itemStack = originItemStack.copy();
		if (slotIndex == SLOT_OUTPUT) { //Is the item in the output slot?
			if (!mergeItemStack(originItemStack, SLOT_OUTPUT + 1, this.inventorySlots.size(), true)) //Place item in the players inventory or hotbar
				return ItemStack.EMPTY;
			slot.onSlotChange(originItemStack, itemStack);
		} else if (slotIndex > SLOT_OUTPUT) {
			if (originItemStack.getItem() == Items.HUNGRY_METAL) {//Is the item a hungry metal block?
				if (!mergeItemStack(originItemStack, SLOT_HUNGRY_METAL_IN, SLOT_HUNGRY_METAL_IN + 1, false))//Place in the hungry metal slot
					return ItemStack.EMPTY;
			}else if (originItemStack.getItem() == Items.NECRON_INGOT) {//Is the item a necron ingot?
					if (!mergeItemStack(originItemStack, SLOT_NECRON_INGOT, SLOT_NECRON_INGOT + 1, false))//Place in the hungry metal slot
						return ItemStack.EMPTY;
			} else if (originItemStack.getItem() instanceof ItemRiftSack && hungryMetalArrangerinventory.getStackInSlot(SLOT_RIFT_SACK) == ItemStack.EMPTY) {//Does the item provide a valid target??
				if (!mergeItemStack(originItemStack, SLOT_RIFT_SACK, SLOT_RIFT_SACK + 1, false))//Place in the target material slot
					return ItemStack.EMPTY;
			} else if (slotIndex < hungryMetalArrangerinventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE) { //Is the item in the players inventory?
				if (!mergeItemStack(originItemStack, hungryMetalArrangerinventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE, this.inventorySlots.size(), false)) //Place in hotbar
					return ItemStack.EMPTY;
			} else if (slotIndex < this.inventorySlots.size() && !mergeItemStack(originItemStack, SLOT_OUTPUT + 1, hungryMetalArrangerinventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE, false)) //Is the item in the players? then place in inventory
				return ItemStack.EMPTY;
		} else if (!mergeItemStack(originItemStack, SLOT_OUTPUT + 1, this.inventorySlots.size(), false))
			return ItemStack.EMPTY;

		if (originItemStack.isEmpty()) { //Any items left?
			slot.putStack(ItemStack.EMPTY); //Tell the client that the old slot is now empty
		} else
			slot.onSlotChanged(); //Make sure the client knows that we changed their itemStack

		if (originItemStack.getCount() == itemStack.getCount()) //Contingency plan
			return ItemStack.EMPTY;

		slot.onTake(player, originItemStack);
		return itemStack;
	}


	public void make(){
		tileEntityHungryMetalArranger.take();
		detectAndSendChanges();
	}
}
