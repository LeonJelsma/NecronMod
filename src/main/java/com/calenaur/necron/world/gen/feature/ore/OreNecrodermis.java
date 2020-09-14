package com.calenaur.necron.world.gen.feature.ore;

import com.calenaur.necron.block.Blocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;

import static net.minecraft.world.biome.Biome.Category.*;

public class OreNecrodermis extends OreFeatureConfig {
	public static final ConfiguredPlacement<?> CONFIGURED_PLACEMENT = Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(10, 60, 16));
	public static final ImmutableList<Biome.Category> BIOME_WHITELIST = ImmutableList.of(
			TAIGA,
			EXTREME_HILLS,
			JUNGLE,
			MESA,
			PLAINS,
			SAVANNA,
			ICY,
			BEACH,
			FOREST,
			OCEAN,
			DESERT,
			RIVER,
			SWAMP,
			MUSHROOM
	);

	public OreNecrodermis() {
		super(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.NECRON_ORE.getDefaultState(), 3);
	}
}
