package com.calenaur.necron.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;

public class ItemJeffPickaxe extends PickaxeItem {

    public ItemJeffPickaxe() {

        super(ItemTier.NECRODERMIS, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS));
        setRegistryName("jeffite_pickaxe");
    }
}
