package com.calenaur.necron.item;

import com.calenaur.necron.entity.type.EntityTypes;
import net.minecraft.item.SpawnEggItem;

public class ItemBillyHerringtonSpawnEgg extends SpawnEggItem {
	public ItemBillyHerringtonSpawnEgg() {
    	super(EntityTypes.BILLY_HERRINGTON, 0xa83299, 0xf00e96, new Properties().group(ItemGroup.NECRON));
    	setRegistryName("billy_herrington_spawn_egg");
	}
}
