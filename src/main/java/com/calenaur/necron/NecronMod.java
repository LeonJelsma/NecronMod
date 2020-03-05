package com.calenaur.necron;

import com.calenaur.necron.entity.EntityNecronSoldier;
import com.calenaur.necron.entity.type.EntityTypeNecronSoldier;
import com.calenaur.necron.entity.type.EntityTypes;
import com.calenaur.necron.item.ItemNecrodermisAxe;
import com.calenaur.necron.item.ItemNecrodermisHoe;
import com.calenaur.necron.item.ItemJeffPickaxe;
import com.calenaur.necron.item.ItemNecrodermisPickaxe;
import com.calenaur.necron.item.ItemNecrodermisShovel;
import com.calenaur.necron.renderer.RendererNecronSoldier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("necron")
public class NecronMod {
    private static final Logger LOGGER = LogManager.getLogger();

    public NecronMod() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypes.NECRON_SOLDIER, RendererNecronSoldier::new);
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
        @SubscribeEvent
        public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            event.getRegistry().register(new EntityTypeNecronSoldier());
        }
    }
}
