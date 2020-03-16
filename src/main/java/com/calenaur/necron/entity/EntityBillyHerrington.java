package com.calenaur.necron.entity;

import com.calenaur.necron.NecronMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityBillyHerrington extends ZombieEntity {
    public EntityBillyHerrington(EntityType<? extends ZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return new SoundEvent(new ResourceLocation(NecronMod.MOD_ID,"billy_herrington_ambient"));
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return new SoundEvent(new ResourceLocation(NecronMod.MOD_ID,"billy_herrington_hurt"));
    }
}
