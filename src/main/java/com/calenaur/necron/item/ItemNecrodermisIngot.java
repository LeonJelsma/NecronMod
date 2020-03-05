package com.calenaur.necron.item;
import com.calenaur.necron.NecronMod;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ItemNecrodermisIngot extends Item {

    public ItemNecrodermisIngot() {
        super(new Properties()
                .group(NecronMod.necronItems));

        setRegistryName("necrodermis_ingot");
    }
}
