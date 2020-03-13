package com.calenaur.necron.inventory.container;

import com.calenaur.necron.multiblock.ContainerPlasmaGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class ContainerTypes {
    @ObjectHolder("necron:mote_processor")
    public static ContainerType<ContainerMoteProcessor> MOTE_PROCESSOR;
    @ObjectHolder("necron:rift_sack")
    public static ContainerType<ContainerRiftSack> RIFT_SACK;
    @ObjectHolder("necron:hungry_metal_arranger")
    public static ContainerType<ContainerHungryMetalArranger> HUNGRY_METAL_ARRANGER;
    @ObjectHolder("necron:plasma_generator")
    public static ContainerType<ContainerPlasmaGenerator> PLASMA_GENERATOR;
}
