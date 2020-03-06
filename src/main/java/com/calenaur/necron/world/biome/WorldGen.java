package com.calenaur.necron.world.biome;

import com.calenaur.necron.world.gen.ore.OreNecrodermis;
import com.calenaur.necron.world.gen.structure.StructureNecronRuin;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGen {
    public static void register() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues())
            switch (biome.getCategory()) {
                case NONE:
                case THEEND:
                case NETHER:
                    break;
                default:
                    System.out.println("TEST123: " + biome.getCategory());
                    addOres(biome);
                    addStructures(biome);
                    break;
            }
    }

    public static void addOres(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreNecrodermis()).func_227228_a_(OreNecrodermis.CONFIGURED_PLACEMENT));
    }

    public static void addStructures(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, new ConfiguredFeature(new StructureNecronRuin(), NoFeatureConfig.NO_FEATURE_CONFIG));
        biomeIn.func_226711_a_(new ConfiguredFeature(new StructureNecronRuin(), NoFeatureConfig.NO_FEATURE_CONFIG));
    }
}