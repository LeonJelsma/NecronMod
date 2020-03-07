package com.calenaur.necron.registry;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.block.BlockMoteProcessor;
import com.calenaur.necron.block.BlockNecrodermisOre;
import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.entity.type.EntityTypes;
import com.calenaur.necron.gui.ProcessorScreen;
import com.calenaur.necron.inventory.container.ContainerTypes;
import com.calenaur.necron.inventory.container.MoteProcessorContainer;
import com.calenaur.necron.item.*;
import com.calenaur.necron.recipe.ProcessingRecipe;
import com.calenaur.necron.recipe.RecipeTypes;
import com.calenaur.necron.renderer.RendererNecronSoldier;
import com.calenaur.necron.tileentity.TileEntityMoteProcessor;
import com.calenaur.necron.world.WorldGen;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuin;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuinPiece;
import com.calenaur.necron.world.gen.feature.structure.Structures;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
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
    	ScreenManager.registerFactory(ContainerTypes.MOTE_PROCESSOR, ProcessorScreen::new);
	}

	public static void commonSetup(final FMLCommonSetupEvent event) {
			WorldGen.register();
	}

	@SubscribeEvent
	public static void onBlockRegistry(final RegistryEvent.Register<Block> event){
		event.getRegistry().register(new BlockMoteProcessor());
		event.getRegistry().register(new BlockNecrodermisOre());
	}

	@SubscribeEvent
	public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityTypes.NECRON_SOLDIER);
	}

	@SubscribeEvent
	public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityMoteProcessor::new, Blocks.MOTE_PROCESSOR).build(null).setRegistryName(TileEntityMoteProcessor.NAME));
	}

	@SubscribeEvent
	public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemJeffPickaxe());
		event.getRegistry().register(new ItemMoteProcessor());
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
	public static void onRecipeRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
		//event.getRegistry().register(new CookingRecipeSerializer(ProcessingRecipe::new, 1));
		RecipeTypes.MOTE_PROCESSING = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(NecronMod.namespace(ProcessingRecipe.NAME)), new IRecipeType<ProcessingRecipe>() {
			public String toString() {
				return ProcessingRecipe.NAME;
			}
		});
	}

	@SubscribeEvent
	public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(new ContainerType<>(MoteProcessorContainer::new).setRegistryName(MoteProcessorContainer.NAME));
	}

	@SubscribeEvent
	public static void onStructureRegistry(final RegistryEvent.Register<Feature<?>> event) {
		//Using the registry directly like this is bad, however this is currently the only way to register a structure piece
		Structures.NECRON_RUIN_PIECE = Registry.register(Registry.STRUCTURE_PIECE, NecronMod.namespace(StructureNecronRuinPiece.NAME), StructureNecronRuinPiece::new);
    	event.getRegistry().register(new StructureNecronRuin());
	}
}
