package com.vnator.gtfrivolous.common.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.DIRT_SIFTER;

public class DirtSifterRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        DIRT_SIFTER.recipeBuilder("dirt_sifting_cobblestone")
                .notConsumable(new ItemStack(Items.COBBLESTONE))
                .outputItems(new ItemStack(Items.COBBLESTONE))
                .EUt(0)
                .duration(20)
                .save(provider);

        DIRT_SIFTER.recipeBuilder("dirt_sifting_andesite")
                .notConsumable(new ItemStack(Items.ANDESITE))
                .outputItems(new ItemStack(Items.ANDESITE))
                .EUt(0)
                .duration(20)
                .save(provider);
    }
}
