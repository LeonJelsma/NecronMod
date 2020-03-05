package com.calenaur.necron.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemNecrodermisHoe extends HoeItem {
    public ItemNecrodermisHoe() {
        super(ItemTier.NECRODERMIS, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS));
        setRegistryName("necrodermis_hoe");
    }
}
