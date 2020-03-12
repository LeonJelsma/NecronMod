package com.calenaur.necron.event;

import com.calenaur.necron.custom.HungryMetalGroup;
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

    private int tick = 0;


    public EventHungryMetal(){
    }



    @SubscribeEvent
    public void spreadHungryMetalTick(TickEvent.WorldTickEvent event) {

        if (event.phase != TickEvent.Phase.END)
            return;

        if (event.world.isRemote())
            return;

        HungryMetalGroupRegistry.world = event.world;

        tick++;
        if (tick > 4) {
            tick = 0;

            HungryMetalGroupRegistry.doSpread(event.world);
        }
    }

}
