package com.calenaur.necron.misc;

import com.calenaur.necron.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

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
}