package com.vnator.gtfrivolous.common.data;

import net.minecraft.data.recipes.FinishedRecipe;

import com.vnator.gtfrivolous.common.data.recipe.*;

import java.util.function.Consumer;

public class FrivolousRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        // Vanilla
        MachineRecipes.init(provider);
        CraftingRecipes.init(provider);

        // GT Machines
        AlloySmelterRecipes.init(provider);
        AssemblerRecipes.init(provider);

        // Frivolous
        BotanicRecipes.init(provider);
        DirtSifterRecipes.init(provider);
        StrainerRecipes.init(provider);
    }
}
