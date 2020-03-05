package com.calenaur.necron.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;

public class ItemNecrodermisShovel extends ShovelItem {
    public ItemNecrodermisShovel() {
        super(ItemTier.NECRODERMIS, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS));
        setRegistryName("necrodermis_shovel");
    }
}
