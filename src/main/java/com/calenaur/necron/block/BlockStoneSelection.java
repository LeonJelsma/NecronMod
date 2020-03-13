package com.calenaur.necron.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStoneSelection extends Block {
    public BlockStoneSelection() {
        super(Block.Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("stone_selection");
    }
}
