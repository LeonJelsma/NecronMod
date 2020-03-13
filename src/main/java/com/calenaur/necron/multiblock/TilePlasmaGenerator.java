package com.calenaur.necron.multiblock;

import com.calenaur.necron.tileentity.TileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TilePlasmaGenerator extends TileEntity implements INamedContainerProvider {

    public TilePlasmaGenerator() {
        super(TileEntities.PLASMA_GENERATOR);
    }

    public static String NAME = "plasma_generator_tile";

    // @todo 1.13
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
//        return oldState.getBlock() != newState.getBlock();
//    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        readRestorableFromNBT(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        writeRestorableToNBT(compound);
        return super.write(compound);
    }

    public void readRestorableFromNBT(CompoundNBT compound) {
        if (compound.contains("items")) {
            itemHandler.deserializeNBT((CompoundNBT) compound.get("items"));
        }
    }

    public void writeRestorableToNBT(CompoundNBT compound) {
        compound.put("items", itemHandler.serializeNBT());
    }

    public boolean canInteractWith(PlayerEntity playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isRemoved() && playerIn.getDistanceSq(0.5D, 0.5D, 0.5D) <= 64D;
    }


    @Nullable
    @Override
       public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ContainerPlasmaGenerator(id, playerInventory, this);
    }

    private ItemStackHandler itemHandler = new ItemStackHandler(3*9) {

        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TilePlasmaGenerator.this.markDirty();
        }
    };


    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }


}
