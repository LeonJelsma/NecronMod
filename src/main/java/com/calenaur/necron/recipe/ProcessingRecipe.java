package com.calenaur.necron.recipe;


import com.calenaur.necron.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.ResourceLocation;

public class ProcessingRecipe extends AbstractCookingRecipe {
    public static final String NAME = "mote_processing_recipe";

    public ProcessingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
        super(RecipeTypes.MOTE_PROCESSING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
    }

    public ItemStack getIcon() {
        return new ItemStack(Blocks.MOTE_PROCESSOR);
    }

    public IRecipeSerializer<?> getSerializer() {
        return Recipes.MOTE_PROCESSING;
    }
}

