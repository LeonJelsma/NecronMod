package com.calenaur.necron.sound;

import com.calenaur.necron.NecronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundRiftSackOpen extends SoundEvent {
	public SoundRiftSackOpen() {
		super(new ResourceLocation(NecronMod.MOD_ID, "rift_sack_open"));
		setRegistryName("rift_sack_open");
	}
}
