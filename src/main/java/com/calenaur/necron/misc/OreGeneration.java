package com.calenaur.necron.misc;

import com.calenaur.necron.block.Blocks;
import com.calenaur.necron.world.structure.Structures;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OreGeneration {
    private static final int NECRODERMIS_VEIN_SIZE = 7;

    public static void setupOreGen() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.NETHER)
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                        Feature.ORE.func_225566_b_(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                                Blocks.NECRON_ORE.getDefaultState(), NECRODERMIS_VEIN_SIZE))
                                .func_227228_a_(Placement.COUNT_DEPTH_AVERAGE.func_227446_a_(new DepthAverageConfig(10, 60, 16)))
                );
        }
    }

    /*
    public static void generateStructure() {
        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.NETHER)
                biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES,
                        //Feature.STRUCTURES.put("necrontest", Structures.NECRON_TEST));
        }
    }

     */
}