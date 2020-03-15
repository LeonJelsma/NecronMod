package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.inventory.container.ContainerHungryMetalArranger;
import com.calenaur.necron.inventory.container.ContainerHungryMetalFilter;
import com.calenaur.necron.inventory.container.ContainerRiftSack;
import com.calenaur.necron.item.ItemHungryMetal;
import com.calenaur.necron.item.Items;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
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
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import javax.annotation.Nullable;
import java.util.HashSet;

public class TileEntityHungryMetalArranger extends TileEntity implements IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "hungry_metal_arranger_tile";

    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private boolean preview = false;

    public TileEntityHungryMetalArranger() {
        super(TileEntities.HUNGRY_METAL_MAKER);
    }

    @Override
    public void tick() {
        if (world == null)
            return;

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
                newStack = ItemHungryMetal.configure(stack, getBlockListFromFilter(items.get(1)), items.get(2).getCount(), TileEntityHungryMetal.DEFAULT_SPEED);
                items.set(3, newStack);
                preview = true;
        }
    }

    public void take(){
        items.get(0).shrink(1);
        items.set(2, ItemStack.EMPTY);
        preview = false;
    }

    private boolean canMake() {
        if (!this.items.get(0).isEmpty()) {
            if (this.items.get(1).getItem() != Items.HUNGRY_METAL_FILTER)
                return false;

            if (getBlockListFromFilter(this.items.get(1)).size() <= 0 )
                return true;

            if(this.items.get(0).getItem() == Items.HUNGRY_METAL)
                if (!ItemHungryMetal.isTargetSet(this.items.get(0)))
                    return this.items.get(2).getItem() == Items.NECRON_INGOT;
        }
        return false;
    }



    public HashSet<BlockState> getBlockListFromFilter(ItemStack filter)  {
        HashSet<BlockState> blocks = new HashSet<>();
        Inventory inventory = com.calenaur.necron.inventory.ItemStackHelper.readInventory(filter, ContainerHungryMetalFilter.INVENTORY_SIZE);

        for (int i = 0; i < ContainerRiftSack.INVENTORY_SIZE; i++){
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.getItem() instanceof BlockItem){
                BlockItem blockItem = (BlockItem)  stack.getItem();
                blocks.add(blockItem.getBlock().getDefaultState());
            }

            if (stack.getItem() instanceof BucketItem){
                BucketItem bucketItem = (BucketItem)  stack.getItem();
                blocks.add(bucketItem.getFluid().getDefaultState().getBlockState());
            }
        }
        return blocks;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(LanguageMap.getInstance().translateKey(Blocks.HUNGRY_METAL_ARRANGER.getTranslationKey()));
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

