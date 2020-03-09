package com.calenaur.necron.item;

import com.calenaur.necron.block.Blocks;
import net.minecraft.item.BlockItem;

public class ItemGreyGoo extends BlockItem {
    public ItemGreyGoo() {
        super(Blocks.GREY_GOO, new Properties().group(ItemGroup.NECRON));
        setRegistryName("grey_goo");
    }
}
