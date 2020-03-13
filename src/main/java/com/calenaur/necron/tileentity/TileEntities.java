package com.calenaur.necron.tileentity;

import com.calenaur.necron.multiblock.TilePlasmaGenerator;
import com.calenaur.necron.multiblock.TilePlasmaGeneratorPart;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntities {
    @ObjectHolder("necron:mote_processor_tile")
    public static TileEntityType<TileEntityMoteProcessor> MOTE_PROCESSOR;

    @ObjectHolder("necron:hungry_metal_tile")
    public static TileEntityType<TileEntityMoteProcessor> HUNGRY_METAL;

    @ObjectHolder("necron:hungry_metal_arranger_tile")
    public static TileEntityType<TileEntityHungryMetalArranger> HUNGRY_METAL_MAKER;

    @ObjectHolder("necron:plasma_generator_tile")
    public static TileEntityType<TilePlasmaGenerator> PLASMA_GENERATOR;

    @ObjectHolder("necron:plasma_generator_part_tile")
    public static TileEntityType<TilePlasmaGeneratorPart> PLASMA_GENERATOR_PART;

}
