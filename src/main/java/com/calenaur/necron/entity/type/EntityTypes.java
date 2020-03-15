package com.calenaur.necron.entity.type;

import com.calenaur.necron.entity.EntityNecronExplosive;
import com.calenaur.necron.entity.EntityNecronSoldier;
import net.minecraft.entity.EntityType;

public class EntityTypes {
	public static final EntityType<EntityNecronSoldier> NECRON_SOLDIER = new EntityTypeNecronSoldier();

	public static final EntityType<EntityNecronExplosive> NECRON_EXPLOSIVE = new EntityTypeNecronExplosive();
}
