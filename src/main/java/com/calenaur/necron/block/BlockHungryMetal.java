package com.calenaur.necron.block;

import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import com.calenaur.necron.tileentity.TileEntityHungryMetalArranger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockHungryMetal extends Block {

    public BlockHungryMetal() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("hungry_metal");
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return false;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (worldIn.getTileEntity(pos) instanceof TileEntityHungryMetal){
            TileEntityHungryMetal hungryMetal = (TileEntityHungryMetal) worldIn.getTileEntity(pos);
            if (hungryMetal.getActivated()){
                stack = ItemStack.EMPTY;
            }
        }
        super.harvestBlock(worldIn, player, pos, state, te, stack);
    }
}