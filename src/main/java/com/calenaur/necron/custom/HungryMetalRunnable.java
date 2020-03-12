package com.calenaur.necron.custom;

import com.calenaur.necron.util.Calculations;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class HungryMetalRunnable implements Runnable {

    private HungryMetalGroup group;
    private World world;



    public HungryMetalRunnable(HungryMetalGroup group){
        this.group = group;
    }

    @Override
    public void run() {
        while (true){
            if (!group.getRetrieved()){
                world = HungryMetalGroupRegistry.world;
                if (world != null) {
                    addBlocksToChange(group);
                    group.setRetrieved(true);
                    group.setHasSpread(false);
                }
            }
        }
    }

    private void addBlocksToChange(HungryMetalGroup group) {

        BlockPos startingPos = group.getStartingPos();
        HashSet<BlockPos> blocksToTarget = new HashSet<>();
        int maxDistance = group.getMaxDistance();

        for (BlockPos pos: group.getSpanningBlocks()) {
            for (int[] neighbour : Calculations.NEIGHBOURS) {
                int x = neighbour[0];
                int y = neighbour[1];
                int z = neighbour[2];
                BlockPos newPos = pos.add(x, y, z);
                //System.out.println(Calculations.GetDistance(newPos, startingPos));
                //System.out.println(maxDistance);
                if (Calculations.GetDistance(newPos, startingPos) < maxDistance) {
                    if (canSpread(group.getTargetBlocks(), newPos)) {
                        if (!blocksToTarget.contains(newPos)) {
                            blocksToTarget.add(newPos);
                        }
                    }
                }
            }
        }
        group.setBlocksToSpan(blocksToTarget);
    }

    private boolean canSpread(HashSet<Block> targets, BlockPos pos){
        if (world != null) {
            BlockState state = world.getBlockState(pos);

            if (state.getBlock() instanceof FlowingFluidBlock) {
                FlowingFluidBlock block = (FlowingFluidBlock) state.getBlock();
                if (block.getFluidState(state).getLevel() == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            if (state.getBlock() != net.minecraft.block.Blocks.AIR.getBlock()) {
                if (targets.contains(state.getBlock())) {
                    return true;
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}
