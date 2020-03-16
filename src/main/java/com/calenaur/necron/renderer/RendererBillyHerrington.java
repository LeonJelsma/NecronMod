package com.calenaur.necron.renderer;

import com.calenaur.necron.NecronMod;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class RendererBillyHerrington extends AbstractZombieRenderer {
	private static final ResourceLocation BILLY_HERRINGTON_TEXTURES = new ResourceLocation(NecronMod.MOD_ID,"textures/entity/billy_herrington.png");

	public RendererBillyHerrington(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ZombieModel<>(0.0F, false), new ZombieModel<>(0.5F, true), new ZombieModel<>(1.0F, true));
	}

	@Override
	public ResourceLocation getEntityTexture(ZombieEntity entity) {
		entity.playAmbientSound();
		return BILLY_HERRINGTON_TEXTURES;
	}
}
