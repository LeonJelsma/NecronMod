package com.calenaur.necron.item;

import net.minecraft.item.HoeItem;

public class ItemNecrodermisHoe extends HoeItem {
    public ItemNecrodermisHoe() {
        super(ItemTier.NECRODERMIS, -2.8F, new Properties().group(ItemGroup.NECRON));
        setRegistryName("necrodermis_hoe");
    }
}
