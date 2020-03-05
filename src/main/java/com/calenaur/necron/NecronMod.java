package com.calenaur.necron;

import com.calenaur.necron.item.ItemNecrodermisAxe;
import com.calenaur.necron.item.ItemNecrodermisHoe;
import com.calenaur.necron.item.ItemJeffPickaxe;
import com.calenaur.necron.item.ItemNecrodermisPickaxe;
import com.calenaur.necron.item.ItemNecrodermisShovel;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("necron")
public class NecronMod {
    private static final Logger LOGGER = LogManager.getLogger();

    public NecronMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemJeffPickaxe());
            event.getRegistry().register(new ItemNecrodermisAxe());
            event.getRegistry().register(new ItemNecrodermisHoe());
            event.getRegistry().register(new ItemNecrodermisPickaxe());
            event.getRegistry().register(new ItemNecrodermisShovel());
        }
    }
}
