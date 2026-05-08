package com.vnator.gtfrivolous.common.data.recipe;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.Consumer;

import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.DIRT_SIFTER;

public class DirtSifterRecipes {

    private static final List<Item> siftingBlocks = List.of(
            Items.COBBLESTONE,
            Items.ANDESITE,
            Items.GRANITE,
            Items.DIORITE,
            Items.NETHERRACK,
            Items.DIRT);

    public static void init(Consumer<FinishedRecipe> provider) {
        siftingBlocks.forEach(block -> DIRT_SIFTER
                .recipeBuilder("dirt_sifting_" + block.toString())
                .notConsumable(block)
                .outputItems(block)
                .EUt(0)
                .duration(100)
                .save(provider));
    }
}
