package com.calenaur.necron.inventory.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

public class ContainerTypes {
    @ObjectHolder("necron:mote_processor")
    public static ContainerType<ContainerMoteProcessor> MOTE_PROCESSOR;
    @ObjectHolder("necron:rift_sack")
    public static ContainerType<ContainerRiftSack> RIFT_SACK;
}
