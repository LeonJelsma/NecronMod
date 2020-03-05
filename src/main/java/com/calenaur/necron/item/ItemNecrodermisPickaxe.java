package com.calenaur.necron.item;

import net.minecraft.item.*;

public class ItemNecrodermisPickaxe extends PickaxeItem {
    public ItemNecrodermisPickaxe() {
        super(ItemTier.NECRODERMIS, 1, -2.8F, new Properties().group(ItemGroup.NECRON));
        setRegistryName("necrodermis_pickaxe");
    }
}
