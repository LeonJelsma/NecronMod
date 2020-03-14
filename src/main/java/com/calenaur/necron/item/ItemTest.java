package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemTest extends BlockItem {
    public ItemTest() {
        super(Blocks.TEST, new Properties().group(ItemGroup.NECRON));
        setRegistryName("test");
    }
}
