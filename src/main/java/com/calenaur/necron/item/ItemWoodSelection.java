package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemWoodSelection extends BlockItem {
    public ItemWoodSelection() {
        super(Blocks.WOOD_SELECTION, new Properties().group(ItemGroup.NECRON));
        setRegistryName("wood_selection");
    }
}
