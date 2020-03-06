package com.calenaur.necron.world.structure;

import com.calenaur.necron.NecronMod;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class Structures {


    public static final Structure<NoFeatureConfig> NECRON_TEST = create("tower_ruin", new StructureNecronTest(NoFeatureConfig::deserialize));


    private static <T extends Feature<?>> T create(String name, T feature) {
        feature.setRegistryName(name);
        return feature;
    }
}
