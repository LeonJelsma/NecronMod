package com.calenaur.necron.inventory.container;

import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import com.calenaur.necron.tileentity.TileEntityMoteProcessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public class ContainerMoteProcessor extends Container {
	public static final String NAME = "mote_processor";
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL_LEFT = 1;
	public static final int SLOT_FUEL_RIGHT = 2;
	public static final int SLOT_OUTPUT = 3;
	private static final int MINECRAFT_INVENTORY_SIZE = 27;
	private static final int MINECRAFT_HOTBAR_SIZE = 9;

	public IIntArray processorData;
	private World world;
	private IInventory processorInventory;
	private IRecipeType<ProcessingRecipe> recipeType;

	public ContainerMoteProcessor(int id, PlayerInventory playerInventory) {
		this(id, playerInventory, new Inventory(4), new IntArray(3));
	}

	public ContainerMoteProcessor(int windowId, PlayerInventory playerInventoryIn, IInventory processorInventory, IIntArray processorData) {
		super(ContainerTypes.MOTE_PROCESSOR, windowId);
		this.recipeType = RecipeTypes.MOTE_PROCESSING;
		this.processorData = processorData;
		this.processorInventory = processorInventory;
		this.world = playerInventoryIn.player.world;
		addSlot(new Slot(processorInventory, SLOT_INPUT, 45, 17));
		addSlot(new SlotMoteProcessorFuel(processorInventory, SLOT_FUEL_LEFT, 34, 53, Items.MAGMA_CREAM));
		addSlot(new SlotMoteProcessorFuel(processorInventory, SLOT_FUEL_RIGHT, 56, 53, Items.BLAZE_POWDER));
		addSlot(new SlotMoteProcessorResult(processorInventory, SLOT_OUTPUT, 116, 35));
		layoutPlayerInventorySlots(playerInventoryIn);

		this.trackIntArray(processorData);
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
		return this.processorInventory.isUsableByPlayer(playerIn);
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
			if (itemHasRecipe(originItemStack)) {//Is the item part of a recipe?
				if (!mergeItemStack(originItemStack, SLOT_INPUT, SLOT_INPUT + 1, false))//Place in the ingredient slot
					return ItemStack.EMPTY;
			} else if (TileEntityMoteProcessor.isFuel(originItemStack)) {//Is the item considered fuel for this mote processor?
				if (!mergeItemStack(originItemStack, SLOT_FUEL_LEFT, SLOT_FUEL_RIGHT + 1, false))//Place in one of the 2 fuel slots
					return ItemStack.EMPTY;
			} else if (slotIndex < processorInventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE) { //Is the item in the players inventory?
				if (!mergeItemStack(originItemStack, processorInventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE, this.inventorySlots.size(), false)) //Place in hotbar
					return ItemStack.EMPTY;
			} else if (slotIndex < this.inventorySlots.size() && !mergeItemStack(originItemStack, SLOT_OUTPUT + 1, processorInventory.getSizeInventory() + MINECRAFT_INVENTORY_SIZE, false)) //Is the item in the players? then place in inventory
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

	protected boolean itemHasRecipe(ItemStack itemStack) {
		return this.world.getRecipeManager().getRecipe(this.recipeType, new Inventory(itemStack), this.world).isPresent();
	}
}