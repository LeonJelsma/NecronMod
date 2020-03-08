package com.calenaur.necron.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class RecipeSerializerTypes {

    private static final List<IRecipeSerializer<?>> RECIPE_SERIALIZERS = Lists.newArrayList();

    public static final ProcessingRecipeSerializer PROCESSING = register("processing", new ProcessingRecipeSerializer());

    private static <T extends IRecipeSerializer<? extends IRecipe<?>>> T register(String registryName, T serializer) {
        serializer.setRegistryName(new ResourceLocation(registryName));
        RECIPE_SERIALIZERS.add(serializer);
        return serializer;
    }
}
