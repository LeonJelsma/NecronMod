package com.calenaur.necron.tileentity;

import com.calenaur.necron.inventory.container.MoteProcessorContainer;
import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Blocks;
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


public class TileEntityMoteProcessor extends TileEntity implements IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "mote_processor_tile";
    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int burnTime = 200;
    private int recipesUsed;
    private int cookTime;
    private int cookTimeTotal;
    private IRecipeType recipeType = RecipeTypes.MOTE_PROCESSING;
    private IRecipe recipeUsed;
    private int recipeUsedSize;

    public TileEntityMoteProcessor() {
        super(TileEntities.MOTE_PROCESSOR);
    }

    @Override
    public void tick() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }
        if (!this.world.isRemote) {
            ItemStack primaryFuel = this.items.get(1);
            ItemStack secondaryFuel = this.items.get(2);
            if (this.isBurning() || (!primaryFuel.isEmpty() && !this.items.get(0).isEmpty()) && !secondaryFuel.isEmpty()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<ProcessingRecipe>)this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    //this.burnTime = this.getBurnTime(primaryFuel);
                    this.recipesUsed = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (primaryFuel.hasContainerItem())
                            this.items.set(1, primaryFuel.getContainerItem());
                        else
                        if (!primaryFuel.isEmpty()) {
                            Item item = primaryFuel.getItem();
                            primaryFuel.shrink(1);
                            secondaryFuel.shrink(1);
                            if (primaryFuel.isEmpty()) {
                                this.items.set(1, primaryFuel.getContainerItem());
                            }
                            if (secondaryFuel.isEmpty()) {
                                this.items.set(2, secondaryFuel.getContainerItem());
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.func_214007_c(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                //this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    protected int getCookTime() {
        return this.world.getRecipeManager().getRecipe((IRecipeType<ProcessingRecipe>)this.recipeType, this, this.world).map(ProcessingRecipe::getProcessingTime).orElse(200);
    }

    private void func_214007_c(@Nullable IRecipe<?> recipe) {
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.items.get(3);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {


            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

    public static boolean isFuel(ItemStack itemStack) {
        return itemStack.getItem() == Items.LAVA_BUCKET || itemStack.getItem() == Items.BLAZE_POWDER;
    }

    protected int getBurnTime(ItemStack fuelStack) {
        if (fuelStack.isEmpty()) {
            return 0;
        } else {
            Item item = fuelStack.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(fuelStack);
        }
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
        if (!this.items.get(0).isEmpty() && recipeIn != null) {
            ItemStack itemstack = recipeIn.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
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
        return new MoteProcessorContainer(id, playerInventory, this);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
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
