package com.calenaur.necron.block;

import com.calenaur.necron.tileentity.TileEntityGreyGoo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGreyGoo extends Block {

    public BlockGreyGoo() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("necrodermis_ore");
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityGreyGoo();
    }


}