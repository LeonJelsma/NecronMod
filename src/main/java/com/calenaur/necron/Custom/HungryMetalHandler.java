package com.calenaur.necron.Custom;

import com.calenaur.necron.util.Calculations;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.HashSet;

public class HungryMetalHandler implements Runnable {


    private HashMap<HungryMetalGroup, HashSet<BlockPos>> blocksToSpreadTo = new HashMap<>();
    private World world;

    @Override
    public void run() {
        while(true){
            HashSet<HungryMetalGroup> hungryGroups = HungryMetalGroupRegistry.getHungryMetalGroups();
            for (HungryMetalGroup group: hungryGroups){
                synchronized (group) {
                    addBlocksToChange(group);
                }
            }
        }
    }

   private BlockPos[] getBlockPositionsToChange(HungryMetalGroup group){
        return null;
   }

   public void setWorld(World world){
        this.world = world;
   }

    private boolean canSpread(HashSet<Block> targets, BlockPos pos){
        BlockState state = world.getBlockState(pos);

        if (state.getBlock() != net.minecraft.block.Blocks.AIR.getBlock()){
            if (targets.contains(state.getBlock())){
                return true;
            }
        }
        return false;
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
                if (Calculations.GetDistance(newPos, startingPos) > maxDistance) {
                    if (canSpread(group.getTargetBlocks(), newPos)) {
                        if (blocksToTarget.contains(newPos)) {
                            blocksToTarget.add(newPos);
                        }
                    }
                }
            }
        }
        group.setBlocksToSpan(blocksToTarget);
    }

    public HashMap<HungryMetalGroup, HashSet<BlockPos>> getBlocksToSpreadTo(){
        synchronized (blocksToSpreadTo){
            return blocksToSpreadTo;
        }
    }
}
