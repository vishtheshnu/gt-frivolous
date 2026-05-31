package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.MYSTICAL_BACTERIA;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.MYSTICAL_BIOMASS;

public class FermenterRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.FERMENTING_RECIPES.recipeBuilder("mystical_bacteria")
                .inputFluids(MYSTICAL_BIOMASS.getFluid(400))
                .outputFluids(MYSTICAL_BACTERIA.getFluid(100))
                .EUt(120)
                .duration(200)
                .save(provider);
    }
}
