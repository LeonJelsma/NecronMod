package com.calenaur.necron.block;

import com.calenaur.necron.tileentity.TileEntityGreyGoo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGreyGoo extends Block {

    public static final IProperty<Boolean> alive = BooleanProperty.create("alive");
    public static final IProperty<Integer> spread = IntegerProperty.create("charges", 0, 100);

    public BlockGreyGoo() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("grey_goo");
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


    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.fillStateContainer(stateBuilder);
        stateBuilder.add(spread);
        stateBuilder.add(alive);
    }
}