package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.BlockGreyGoo;
import com.calenaur.necron.inventory.container.ContainerGooMaker;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.calenaur.necron.item.ItemGreyGoo;
import com.calenaur.necron.item.Items;
import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import javax.annotation.Nullable;

public class TileEntityGooMaker extends TileEntity implements IInventory, ITickableTileEntity, INamedContainerProvider {
    public static final String NAME = "goo_maker_tile";

    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);


    public TileEntityGooMaker() {
        super(TileEntities.GOO_MAKER);
    }


    private boolean isItemBlock(Item item){
        return item instanceof BlockItem;
    }


    @Override
    public void tick() {
        if(!world.isRemote){
            if(canMake(items.get(0).getItem())){
                if(items.get(1).getItem() == Items.NECRON_MOTE){
                    make();
                }
            }
        }
    }

    private void make(){
        items.get(0).shrink(1);
        items.get(1).shrink(1);

        if(items.get(2).isEmpty()){
            ItemStack stack = new ItemStack(Items.GREY_GOO);
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.put("target", StringNBT.func_229705_a_(items.get(0).getItem().getRegistryName().toString()));
            stack.setTag(compoundnbt);
        }else{
            items.get(2).grow(1);
        }
    }

    private boolean canMake(@Nullable Item itemIn) {
        if (!this.items.get(0).isEmpty() && itemIn != null) {
            if (!isItemBlock(itemIn)) {
                return false;
            } else {
                BlockItem itemInput = (BlockItem) itemIn;
                Block inputBlock = itemInput.getBlock();

                BlockItem itemOutput = (BlockItem) this.items.get(2).getItem();

                ItemGreyGoo gooOutput;
                if (itemOutput instanceof ItemGreyGoo){
                    gooOutput = (ItemGreyGoo) itemOutput;
                } else{
                    return false;
                }
                if (this.items.get(2).isEmpty()) {
                    return true;
                }
                StringNBT stringNBT = (StringNBT) this.items.get(2).getStack().getTag().get("target");
                if (stringNBT.getString() != inputBlock.getBlock().getRegistryType().toString()) {
                    return false;
                } else if (this.items.get(2).getStack().getCount() + 1 <= this.getInventoryStackLimit() && this.items.get(3).getCount() + 1 <= this.items.get(3).getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
                    return true;
                } else {
                    return this.items.get(2).getCount() + 1 <= this.items.get(3).getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
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
        return new ContainerGooMaker(id, playerInventory, this );
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

