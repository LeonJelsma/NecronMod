package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.BlockMoteProcessor;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import javax.annotation.Nullable;

public class TileEntityMoteProcessor extends TileEntity implements ISidedInventory, IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "mote_processor_tile";
    private static final int DEFAULT_PROCESSING_TIME = 300;
    private static final int[] SLOTS_UP = new int[]{ContainerMoteProcessor.SLOT_INPUT};
    private static final int[] SLOTS_DOWN = new int[]{ContainerMoteProcessor.SLOT_OUTPUT};
    private static final int[] SLOTS_HORIZONTAL = new int[]{ContainerMoteProcessor.SLOT_FUEL_LEFT, ContainerMoteProcessor.SLOT_FUEL_RIGHT};
    private static final ImmutableList<Item> FUEL = ImmutableList.of(Items.MAGMA_CREAM, Items.BLAZE_POWDER);
    private static final int FUEL_BURN_TIME = 200;

    public final IIntArray processorData = new IIntArray() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return TileEntityMoteProcessor.this.processingChargeLeft;
                case 1:
                    return TileEntityMoteProcessor.this.processingProgress;
                case 2:
                    return TileEntityMoteProcessor.this.requiredProcessingProgress;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    TileEntityMoteProcessor.this.processingChargeLeft = value;
                    break;
                case 1:
                    TileEntityMoteProcessor.this.processingProgress = value;
                    break;
                case 2:
                    TileEntityMoteProcessor.this.requiredProcessingProgress = value;
            }

        }

        public int size() {
            return 3;
        }
    };

    private IRecipeType<ProcessingRecipe> recipeType = RecipeTypes.MOTE_PROCESSING;
    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int processingChargeLeft;
    private int processingProgress;
    private int requiredProcessingProgress;
    
    public TileEntityMoteProcessor() {
        super(TileEntities.MOTE_PROCESSOR);
    }
    
    @Override
    public void tick() {
        boolean wasProcessing = isProcessing();
        boolean tileChanged = false;

        if (this.world == null)
            return;
        
        if (this.world.isRemote)
            return;

        ItemStack[] fuel = {this.items.get(ContainerMoteProcessor.SLOT_FUEL_LEFT), this.items.get(ContainerMoteProcessor.SLOT_FUEL_RIGHT)};
        if (this.isProcessing() || (!fuel[0].isEmpty() && !fuel[1].isEmpty() && !this.items.get(ContainerMoteProcessor.SLOT_INPUT).isEmpty())) {
            IRecipe<?> recipe = this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).orElse(null);
            if (!this.isProcessing() && this.canProcess(recipe)) {
                this.requiredProcessingProgress = this.getProcessingTime();
                if (!this.isProcessing()) {
                    tileChanged = true;
                    if (isFuel(fuel[0]) && isFuel(fuel[1])){
                        processingChargeLeft += FUEL_BURN_TIME;
                        if (fuel[0].hasContainerItem()) {
                            this.items.set(ContainerMoteProcessor.SLOT_FUEL_LEFT, fuel[0].getContainerItem());
                        } else
                            fuel[0].shrink(1);

                        if (fuel[1].hasContainerItem()) {
                            this.items.set(ContainerMoteProcessor.SLOT_FUEL_RIGHT, fuel[1].getContainerItem());
                        } else
                            fuel[1].shrink(1);

                    } else if (!fuel[1].isEmpty() && !fuel[1].isEmpty()) {
                        if (fuel[1].isEmpty())
                            this.items.set(ContainerMoteProcessor.SLOT_FUEL_LEFT, fuel[1].getContainerItem());
                        if (fuel[0].isEmpty())
                            this.items.set(ContainerMoteProcessor.SLOT_FUEL_RIGHT, fuel[0].getContainerItem());
                    }
                }
            }

            world.setBlockState(pos, world.getBlockState(pos).with(BlockMoteProcessor.LIT, isProcessing()), 3);
            if (this.isProcessing() && this.canProcess(recipe)) {
                --this.processingChargeLeft;
                ++this.processingProgress;
                if (this.processingProgress >= this.requiredProcessingProgress) {
                    this.processingProgress = 0;
                    this.requiredProcessingProgress = this.getProcessingTime();
                    this.useRecipe(recipe);
                    tileChanged = true;
                }
            } else {
                --this.processingChargeLeft;
                this.processingProgress = 0;
            }
        } else
            world.setBlockState(pos, world.getBlockState(pos).with(BlockMoteProcessor.LIT, isProcessing()), 3);

        if (wasProcessing != this.isProcessing())
            tileChanged = true;

        if (tileChanged)
            this.markDirty();
    }

    protected int getProcessingTime() {
        if (this.world == null)
            return DEFAULT_PROCESSING_TIME;
        return this.world.getRecipeManager().getRecipe(this.recipeType, this, this.world).map(ProcessingRecipe::getProcessingTime).orElse(DEFAULT_PROCESSING_TIME);
    }

    private void useRecipe(@Nullable IRecipe<?> recipe) {
        if (recipe == null)
            return;

        if (!this.canProcess(recipe))
            return;


        ItemStack inputItem = this.items.get(ContainerMoteProcessor.SLOT_INPUT);
        ItemStack outputItem = this.items.get(ContainerMoteProcessor.SLOT_OUTPUT);
        ItemStack recipeOutput = recipe.getRecipeOutput();

        if (outputItem.isEmpty()) {
            this.items.set(ContainerMoteProcessor.SLOT_OUTPUT , recipeOutput.copy());
        } else if (outputItem.getItem() == recipeOutput.getItem())
            outputItem.grow(recipeOutput.getCount());

        inputItem.shrink(1);
    }

    public static boolean isFuel(ItemStack itemStack) {
        return FUEL.contains(itemStack.getItem()) || FUEL.contains(itemStack.getItem());
    }

    private boolean isProcessing() {
        return this.processingChargeLeft > 0;
    }

    protected boolean canProcess(@Nullable IRecipe<?> recipeIn) {
        if (recipeIn == null)
            return false;

        if (this.items.get(ContainerMoteProcessor.SLOT_INPUT).isEmpty())
            return false;

        ItemStack resultStack = recipeIn.getRecipeOutput();
        if (resultStack.isEmpty())
            return false;

        ItemStack outputStack = this.items.get(ContainerMoteProcessor.SLOT_OUTPUT);
        if (outputStack.isEmpty())
            return true;

        if (!outputStack.isItemEqual(resultStack))
            return false;

        if (outputStack.getCount() + resultStack.getCount() <= this.getInventoryStackLimit() && outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize()) // Forge fix: make furnace respect stack sizes in furnace recipes
            return true;

        return outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == ContainerMoteProcessor.SLOT_OUTPUT)
            return false;

        if (index == ContainerMoteProcessor.SLOT_INPUT)
            return true;

        if (index == ContainerMoteProcessor.SLOT_FUEL_LEFT && stack.getItem() == FUEL.get(0))
            return true;

        return index == ContainerMoteProcessor.SLOT_FUEL_RIGHT && stack.getItem() == FUEL.get(1);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerMoteProcessor(id, playerInventory, this, this. processorData);
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
        this.items.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());

        if (index == 0 && !(!stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack)))
            this.markDirty();
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

    public int[] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? SLOTS_DOWN : (side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN && index == ContainerMoteProcessor.SLOT_OUTPUT;
    }
}