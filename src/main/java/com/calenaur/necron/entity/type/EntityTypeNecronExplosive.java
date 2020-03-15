package com.calenaur.necron.entity.type;

import com.calenaur.necron.entity.EntityNecronExplosive;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;

public class EntityTypeNecronExplosive extends EntityType<EntityNecronExplosive> {
    public EntityTypeNecronExplosive() {
        this(EntityNecronExplosive::new, EntityClassification.MISC, true, true, true, EntitySize.flexible(1f, 1f));
        setRegistryName("necron_explosive");
    }

    private EntityTypeNecronExplosive(IFactory<EntityNecronExplosive> factory, EntityClassification entityClassification, boolean serializable, boolean summonable, boolean immuneToFire, EntitySize size) {
        super(factory, entityClassification, serializable, summonable, immuneToFire, false, size);
    }
}