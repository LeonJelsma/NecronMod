package com.calenaur.necron.blocks;


import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockNecrodermisOre extends OreBlock {

    public BlockNecrodermisOre() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("necrodermis_ore");
    }

    @Override
    public ResourceLocation getLootTable() {
        return super.getLootTable();
    }


}
