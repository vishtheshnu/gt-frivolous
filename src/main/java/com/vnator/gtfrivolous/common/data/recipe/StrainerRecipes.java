package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.STRAINER;

public class StrainerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        STRAINER.recipeBuilder("water")
                .notConsumable(Items.STICK)
                .chancedOutput(TagPrefix.nugget, GTMaterials.Iron, 20_00, 15_00)
                .chancedOutput(TagPrefix.nugget, GTMaterials.Gold, 20_00, 15_00)
                .chancedOutput(TagPrefix.nugget, GTMaterials.Copper, 20_00, 15_00)
                .chancedOutput(TagPrefix.nugget, GTMaterials.Tin, 20_00, 15_00)
                .chancedOutput(TagPrefix.dust, GTMaterials.Andesite, 25_00, 15_00)
                .chancedOutput(new ItemStack(Items.BONE_MEAL), 70_00, 15_00)
                .EUt(0)
                .duration(100)
                .save(provider);

        STRAINER.recipeBuilder("fill_bucket")
                .inputItems(Items.BUCKET)
                .outputItems(Items.WATER_BUCKET)
                .EUt(0)
                .duration(200)
                .save(provider);
    }
}
