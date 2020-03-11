package com.calenaur.necron.world;

import com.calenaur.necron.world.gen.feature.ore.OreNecrodermis;
import com.calenaur.necron.world.gen.feature.structure.Structures;
import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuin;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

public class WorldGen {
    public static void register() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            addStructures(biome); //Add all structures as a no-place-feature to all biome's to prevent structures from being cutoff when overlapping into a new biome.
            if (StructureNecronRuin.BIOME_WHITELIST.contains(biome.getCategory()))
                addNecronRuins(biome);
            if (OreNecrodermis.BIOME_WHITELIST.contains(biome.getCategory()))
                addNecronOres(biome);
        }
    }

    public static void addStructures(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Structures.NECRON_RUIN.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.NOPE.func_227446_a_(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }

    public static void addNecronRuins(Biome biomeIn) {
        biomeIn.func_226711_a_(Structures.NECRON_RUIN.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addNecronOres(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreNecrodermis()).func_227228_a_(OreNecrodermis.CONFIGURED_PLACEMENT));
    }
}