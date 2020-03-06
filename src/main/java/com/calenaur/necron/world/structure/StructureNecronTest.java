package com.calenaur.necron.world.structure;


import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.ScatteredStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;

public class StructureNecronTest extends ScatteredStructure<NoFeatureConfig> {

    public static final String NAME = "necron_test";

    public StructureNecronTest(Function<Dynamic<?>, ? extends NoFeatureConfig> deserialize){
        super(deserialize);
    }

    @Override
    protected int getSeedModifier() {
        return 0;
    }

    @Override
    public IStartFactory getStartFactory() {
        return null;
    }

    @Override
    public String getStructureName() {
        return NAME;
    }

    @Override
    public int getSize() {
        return 0;
    }

    public static class Start extends MarginedStructureStart {

        public Start(Structure<?> p_i50497_1_, int p_i50497_2_, int p_i50497_3_, MutableBoundingBox p_i50497_5_, int p_i50497_6_, long p_i50497_7_) {
            super(p_i50497_1_, p_i50497_2_, p_i50497_3_, p_i50497_5_, p_i50497_6_, p_i50497_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            int i = 5;
            int j = 5;
            if (rotation == Rotation.CLOCKWISE_90) {
                i = -5;
            } else if (rotation == Rotation.CLOCKWISE_180) {
                i = -5;
                j = -5;
            } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
                j = -5;
            }

            int k = (chunkX << 4) + 7;
            int l = (chunkZ << 4) + 7;
            int i1 = generator.func_222531_c(k, l, Heightmap.Type.WORLD_SURFACE_WG);
            int j1 = generator.func_222531_c(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
            int k1 = generator.func_222531_c(k + i, l, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
            int l1 = generator.func_222531_c(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
            int minHeight = Math.min(Math.min(i1, j1), Math.min(k1, l1));
            int maxHeight = Math.max(Math.max(i1, j1), Math.max(k1, l1));
            if (maxHeight - minHeight < 2 && maxHeight - minHeight > -2) {
                BlockPos blockpos = new BlockPos(chunkX * 16, minHeight + 2, chunkZ * 16);
                //TowerRuinPieces.func_215139_a(generator, templateManagerIn, blockpos, this.components, this.rand);
                this.recalculateStructureSize();
            }
        }
    }
}