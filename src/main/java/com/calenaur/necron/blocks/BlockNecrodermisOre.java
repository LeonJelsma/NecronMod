package com.calenaur.necron.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BlockNecrodermisOre extends Block {

    public BlockNecrodermisOre() {
        super(Properties.create(Material.ROCK)
                .harvestLevel(3)
                .hardnessAndResistance(3)
                .sound(SoundType.STONE)
        );
        setRegistryName("necrodermis_ore");
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity entity) {
        super.onBlockHarvested(world, pos, state, entity);

        BlockItem blockItem = new BlockItem(new BlockNecrodermisOre(), new Item.Properties());
        Item item = blockItem.getItem();


        ItemStack stack = new ItemStack(item);
    }

    @Override
    public ResourceLocation getLootTable() {
        return super.getLootTable();
    }
}
