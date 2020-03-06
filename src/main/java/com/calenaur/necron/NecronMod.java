package com.calenaur.necron;

import com.calenaur.necron.event.EventNecrodermisItem;
import com.calenaur.necron.registry.Registrar;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("necron")
public class NecronMod {
    public NecronMod() {
        MinecraftForge.EVENT_BUS.register(new Registrar());
        MinecraftForge.EVENT_BUS.register(new EventNecrodermisItem());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Registrar::clientSetup);
    }
}
