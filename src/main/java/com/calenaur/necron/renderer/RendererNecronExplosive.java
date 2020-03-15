package com.calenaur.necron.renderer;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TNTRenderer;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.ResourceLocation;

public class RendererNecronExplosive extends TNTRenderer {

	public RendererNecronExplosive(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
	}


	@Override
	public ResourceLocation getEntityTexture(TNTEntity entity) {
		return super.getEntityTexture(RESOURCEL);
	}
}
