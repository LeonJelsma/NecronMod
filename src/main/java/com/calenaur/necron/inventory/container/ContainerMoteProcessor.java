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
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerMoteProcessor extends Container {
	public static final String NAME = "mote_processor";

	private IInventory processorInventory;
	private World world;
	private IRecipeType<ProcessingRecipe> recipeType;
	public IIntArray processorData;

	public ContainerMoteProcessor(int id, PlayerInventory playerInventory) {
		this(ContainerTypes.MOTE_PROCESSOR, RecipeTypes.MOTE_PROCESSING, id, playerInventory, new Inventory(4), new IntArray(3));
	}

	public ContainerMoteProcessor(int id, PlayerInventory playerInventory, IInventory processorInventory, IIntArray processorData) {
		this(ContainerTypes.MOTE_PROCESSOR, RecipeTypes.MOTE_PROCESSING, id, playerInventory, processorInventory, processorData);
	}

	public ContainerMoteProcessor(ContainerType<ContainerMoteProcessor> containerTypeIn, IRecipeType<ProcessingRecipe> recipeTypeIn, int windowId, PlayerInventory playerInventoryIn, IInventory processorInventory, IIntArray processorData) {
		super(containerTypeIn, windowId);
		this.processorData = processorData;
		this.processorInventory = processorInventory;
		this.world = playerInventoryIn.player.world;
		this.recipeType = recipeTypeIn;
		addSlot(new Slot(processorInventory, 0, 45, 17));
		addSlot(new SlotMoteProcessorFuel(processorInventory, 1, 34, 53));
		addSlot(new SlotMoteProcessorFuel(processorInventory, 2, 56, 53));
		addSlot(new SlotMoteProcessorResult(playerInventoryIn.player, processorInventory, 3, 116, 35));
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
		if (slotIndex == 3) { //Is the item in the output slot?
			if (!mergeItemStack(originItemStack, 4, 40, true)) //Place item in the players inventory or toolbar
				return ItemStack.EMPTY;
			slot.onSlotChange(originItemStack, itemStack);
		} else if (slotIndex > 3) {
			if (itemHasRecipe(originItemStack)) {//Is the item part of a recipe?
				if (!mergeItemStack(originItemStack, 0, 1, false))//Place in the ingredient slot
					return ItemStack.EMPTY;
			} else if (TileEntityMoteProcessor.isFuel(originItemStack)) {//Is the item considered fuel for this mote processor?
				if (!mergeItemStack(originItemStack, 1, 3, false))//Place in one of the 2 fuel slots
					return ItemStack.EMPTY;
			} else if (slotIndex < 31) { //Is the item in the players inventory?
				if (!mergeItemStack(originItemStack, 30, 40, false)) //Place in toolbar
					return ItemStack.EMPTY;
			} else if (slotIndex < 40 && !mergeItemStack(originItemStack, 4, 31, false)) //Is the item in the players? then place in inventory
				return ItemStack.EMPTY;
		} else if (!mergeItemStack(originItemStack, 4, 40, false))
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
