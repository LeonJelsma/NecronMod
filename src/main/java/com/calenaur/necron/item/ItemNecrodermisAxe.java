package com.calenaur.necron.item;

import net.minecraft.item.*;

public class ItemNecrodermisAxe extends AxeItem {
    public ItemNecrodermisAxe() {
        super(ItemTier.NECRODERMIS, 1, -2.8F, new Properties().group(ItemGroup.NECRON));
        setRegistryName("necrodermis_axe");
    }
}
