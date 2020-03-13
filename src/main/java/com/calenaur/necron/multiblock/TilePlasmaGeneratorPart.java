package com.calenaur.necron.multiblock;


import com.calenaur.necron.tileentity.TileEntities;
import net.minecraft.tileentity.TileEntity;


public class TilePlasmaGeneratorPart extends TileEntity {

    public TilePlasmaGeneratorPart() {
        super(TileEntities.PLASMA_GENERATOR_PART);
    }

    public static String NAME = "plasma_generator_tile";

    // @todo 1.13
//    @Override
//    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
//        return oldState.getBlock() != newState.getBlock();
//    }


}
