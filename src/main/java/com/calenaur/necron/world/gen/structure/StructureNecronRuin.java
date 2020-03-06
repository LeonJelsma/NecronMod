package com.calenaur.necron.world.gen.structure;


import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class StructureNecronRuin extends ScatteredStructure<NoFeatureConfig> {

    public StructureNecronRuin(){
        super(NoFeatureConfig::deserialize);
        setRegistryName("necron_ruin");
    }

    @Override
    public IStartFactory getStartFactory() {
        return StructureNecronRuin.Start::new;
    }

    @Override
    public String getStructureName() {
        return "Necron Ruin";
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    protected int getSeedModifier() {
        return 2502;
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i225806_1_, int p_i225806_2_, int p_i225806_3_, MutableBoundingBox p_i225806_4_, int p_i225806_5_, long p_i225806_6_) {
            super(p_i225806_1_, p_i225806_2_, p_i225806_3_, p_i225806_4_, p_i225806_5_, p_i225806_6_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            NoFeatureConfig nofeatureconfig = (NoFeatureConfig) generator.getStructureConfig(biomeIn, Structures.NECRON_RUIN);
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            IglooPieces.func_207617_a(templateManagerIn, blockpos, rotation, this.components, this.rand, nofeatureconfig);
            this.recalculateStructureSize();
        }
    }
}