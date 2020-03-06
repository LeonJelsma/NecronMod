package com.calenaur.necron.world.gen.ore;

import com.calenaur.necron.block.Blocks;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

public class OreNecrodermis extends OreFeatureConfig {
	public static ConfiguredPlacement<?> CONFIGURED_PLACEMENT = Placement.COUNT_DEPTH_AVERAGE.func_227446_a_(new DepthAverageConfig(10, 60, 16));

	public OreNecrodermis() {
		super(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.NECRON_ORE.getDefaultState(), 3);
	}
}
