package com.calenaur.necron.entity.type;

import com.calenaur.necron.entity.EntityNecronSoldier;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;

public class EntityTypeNecronSoldier extends EntityType<EntityNecronSoldier> {
	public EntityTypeNecronSoldier() {
		this(EntityNecronSoldier::new, EntityClassification.MONSTER, true, true, true, EntitySize.flexible(0.7f, 2.4f));
		setRegistryName("necron_soldier");
	}

	private EntityTypeNecronSoldier(IFactory<EntityNecronSoldier> factory, EntityClassification entityClassification, boolean serializable, boolean summonable, boolean immuneToFire, EntitySize size) {
		super(factory, entityClassification, serializable, summonable, immuneToFire, false, size);
	}
}
