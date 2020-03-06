package com.calenaur.necron.registry;

import com.calenaur.necron.block.BlockNecrodermisOre;
import com.calenaur.necron.entity.type.EntityTypes;
import com.calenaur.necron.item.*;
import com.calenaur.necron.renderer.RendererNecronSoldier;
import com.calenaur.necron.world.biome.WorldGen;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registrar {
	public static void clientSetup(final FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityTypes.NECRON_SOLDIER, RendererNecronSoldier::new);
	}

	public static void commonSetup(final FMLCommonSetupEvent event) {
			WorldGen.register();
	}

	@SubscribeEvent
	public static void onBlockRegistry(final RegistryEvent.Register<Block> event){
		event.getRegistry().register(new BlockNecrodermisOre());
	}

	@SubscribeEvent
	public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityTypes.NECRON_SOLDIER);
	}

	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemJeffPickaxe());
		event.getRegistry().register(new ItemNecrodermisAxe());
		event.getRegistry().register(new ItemNecrodermisHoe());
		event.getRegistry().register(new ItemNecrodermisPickaxe());
		event.getRegistry().register(new ItemNecrodermisShovel());
		event.getRegistry().register(new ItemNecrodermisIngot());
		event.getRegistry().register(new ItemNecrodermisOre());
		event.getRegistry().register(new ItemNecrodermisMote());
		event.getRegistry().register(new ItemNecronSoldierSpawnEgg());
	}
}
