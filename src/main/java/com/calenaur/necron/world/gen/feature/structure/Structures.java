package com.calenaur.necron.world.gen.feature.structure;

import com.calenaur.necron.world.gen.feature.structure.ruin.StructureNecronRuin;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraftforge.registries.ObjectHolder;

public class Structures {
	@ObjectHolder("necron:necron_ruin")
	public static StructureNecronRuin NECRON_RUIN;
	public static IStructurePieceType NECRON_RUIN_PIECE;
}
