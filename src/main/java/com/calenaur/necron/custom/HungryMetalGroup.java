package com.calenaur.necron.custom;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;

public class HungryMetalGroup {

    private HashSet<BlockPos> blocksToSpan = new HashSet<>();
    private HashSet<BlockPos> spanningBlocks = new HashSet<>();
    private HashSet<Block> targetBlocks;
    private BlockPos startingPos;
    private boolean configured = false;
    public boolean hasSpread = true;
    public boolean retrieved = false;
    private int maxDistance = 0;
    private int delay = 5;
    private int timer = 0;


    public HungryMetalGroup(HashSet<Block> targetBlocks, BlockPos startingPos, int maxDistance, int delay){
        this.targetBlocks = targetBlocks;
        this.startingPos = startingPos;
        this.maxDistance = maxDistance;
        this.delay = delay;
        //addSpanningBlock(world, startingPos);
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public HashSet<Block> getTargetBlocks() {
        return targetBlocks;
    }

    public BlockPos getStartingPos(){
        return startingPos;
    }

    public void setBlocksToSpan(HashSet<BlockPos> blocks){
        blocksToSpan = blocks;
    }

    public HashSet<BlockPos> getBlocksToSpan(){
        return blocksToSpan;
    }

    public void setSpanningBlocks(HashSet<BlockPos> blocks){
        spanningBlocks = blocks;
    }

    public HashSet<BlockPos> getSpanningBlocks(){
        return spanningBlocks;
    }
}
