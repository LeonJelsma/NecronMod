package com.calenaur.necron;

import com.calenaur.necron.event.EventNecrodermisItem;
import com.calenaur.necron.registry.Registrar;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.calenaur.necron.NecronMod.MOD_ID;

@Mod(MOD_ID)
public class NecronMod {
    public static final String MOD_ID = "necron";
    public NecronMod() {
        MinecraftForge.EVENT_BUS.register(new Registrar());
        MinecraftForge.EVENT_BUS.register(new EventNecrodermisItem());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Registrar::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Registrar::commonSetup);
    }

    public static String namespace(String key) {
        return NecronMod.MOD_ID + ":" + key;
    }
}
