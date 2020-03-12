package com.calenaur.necron.Custom;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class HungryMetalGroupRegistry {

    private volatile static HashSet<HungryMetalGroup> hungryMetalGroups = new HashSet<>();

    public HungryMetalGroupRegistry(){

    }

    public synchronized static void addGroup(BlockPos startingPos, HashSet<Block> targetBlocks, int maxDistance, int delay){
        HungryMetalGroup group = new HungryMetalGroup(targetBlocks, startingPos, maxDistance, delay);
        HashSet<BlockPos> blocks = new HashSet<>();
        blocks.add(startingPos);
        group.setSpanningBlocks(blocks);
        hungryMetalGroups.add(group);
    }

    public synchronized static HashSet<HungryMetalGroup> getHungryMetalGroups(){
        return hungryMetalGroups;
    }

    public synchronized static void mergeHungryMetalGroups(HashSet<HungryMetalGroup> groups){
        HashSet<HungryMetalGroup> mergedGroups = new HashSet<>();
        for (HungryMetalGroup localGroup: hungryMetalGroups){
            if (!localGroup.retrieved){
                mergedGroups.add(localGroup);
            }
        }

        for (HungryMetalGroup handlerGroup: groups){
            mergedGroups.add(handlerGroup);
        }
        hungryMetalGroups = mergedGroups;
    }

    public static void doSpread(World world){
        HashSet<HungryMetalGroup> groups = (HashSet<HungryMetalGroup>) hungryMetalGroups.clone();
        synchronized (groups) {
            for (HungryMetalGroup group : groups) {
                HashSet<BlockPos> changedBlocks = new HashSet<>();
                if (!group.hasSpread && group.retrieved) {
                    for (BlockPos pos : group.getBlocksToSpan()) {
                        if (world.setBlockState(pos, Blocks.HUNGRY_METAL.getDefaultState())) {
                            changedBlocks.add(pos);
                        }
                    }
                    removeOldBlocks(world, group.getSpanningBlocks());
                    group.setSpanningBlocks(changedBlocks);
                    group.hasSpread = true;
                    group.retrieved = false;
                    if (group.getSpanningBlocks().isEmpty()) {
                        HungryMetalGroupRegistry.getHungryMetalGroups().remove(group);
                    }
                }
            }
        }
    }

    public synchronized static void setHungryMetalGroups(HashSet<HungryMetalGroup> groups){
        hungryMetalGroups = groups;
    }

    public static void removeOldBlocks(World world, HashSet<BlockPos> blocks){
        for (BlockPos block: blocks){
            world.setBlockState(block, net.minecraft.block.Blocks.AIR.getDefaultState());
        }
    }
}
