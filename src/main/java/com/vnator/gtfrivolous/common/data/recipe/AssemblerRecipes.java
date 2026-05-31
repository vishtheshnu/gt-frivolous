package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;

import com.vnator.gtfrivolous.FrivolousItems;
import com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials;
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

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder("transistor")
                .inputItems(GTItems.SILICON_WAFER)
                .inputItems(TagPrefix.wireFine, GTMaterials.Tin, 4)
                .inputFluids(FrivolousMaterials.ECTO_PLASTIC.getFluid(144))
                .outputItems(GTItems.TRANSISTOR, 8)
                .EUt(120)
                .duration(160)
                .save(provider);
    }
}
