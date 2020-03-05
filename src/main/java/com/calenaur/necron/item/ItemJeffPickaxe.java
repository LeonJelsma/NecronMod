package com.calenaur.necron.item;

import net.minecraft.item.PickaxeItem;

public class ItemJeffPickaxe extends PickaxeItem {
    public ItemJeffPickaxe() {
        super(ItemTier.JEFFITE, 1, -2.8F, new Properties().group(ItemGroup.NECRON));
        setRegistryName("jeffite_pickaxe");
    }
}
