package com.calenaur.necron.item;

import net.minecraft.item.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public class ItemNecrodermisPickaxe extends PickaxeItem {


    public ItemNecrodermisPickaxe() {
        super(ItemTier.NECRODERMIS, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS));
        setRegistryName("necrodermis_pickaxe");
    }


}
