package com.calenaur.necron.custom;

import com.calenaur.necron.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HungryMetalGroupRegistry {


    private static Set<HungryMetalGroup> hungryMetalGroups = ConcurrentHashMap.newKeySet();
    public static ExecutorService executor = Executors.newFixedThreadPool(100);
    public static HashSet<HungryMetalRunnable> runnables = new HashSet<>();
    public static World world;

    public HungryMetalGroupRegistry(){

    }

    public synchronized static void addGroup(BlockPos startingPos, HashSet<Block> targetBlocks, int maxDistance, int delay){
        HungryMetalGroup group = new HungryMetalGroup(targetBlocks, startingPos, maxDistance, delay);
        HashSet<BlockPos> blocks = new HashSet<>();
        blocks.add(startingPos);
        group.setSpanningBlocks(blocks);

        synchronized (hungryMetalGroups) {
            hungryMetalGroups.add(group);
        }
        HungryMetalRunnable runnable = new HungryMetalRunnable(group);
        runnables.add(runnable) ;
        executor.execute(runnable);
    }

    public synchronized static Set<HungryMetalGroup> getHungryMetalGroups(){
        return hungryMetalGroups;
    }

    public synchronized static void mergeHungryMetalGroups(HashSet<HungryMetalGroup> groups){
        HashSet<HungryMetalGroup> mergedGroups = new HashSet<>();
        for (HungryMetalGroup localGroup: hungryMetalGroups){
            if (!localGroup.getRetrieved()){
                mergedGroups.add(localGroup);
            }
        }

        for (HungryMetalGroup handlerGroup: groups){
            mergedGroups.add(handlerGroup);
        }
        hungryMetalGroups = mergedGroups;
    }

    public synchronized static void doSpread(World world){
        synchronized (hungryMetalGroups) {
            for (HungryMetalGroup group : hungryMetalGroups) {
                HashSet<BlockPos> changedBlocks = new HashSet<>();
                if (!group.hasSpread && group.retrieved) {
                    for (BlockPos pos : group.getBlocksToSpan()) {
                        if (world.setBlockState(pos, Blocks.HUNGRY_METAL.getDefaultState())) {
                            changedBlocks.add(pos);
                        }
                    }
                    removeOldBlocks(world, group.getSpanningBlocks());
                    group.setSpanningBlocks(changedBlocks);
                    if (changedBlocks.isEmpty()) {
                        HungryMetalGroupRegistry.getHungryMetalGroups().remove(group);
                    }
                    group.setHasSpread(true);
                    group.setRetrieved(false);
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
