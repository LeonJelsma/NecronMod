package com.calenaur.necron.entity;

import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.world.World;

public class EntityNecronSoldier extends WitherSkeletonEntity {
	public EntityNecronSoldier(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
		super(entityType, world);
	}
}
