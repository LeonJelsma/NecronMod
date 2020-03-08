package com.calenaur.necron.registry;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.block.BlockNecrodermisOre;
import com.calenaur.necron.block.necron.*;
import com.calenaur.necron.entity.type.EntityTypes;
import com.calenaur.necron.item.*;
import com.calenaur.necron.item.necron.*;
import com.calenaur.necron.renderer.RendererNecronSoldier;
import com.calenaur.necron.world.WorldGen;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuin;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuinPiece;
import com.calenaur.necron.world.gen.feature.structure.Structures;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
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
		event.getRegistry().register(new BlockNecronCorner());
		event.getRegistry().register(new BlockNecronCrescent());
		event.getRegistry().register(new BlockNecronIntersection());
		event.getRegistry().register(new BlockNecronPlain());
		event.getRegistry().register(new BlockNecronStairs());
		event.getRegistry().register(new BlockNecronCircuit());
		event.getRegistry().register(new BlockNecronStraightDouble());
		event.getRegistry().register(new BlockNecronStraightSingle());
		event.getRegistry().register(new BlockNecronStraightCircuit());
		event.getRegistry().register(new BlockNecrodermisOre());
	}

	@SubscribeEvent
	public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityTypes.NECRON_SOLDIER);
	}

	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlockNecronCorner());
		event.getRegistry().register(new ItemBlockNecronCrescent());
		event.getRegistry().register(new ItemBlockNecronIntersection());
		event.getRegistry().register(new ItemBlockNecronPlain());
		event.getRegistry().register(new ItemBlockNecronStairs());
		event.getRegistry().register(new ItemBlockNecronCircuit());
		event.getRegistry().register(new ItemBlockNecronStraightDouble());
		event.getRegistry().register(new ItemBlockNecronStraightCircuit());
		event.getRegistry().register(new ItemBlockNecronStraightSingle());
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

	@SubscribeEvent
	public static void onStructureRegistry(final RegistryEvent.Register<Feature<?>> event) {
		//Using the registry directly like this is bad, however this is currently the only way to register a structure piece
		Structures.NECRON_RUIN_PIECE = Registry.register(Registry.STRUCTURE_PIECE, NecronMod.MOD_ID + ":" + StructureNecronRuinPiece.NAME, StructureNecronRuinPiece::new);
    	event.getRegistry().register(new StructureNecronRuin());
	}
}
