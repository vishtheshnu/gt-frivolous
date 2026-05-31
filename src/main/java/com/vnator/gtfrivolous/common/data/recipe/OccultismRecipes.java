package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.OCCULTISM_MICRO_RITUAL_CHAMBER;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.*;

public class OccultismRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        OCCULTISM_MICRO_RITUAL_CHAMBER.recipeBuilder("ectoplastic")
                .inputFluids(MYSTICAL_BACTERIA.getFluid(1000), GTMaterials.SulfuricAcid.getFluid(1000))
                .outputFluids(ECTO_PLASTIC.getFluid(288))
                .EUt(120)
                .duration(200)
                .save(provider);
    }
}
