package com.calenaur.necron.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class TileEntities {
    @ObjectHolder("necron:mote_processor_tile")
    public static TileEntityType<TileEntityMoteProcessor> MOTE_PROCESSOR;

    @ObjectHolder("necron:grey_goo_tile")
    public static TileEntityType<TileEntityMoteProcessor> GREY_GOO;
}
