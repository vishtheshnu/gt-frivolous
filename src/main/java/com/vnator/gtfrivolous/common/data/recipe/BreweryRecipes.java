package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.FrivolousItems.*;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.MYSTICAL_BIOMASS;

public class BreweryRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.BREWING_RECIPES.recipeBuilder("mystical_biomass")
                .inputItems(MYSTICAL_PETAL_MULCH)
                .inputFluids(GTMaterials.Water, 100)
                .outputFluids(MYSTICAL_BIOMASS.getFluid(100))
                .EUt(30)
                .duration(100)
                .save(provider);
    }
}
