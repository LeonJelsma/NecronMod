package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.BlockMoteProcessor;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TileEntityMoteProcessor extends TileEntity implements IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "mote_processor_tile";

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int processingChargeLeft;
    private int processingProgress;
    private int requiredProcessingProgress;


    private IRecipeType recipeType = RecipeTypes.MOTE_PROCESSING;
    private IRecipe recipeUsed;


    public TileEntityMoteProcessor() {
        super(TileEntities.MOTE_PROCESSOR);
    }


    @Override
    public void tick() {
        boolean isProcessingFlag = this.isProcessing();
        boolean tileCHanged = false;

        if (!this.world.isRemote) {
            ItemStack[] fuel = {this.items.get(1), this.items.get(2)};

            if (this.isProcessing() || (!fuel[0].isEmpty() && !fuel[1].isEmpty()) &&!this.items.get(0).isEmpty()) {

                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<ProcessingRecipe>)this.recipeType, this, this.world).orElse(null);

                if (!this.isProcessing() && this.canProcess(irecipe)) {

                    this.requiredProcessingProgress = this.getProcessingTime();

                    if (!this.isProcessing()) {
                        tileCHanged = true;

                        if (isFuel(fuel[0]) && isFuel(fuel[1])){
                            processingChargeLeft += 200;

                            if (fuel[0].hasContainerItem()) {
                                this.items.set(1, fuel[0].getContainerItem());
                            } else{
                                fuel[0].shrink(1);
                            }
                            if (fuel[1].hasContainerItem()) {
                                this.items.set(2, fuel[1].getContainerItem());
                            } else{
                                fuel[1].shrink(1);
                            }
                        }

                        else
                        if (!fuel[1].isEmpty() && !fuel[1].isEmpty()) {


                            if (fuel[1].isEmpty()) {
                                this.items.set(1, fuel[1].getContainerItem());
                            }
                            if (fuel[0].isEmpty()) {
                                this.items.set(2, fuel[0].getContainerItem());
                            }
                        }
                    }
                }

                if (this.isProcessing() && this.canProcess(irecipe)) {
                    --this.processingChargeLeft;
                    ++this.processingProgress;
                    if (this.processingProgress >= this.requiredProcessingProgress) {
                        this.processingProgress = 0;
                        this.requiredProcessingProgress = this.getProcessingTime();
                        this.useRecipe(irecipe);
                        tileCHanged = true;
                    }
                } else {
                    this.processingProgress = 0;
                }
            }

            if (isProcessingFlag != this.isProcessing()) {
                tileCHanged = true;
                //this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockMoteProcessor.LIT, Boolean.valueOf(this.isProcessing())), 3);
            }
        }

        if (tileCHanged) {
            this.markDirty();
        }
    }



    protected int getProcessingTime() {
        return this.world.getRecipeManager().getRecipe((IRecipeType<ProcessingRecipe>)this.recipeType, this, this.world).map(ProcessingRecipe::getProcessingTime).orElse(200);
    }

    private void useRecipe(@Nullable IRecipe<?> recipe) {

        int input_slot = 0;
        int output_slot = 3;

        if (recipe != null && this.canProcess(recipe)) {
            ItemStack inputItem = this.items.get(input_slot);
            ItemStack recipeOutput = recipe.getRecipeOutput();
            ItemStack outputItem = this.items.get(output_slot);
            if (outputItem.isEmpty()) {
                this.items.set(output_slot , recipeOutput.copy());
            } else if (outputItem.getItem() == recipeOutput.getItem()) {
                outputItem.grow(recipeOutput.getCount());
            }

            if (!this.world.isRemote) {
                this.setRecipeUsed(recipe);
            }
            inputItem.shrink(1);
        }
    }

    private void setRecipeUsed(IRecipe recipe){
        recipeUsed = recipe;
    }

    public static boolean isFuel(ItemStack itemStack) {
        return itemStack.getItem() == Items.LAVA_BUCKET || itemStack.getItem() == Items.BLAZE_POWDER;
    }

    private boolean isProcessing() {
        return this.processingChargeLeft > 0;
    }

    protected boolean canProcess(@Nullable IRecipe<?> recipeIn) {
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(3);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerMoteProcessor(id, playerInventory, this);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
        this.processingChargeLeft = compound.getInt("processingChargeLeft");
        this.processingProgress = compound.getInt("processingProgress");
        this.requiredProcessingProgress = compound.getInt("requiredProcessingProgress");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("processingChargeLeft", this.processingChargeLeft);
        compound.putInt("processingProgress", this.processingProgress);
        compound.putInt("requiredProcessingProgress", this.requiredProcessingProgress);
        ItemStackHelper.saveAllItems(compound, this.items);
        super.write(compound);
        return compound;
    }

    @Override
    public int getSizeInventory() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.markDirty();
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world == null)
            return false;

        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clear() {
        this.items.clear();
    }
}

