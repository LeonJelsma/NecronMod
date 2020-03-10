package com.calenaur.necron.sound;

import com.calenaur.necron.NecronMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundMoteProcessorLit extends SoundEvent {
	public SoundMoteProcessorLit() {
		super(new ResourceLocation(NecronMod.MOD_ID, "mote_processor_lit"));
		setRegistryName("mote_processor_lit");
	}
}
