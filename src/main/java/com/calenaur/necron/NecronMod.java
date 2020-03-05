package com.calenaur.necron;

import com.calenaur.necron.blocks.BlockNecrodermisOre;
import com.calenaur.necron.blocks.ModBlocks;
import com.calenaur.necron.item.*;
import com.calenaur.necron.misc.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("necron")
public class NecronMod {

    public static final ItemGroup necronItems = new ItemGroup("necrontab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.NECRON_ORE);
        }
    };

    private static final Logger LOGGER = LogManager.getLogger();

    public NecronMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<Block> event){
            event.getRegistry().register(new BlockNecrodermisOre());
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemNecrodermisAxe());
            event.getRegistry().register(new ItemNecrodermisHoe());
            event.getRegistry().register(new ItemNecrodermisPickaxe());
            event.getRegistry().register(new ItemNecrodermisShovel());
            event.getRegistry().register(new ItemNecrodermisIngot());
            event.getRegistry().register(new BlockItem(ModBlocks.NECRON_ORE, new Item.Properties().group(necronItems)).setRegistryName("necrodermis_ore"));

        }

        @SubscribeEvent
        public static void onBiomeGeneration(final RegistryEvent.Register<Biome> event){
            OreGeneration.setupOreGen();
        }

    }
}
