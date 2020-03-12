package com.calenaur.necron.event;

import com.calenaur.necron.custom.HungryMetalGroupRegistry;
import com.calenaur.necron.custom.HungryMetalHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod.EventBusSubscriber
public class EventHungryMetal {

    private static int TICKS_PER_SECOND = 20;
    private int tick = 0;
    private static HashSet<BlockPos> blocksToChange = new HashSet<>();
    private static boolean haveBlocksBeenChanged = false;
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
        if (tick > 4) {
            tick = 0;

            HungryMetalGroupRegistry.doSpread(event.world);
            hungryMetalHandler.setWorld(event.world);
            /*
            hungryMetalHandler.setWorld(event.world);
            HashSet<HungryMetalGroup> groups = HungryMetalGroupRegistry.getHungryMetalGroups();
            synchronized (groups) {
                for (HungryMetalGroup group : groups) {
                    HashSet<BlockPos> changedBlocks = new HashSet<>();
                    if (!group.hasSpread && group.retrieved) {
                        for (BlockPos pos : group.getBlocksToSpan()) {
                            if (event.world.setBlockState(pos, Blocks.HUNGRY_METAL.getDefaultState())) {
                                changedBlocks.add(pos);
                            }
                        }
                        removeOldBlocks(event.world, group.getSpanningBlocks());
                        group.setSpanningBlocks(changedBlocks);
                        group.hasSpread = true;
                        group.retrieved = false;
                        if (group.getSpanningBlocks().isEmpty()) {
                            HungryMetalGroupRegistry.getHungryMetalGroups().remove(group);
                        }
                    }
                }
            }

             */
        }
    }

    public void retrieveBlocksToChange(){
        //World test = SerializationUtils.clone(world.getChunk(1,1, ChunkStatus.FULL));
        HungryMetalGroupRegistry.getHungryMetalGroups();
    }

    public static void exchangeBlockStatus(HashSet<BlockPos> blocks){
        blocksToChange = blocks;
    }

    public void removeOldBlocks(World world, HashSet<BlockPos> blocks){
        for (BlockPos block: blocks){
            world.setBlockState(block, net.minecraft.block.Blocks.AIR.getDefaultState());
        }
    }
}
