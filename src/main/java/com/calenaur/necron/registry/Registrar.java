package com.calenaur.necron.registry;

import com.calenaur.necron.NecronMod;
import com.calenaur.necron.block.*;
import com.calenaur.necron.block.necron.*;
import com.calenaur.necron.entity.type.EntityTypes;
import com.calenaur.necron.gui.ScreenHungryMetalArranger;
import com.calenaur.necron.gui.ScreenHungryMetalFilter;
import com.calenaur.necron.gui.ScreenMoteProcessor;
import com.calenaur.necron.inventory.container.ContainerHungryMetalArranger;
import com.calenaur.necron.gui.ScreenRiftSack;
import com.calenaur.necron.inventory.container.ContainerHungryMetalFilter;
import com.calenaur.necron.inventory.container.ContainerMoteProcessor;
import com.calenaur.necron.inventory.container.ContainerRiftSack;
import com.calenaur.necron.inventory.container.ContainerTypes;
import com.calenaur.necron.item.*;
import com.calenaur.necron.particles.ParticleMoteProcessorFlame;
import com.calenaur.necron.particles.ParticleTypeMoteProcessorFlame;
import com.calenaur.necron.particles.ParticleTypes;
import com.calenaur.necron.recipe.RecipeSerializerTypes;
import com.calenaur.necron.item.necron.*;
import com.calenaur.necron.renderer.RendererNecronExplosive;
import com.calenaur.necron.renderer.RendererNecronSoldier;
import com.calenaur.necron.sound.SoundMoteProcessorLit;
import com.calenaur.necron.tileentity.TileEntityHungryMetalArranger;
import com.calenaur.necron.tileentity.TileEntityHungryMetal;
import com.calenaur.necron.sound.SoundRiftSackOpen;
import com.calenaur.necron.tileentity.TileEntityMoteProcessor;
import com.calenaur.necron.world.WorldGen;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuin;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuinPiece;
import com.calenaur.necron.world.gen.feature.structure.Structures;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
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
		RenderingRegistry.registerEntityRenderingHandler(EntityTypes.NECRON_EXPLOSIVE, RendererNecronExplosive::new);
    	ScreenManager.registerFactory(ContainerTypes.MOTE_PROCESSOR, ScreenMoteProcessor::new);
    	ScreenManager.registerFactory(ContainerTypes.HUNGRY_METAL_ARRANGER, ScreenHungryMetalArranger::new);
		ScreenManager.registerFactory(ContainerTypes.RIFT_SACK, ScreenRiftSack::new);
		ScreenManager.registerFactory(ContainerTypes.HUNGRY_METAL_FILTER, ScreenHungryMetalFilter::new);
	}

	public static void commonSetup(final FMLCommonSetupEvent event) {
		WorldGen.register();
	}

	@SubscribeEvent
	public static void onBlockRegistry(final RegistryEvent.Register<Block> event){
		event.getRegistry().register(new BlockMoteProcessor());
		event.getRegistry().register(new BlockHungryMetalArranger());
		event.getRegistry().register(new BlockHungryMetal());
		event.getRegistry().register(new BlockWoodSelection());
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
		event.getRegistry().register(new BlockStoneSelection());
		event.getRegistry().register(new BlockNecronExplosive());
	}

	@SubscribeEvent
	public static void onEntityTypeRegistry(final RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(EntityTypes.NECRON_SOLDIER);
		event.getRegistry().register(EntityTypes.NECRON_EXPLOSIVE);
	}

	@SubscribeEvent
	public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityMoteProcessor::new, Blocks.MOTE_PROCESSOR).build(null).setRegistryName(TileEntityMoteProcessor.NAME));
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityHungryMetalArranger::new, Blocks.HUNGRY_METAL_ARRANGER).build(null).setRegistryName(TileEntityHungryMetalArranger.NAME));
		event.getRegistry().register(TileEntityType.Builder.create(TileEntityHungryMetal::new, Blocks.HUNGRY_METAL).build(null).setRegistryName(TileEntityHungryMetal.NAME));
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
		event.getRegistry().register(new ItemMoteProcessor());
		event.getRegistry().register(new ItemHungryMetalArranger());
		event.getRegistry().register(new ItemHungryMetal());
		event.getRegistry().register(new ItemNecrodermisAxe());
		event.getRegistry().register(new ItemNecrodermisHoe());
		event.getRegistry().register(new ItemNecrodermisPickaxe());
		event.getRegistry().register(new ItemNecrodermisShovel());
		event.getRegistry().register(new ItemNecrodermisIngot());
		event.getRegistry().register(new ItemNecrodermisOre());
		event.getRegistry().register(new ItemNecrodermisMote());
		event.getRegistry().register(new ItemNecronSoldierSpawnEgg());
		event.getRegistry().register(new ItemRiftSack());
		event.getRegistry().register(new ItemHungryMetalFilter());
		event.getRegistry().register(new ItemNecronExplosive());
	}

	@SubscribeEvent
	public static void onRecipeSerializerRegistry(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().register(RecipeSerializerTypes.PROCESSING);
	}
	

	@SubscribeEvent
	public static void onContainerTypeRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(new ContainerType<>(ContainerMoteProcessor::new).setRegistryName(ContainerMoteProcessor.NAME));
		event.getRegistry().register(new ContainerType<>(ContainerHungryMetalArranger::new).setRegistryName(ContainerHungryMetalArranger.NAME));
		event.getRegistry().register(new ContainerType<>(ContainerRiftSack::new).setRegistryName(ContainerRiftSack.NAME));
		event.getRegistry().register(new ContainerType<>(ContainerHungryMetalFilter::new).setRegistryName(ContainerHungryMetalFilter.NAME));
	}

	@SubscribeEvent
	public static void onStructureRegistry(final RegistryEvent.Register<Feature<?>> event) {
		//Using the registry directly like this is bad, however this is currently the only way to register a structure piece
		Structures.NECRON_RUIN_PIECE = Registry.register(Registry.STRUCTURE_PIECE, NecronMod.namespace(StructureNecronRuinPiece.NAME), StructureNecronRuinPiece::new);
    	event.getRegistry().register(new StructureNecronRuin());
	}

	@SubscribeEvent
	public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> event){
		event.getRegistry().register(new SoundMoteProcessorLit());
		event.getRegistry().register(new SoundRiftSackOpen());
	}

	@SubscribeEvent
	public static void onParticleTypeRegistry(final RegistryEvent.Register<ParticleType<?>> event){
		event.getRegistry().register(new ParticleTypeMoteProcessorFlame());
	}

	@SubscribeEvent
	public static void onParticleRegistry(final ParticleFactoryRegisterEvent event){
		Minecraft.getInstance().particles.registerFactory(ParticleTypes.MOTE_PROCESSOR_FLAME, ParticleMoteProcessorFlame.Factory::new);
	}
}