package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ItemStoneSelection extends BlockItem {
    public ItemStoneSelection() {
        super(Blocks.STONE_SELECTION, new Properties().group(ItemGroup.NECRON));
        setRegistryName("stone_selection");
    }
}
