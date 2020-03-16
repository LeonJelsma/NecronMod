package com.calenaur.necron.item;

import com.calenaur.necron.entity.type.EntityTypes;
import net.minecraft.item.SpawnEggItem;

public class ItemNecronSoldierSpawnEgg extends SpawnEggItem {
	public ItemNecronSoldierSpawnEgg() {
    	super(EntityTypes.NECRON_SOLDIER, 0x121212, 0x008000, new Properties().group(ItemGroup.NECRON));
    	setRegistryName("necron_soldier_spawn_egg");
	}
}
