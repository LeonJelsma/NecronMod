package com.calenaur.necron.Custom;

import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import net.minecraft.block.Block;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class HungryMetalGroupRegistry {

    private static HashSet<HungryMetalGroup> hungryMetalGroups = new HashSet<>();

    public HungryMetalGroupRegistry(){

    }

    public static void addGroup(BlockPos startingPos, HashSet<Block> targetBlocks, int maxDistance, int delay){
        HungryMetalGroup group = new HungryMetalGroup(targetBlocks, startingPos, maxDistance, delay);
        HashSet<BlockPos> blocks = new HashSet<BlockPos>();
        blocks.add(startingPos);
        group.setSpanningBlocks(blocks);
        hungryMetalGroups.add(group);
    }

    public static HashSet<HungryMetalGroup> getHungryMetalGroups(){
        return hungryMetalGroups;
    }

    public synchronized static void setHungryMetalGroups(HashSet<HungryMetalGroup> groups){
        hungryMetalGroups = groups;
    }
}
