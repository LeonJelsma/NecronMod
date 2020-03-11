package com.calenaur.necron.tileentity;

import com.calenaur.necron.inventory.container.ContainerHungryMetalArranger;
import com.calenaur.necron.item.ItemHungryMetal;
import com.calenaur.necron.item.Items;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import javax.annotation.Nullable;

public class TileEntityHungryMetalArranger extends TileEntity implements IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "hungry_metal_arranger_tile";

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private boolean preview = false;

    public TileEntityHungryMetalArranger() {
        super(TileEntities.HUNGRY_METAL_MAKER);
    }

    @Override
    public void tick() {
        if(!world.isRemote){
            if(canMake()){
                make();
            } else if (preview){
                items.set(3, ItemStack.EMPTY);
            }
        }
    }

    public void make(){
        if(items.get(3).isEmpty() || preview){
                ItemStack stack = new ItemStack(Items.HUNGRY_METAL);
                ItemStack newStack;
                if (items.get(1).getItem() instanceof BlockItem) {
                    BlockItem targetItem = (BlockItem) items.get(1).getItem();
                    newStack = ItemHungryMetal.configure(stack, targetItem.getBlock().getDefaultState(), items.get(2).getCount(), 40);
                } else {
                    BucketItem targetItem = (BucketItem) items.get(1).getItem();
                    newStack = ItemHungryMetal.configure(stack, targetItem.getFluid().getDefaultState().getBlockState(), items.get(2).getCount(), 40);
                }
                items.set(3, newStack);
                preview = true;
        }
    }

    public void take(){
        items.get(0).shrink(1);
        items.get(1).shrink(1);
        items.set(2, ItemStack.EMPTY);
        preview = false;
    }

    private boolean canMake() {
        if (!this.items.get(0).isEmpty() && this.items.get(0).getItem() != null) {
            if (!(this.items.get(1).getItem() instanceof BlockItem)){
                if (this.items.get(1).getItem() instanceof BucketItem) {
                    BucketItem bucket = (BucketItem) this.items.get(1).getItem();
                    if (bucket.getFluid() == Fluids.EMPTY){
                        return false;
                    }
                } else {
                    return false;
                }
            }
            if(this.items.get(0).getItem() == Items.HUNGRY_METAL) {
                if (!ItemHungryMetal.isTargetSet(this.items.get(0))) {
                    if (this.items.get(2).getItem() == Items.NECRON_INGOT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerHungryMetalArranger(id, playerInventory, this , this);
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
        NonNullList<ItemStack> itemToSave = this.items;
        itemToSave.set(3, ItemStack.EMPTY);
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

