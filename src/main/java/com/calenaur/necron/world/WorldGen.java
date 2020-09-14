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
        biomeIn.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Structures.NECRON_RUIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }

    public static void addNecronRuins(Biome biomeIn) {
        biomeIn.addStructure(Structures.NECRON_RUIN.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    }

    public static void addNecronOres(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreNecrodermis()).withPlacement(OreNecrodermis.CONFIGURED_PLACEMENT));
    }
}