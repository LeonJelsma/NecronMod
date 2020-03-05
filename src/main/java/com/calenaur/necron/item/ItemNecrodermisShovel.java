package com.calenaur.necron.item;

import net.minecraft.item.ShovelItem;

public class ItemNecrodermisShovel extends ShovelItem {
    public ItemNecrodermisShovel() {
        super(ItemTier.NECRODERMIS, 1, -2.8F, new Properties().group(ItemGroup.NECRON));
        setRegistryName("necrodermis_shovel");
    }
}
