package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import com.vnator.gtfrivolous.FrivolousItems;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.function.Consumer;

public class MaceratorRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.MACERATOR_RECIPES.recipeBuilder("mystical_petal_mulch")
                .inputItems(Ingredient.of(BotaniaTags.Items.PETALS))
                .outputItems(FrivolousItems.MYSTICAL_PETAL_MULCH)
                .EUt(8)
                .duration(10)
                .save(provider);
    }
}
