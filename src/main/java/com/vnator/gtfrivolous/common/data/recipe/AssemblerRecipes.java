package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;

import com.vnator.gtfrivolous.FrivolousItems;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.function.Consumer;

public class AssemblerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("livingwood_circuit_board")
                .inputItems(BotaniaBlocks.livingwoodPlanks.asItem())
                .inputItems(TagPrefix.foil, GTMaterials.Copper)
                .inputFluids(GTMaterials.Glue, 100)
                .outputItems(FrivolousItems.LIVINGWOOD_PRINTED_CIRCUIT_BOARD)
                .duration(200)
                .EUt(7)
                .save(provider);
    }
}
