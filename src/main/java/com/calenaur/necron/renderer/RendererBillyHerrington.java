package com.calenaur.necron.renderer;

import com.calenaur.necron.NecronMod;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.util.ResourceLocation;

public class RendererNecronSoldier extends WitherSkeletonRenderer {
	private static final ResourceLocation NECRON_SOLDIER_TEXTURES = new ResourceLocation(NecronMod.MOD_ID,"textures/entity/necron_soldier.png");

	public RendererNecronSoldier(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}

	public ResourceLocation getEntityTexture(AbstractSkeletonEntity entity) {
		return NECRON_SOLDIER_TEXTURES;
	}
}
