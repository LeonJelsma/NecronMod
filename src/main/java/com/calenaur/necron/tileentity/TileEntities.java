package com.calenaur.necron.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntities {
    @ObjectHolder("necron:mote_processor_tile")
    public static TileEntityType<TileEntityMoteProcessor> MOTE_PROCESSOR;

    @ObjectHolder("necron:hungry_metal_tile")
    public static TileEntityType<TileEntityMoteProcessor> HUNGRY_METAL;

    @ObjectHolder("necron:hungry_metal_arranger_tile")
    public static TileEntityType<TileEntityHungryMetalArranger> HUNGRY_METAL_MAKER;
}
