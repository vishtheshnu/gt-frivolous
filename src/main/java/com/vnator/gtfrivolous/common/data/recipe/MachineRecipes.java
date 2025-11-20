package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader;

import net.minecraft.data.recipes.FinishedRecipe;

import com.vnator.gtfrivolous.common.data.FrivolousBlocks;
import com.vnator.gtfrivolous.common.data.FrivolousMachines;
import com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.CABLE;
import static com.vnator.gtfrivolous.common.data.recipe.FrivolousCraftingComponents.*;

public class MachineRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "magic_casing_lv",
                FrivolousBlocks.MACHINE_CASING_MAGIC_LV.asStack(),
                "MMM", "MwM", "MMM", 'M', new MaterialEntry(TagPrefix.plate, FrivolousMaterials.MANA_STEEL));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "magic_casing_mv",
                FrivolousBlocks.MACHINE_CASING_MAGIC_MV.asStack(),
                "TTT", "TwT", "TTT", 'T', new MaterialEntry(TagPrefix.plate, FrivolousMaterials.TERRA_STEEL));

        MetaTileEntityLoader.registerMachineRecipe(provider, false, FrivolousMachines.magicHulls, "PLP", "CHC", 'P',
                HULL_PLATE, 'L', PLATE, 'C', CABLE,
                'H', CASING);
    }
}
