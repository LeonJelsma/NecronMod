package com.calenaur.necron.tileentity;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class TileEntityGreyGoo extends TileEntity implements ITickableTileEntity {

    private int spreadRemaining;

    public TileEntityGreyGoo() {
        super(TileEntities.GREY_GOO);
        this.spreadRemaining = spreadRemaining;
    }

    @Override
    public void tick() {
        world.getTileEntity().
    }


}
