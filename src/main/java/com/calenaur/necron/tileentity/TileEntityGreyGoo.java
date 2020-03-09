package com.calenaur.necron.tileentity;

import com.calenaur.necron.block.BlockGreyGoo;
import com.calenaur.necron.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class TileEntityGreyGoo extends TileEntity implements ITickableTileEntity {



    private static final int[][] NEIGHBOURS = {
            {-1, 0, 0},
            {1, 0, 0},

            {0,-1, 0},
            {0, 1, 0},

            {0, 0, -1},
            {0, 0, 1},
    };

    public static final String NAME = "grey_goo_tile";

    private int timer = 0;
    private int spreadRemaining;

    public TileEntityGreyGoo() {
        super(TileEntities.GREY_GOO);
    }

    @Override
    public void tick() {
        this.spreadRemaining = getBlockState().get(BlockGreyGoo.spread) - 1;
        BlockPos pos = getPos();
        IWorld world = getWorld();
        BlockState state = getBlockState();
        if (world == null)
            return;

        if (world.isRemote())
            return;

        TileEntity tileEntity =  world.getTileEntity(pos);
        if (tileEntity == null)
            return;

        if (!state.get(BlockGreyGoo.alive)) {
            tileEntity.remove();
            world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState(), 0);
            return;
        }
        if (timer > 100) {
            spread(world, pos);
            timer = 0;
        }
        timer++;
    }

    private void spread(IWorld world, BlockPos pos) {
        for (int[] neighbour : NEIGHBOURS){
            int x = neighbour[0];
            int y = neighbour[1];
            int z = neighbour[2];
            BlockPos newPos = pos.add(x, y, z);
            if (world.getBlockState(newPos).isSolid() && !(world.getBlockState(newPos).getBlock() instanceof BlockGreyGoo)) {
                world.setBlockState(newPos, Blocks.GREY_GOO.getDefaultState(), 1);
            }
        }
        world.setBlockState(pos, Blocks.GREY_GOO.getDefaultState().with(BlockGreyGoo.alive, false).with(BlockGreyGoo.spread, spreadRemaining), 1);
    }
}
