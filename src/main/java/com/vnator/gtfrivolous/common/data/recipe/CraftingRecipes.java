package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import vazkii.botania.common.block.BotaniaBlocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.vnator.gtfrivolous.FrivolousItems.*;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.*;

public class CraftingRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, "botanic_circuit", LV_BOTANIC_CIRCUIT.asStack(),
                "RPR", "VBV", "WWW", 'R', GTItems.RESISTOR, 'P', new MaterialEntry(TagPrefix.plate, MANA_STEEL),
                'V', MANAGLASS_VACUUM_TUBE, 'B', LIVINGWOOD_PRINTED_CIRCUIT_BOARD, 'W',
                new MaterialEntry(TagPrefix.wireGtSingle, RedAlloy));

        VanillaRecipeHelper.addShapedRecipe(provider, "managlass_tube", MANAGLASS_TUBE.asStack(),
                "PPP", "P P", "PPP", 'P', BotaniaBlocks.managlassPane.asItem());
        VanillaRecipeHelper.addShapedRecipe(provider, "managlass_vacuum_tube", MANAGLASS_VACUUM_TUBE.asStack(),
                "BTB", "WWW", 'B', new MaterialEntry(TagPrefix.bolt, MANA_STEEL), 'T', MANAGLASS_TUBE, 'W',
                new MaterialEntry(TagPrefix.wireGtSingle, RedAlloy));

        VanillaRecipeHelper.addShapedRecipe(provider, "livingwood_printed_board",
                LIVINGWOOD_PRINTED_CIRCUIT_BOARD.asStack(),
                "CCC", "CBC", "CCC", 'C', new MaterialEntry(TagPrefix.wireGtSingle, Copper), 'B',
                LIVINGWOOD_CIRCUIT_BOARD);
        VanillaRecipeHelper.addShapedRecipe(provider, "livingwood_board_triple", LIVINGWOOD_CIRCUIT_BOARD.asStack(3),
                "RRR", "WWW", "RRR", 'R', GTItems.STICKY_RESIN, 'W', BotaniaBlocks.livingwoodPlanks.asItem());
        VanillaRecipeHelper.addShapelessRecipe(provider, "livingwood_board_single", LIVINGWOOD_CIRCUIT_BOARD.asStack(),
                BotaniaBlocks.livingwoodPlanks.asItem(), GTItems.STICKY_RESIN, GTItems.STICKY_RESIN);
    }
}
