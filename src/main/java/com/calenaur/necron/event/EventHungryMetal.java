package com.calenaur.necron.event;

import com.calenaur.necron.Custom.HungryMetalGroup;
import com.calenaur.necron.Custom.HungryMetalGroupRegistry;
import com.calenaur.necron.Custom.HungryMetalHandler;
import com.calenaur.necron.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod.EventBusSubscriber
public class EventHungryMetal {

    private static int TICKS_PER_SECOND = 20;
    private int tick = 0;
    private static HashSet<BlockPos> blocksToChange = new HashSet<>();
    private static boolean haveBlocksBeenChanged = false;
    private World world;
    ExecutorService executor = Executors.newFixedThreadPool(2);
    HungryMetalHandler hungryMetalHandler = new HungryMetalHandler();

    public EventHungryMetal(){
        executor.execute(hungryMetalHandler);
    }



    @SubscribeEvent
    public void spreadHungryMetalTick(TickEvent.WorldTickEvent event) {

        if (event.phase != TickEvent.Phase.END)
            return;

        if (event.world.isRemote())
            return;

        tick++;
        if (tick > 20){
            tick = 0;
            hungryMetalHandler.setWorld(event.world);
            for (HungryMetalGroup group: HungryMetalGroupRegistry.getHungryMetalGroups()){
                HashSet<BlockPos> changedBlocks = new HashSet<>();
                for (BlockPos pos: group.getBlocksToSpan()){
                    if (world.setBlockState(pos, Blocks.HUNGRY_METAL.getDefaultState())){
                        changedBlocks.add(pos);
                    }
                }
            }
        }
    }

    public void retrieveBlocksToChange(){
        //World test = SerializationUtils.clone(world.getChunk(1,1, ChunkStatus.FULL));
        HungryMetalGroupRegistry.getHungryMetalGroups();
    }

    public static void exchangeBlockStatus(HashSet<BlockPos> blocks){
        blocksToChange = blocks;
    }

}
