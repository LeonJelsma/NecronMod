package com.calenaur.necron.sound;

import com.calenaur.necron.NecronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundBillyHurt extends SoundEvent {
	public SoundBillyHurt() {
		super(new ResourceLocation(NecronMod.MOD_ID, "billy_herrington_hurt"));
		setRegistryName("billy_herrington_hurt");
	}
}
