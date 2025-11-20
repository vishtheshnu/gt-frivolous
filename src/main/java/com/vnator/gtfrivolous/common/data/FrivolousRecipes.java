package com.vnator.gtfrivolous.common.data;

import net.minecraft.data.recipes.FinishedRecipe;

import com.vnator.gtfrivolous.common.data.recipe.*;

import java.util.function.Consumer;

public class FrivolousRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        BotanicRecipes.init(provider);
        MachineRecipes.init(provider);
        CraftingRecipes.init(provider);

        AlloySmelterRecipes.init(provider);
        AssemblerRecipes.init(provider);
    }
}
