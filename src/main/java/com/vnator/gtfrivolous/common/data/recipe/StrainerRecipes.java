package com.vnator.gtfrivolous.common.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.STRAINER;

public class StrainerRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        STRAINER.recipeBuilder("water")
                .notConsumable(new ItemStack(Items.STICK))
                .outputItems(new ItemStack(Blocks.ANDESITE.asItem()), new ItemStack(Items.BONE_MEAL))
                .EUt(0)
                .duration(20)
                .save(provider);
    }
}
