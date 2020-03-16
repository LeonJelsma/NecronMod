package com.calenaur.necron.sound;

import com.calenaur.necron.NecronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundBillyAmbient extends SoundEvent {
	public SoundBillyAmbient() {
		super(new ResourceLocation(NecronMod.MOD_ID, "billy_herrington_ambient"));
		setRegistryName("billy_herrington_ambient");
	}
}
