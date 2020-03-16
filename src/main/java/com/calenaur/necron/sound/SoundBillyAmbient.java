package com.calenaur.necron.sound;

import com.calenaur.necron.NecronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundBillyTaunt extends SoundEvent {
	public SoundBillyTaunt() {
		super(new ResourceLocation(NecronMod.MOD_ID, "billy_herrington_taunt"));
		setRegistryName("billy_herrington_taunt");
	}
}
