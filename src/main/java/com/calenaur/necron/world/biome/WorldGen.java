package com.calenaur.necron.world.biome;

import com.calenaur.necron.world.gen.ore.OreNecrodermis;
import com.calenaur.necron.world.gen.structure.Structures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGen {
    public static void register() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            System.out.println("NecronMod: Registering Generation -> " + biome.getRegistryName());
            addStructures(biome); //Add all structures as a no-place-feature to all biome's to prevent structures from being cutoff when overlapping into a new biome.
            switch (biome.getCategory()) {
                case NONE:
                case THEEND:
                case NETHER:
                    break;
                default:
                    addOres(biome);
                    addNecronStructures(biome);
                    break;
            }
        }
    }

    public static void addOres(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreNecrodermis()).func_227228_a_(OreNecrodermis.CONFIGURED_PLACEMENT));
    }

    public static void addStructures(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Structures.NECRON_RUIN.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }

    public static void addNecronStructures(Biome biomeIn) {
        biomeIn.func_226711_a_(Structures.NECRON_RUIN.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
    }
}