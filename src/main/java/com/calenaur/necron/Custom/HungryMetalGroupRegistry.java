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

    public synchronized static void addGroup(BlockPos startingPos, HashSet<Block> targetBlocks, int maxDistance, int delay){
        HungryMetalGroup group = new HungryMetalGroup(targetBlocks, startingPos, maxDistance, delay);
        HashSet<BlockPos> blocks = new HashSet<BlockPos>();
        blocks.add(startingPos);
        group.setSpanningBlocks(blocks);
        hungryMetalGroups.add(group);
    }

    public synchronized static HashSet<HungryMetalGroup> getHungryMetalGroups(){
        for (HungryMetalGroup group: hungryMetalGroups) {
            group.retrieved = true;
        }
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
        hungryMetalGroups = groups;
    }
}
