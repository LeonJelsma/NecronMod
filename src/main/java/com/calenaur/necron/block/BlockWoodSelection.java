package com.calenaur.necron.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWoodSelection extends Block {
    public BlockWoodSelection() {
        super(Properties.create(Material.WOOD)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.WOOD)
        );
        setRegistryName("wood_selection");
    }
}
