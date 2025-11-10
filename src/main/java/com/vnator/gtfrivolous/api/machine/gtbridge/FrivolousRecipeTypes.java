package com.vnator.gtfrivolous.api.machine.gtbridge;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class FrivolousRecipeTypes {
    // TODO create custom textures and sounds for recipe types

    public static GTRecipeType BOTANIA_PURITY_PROCESSOR = GTRecipeTypes
            .register("botania_purity_processor", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(1, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.SUS_RECORD);

    public static GTRecipeType BOTANIA_MAGICAL_GREENHOUSE = GTRecipeTypes
            .register("botania_magical_greenhouse", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(1, 4, 1, 1)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT);

    public static GTRecipeType BOTANIA_ORECHID_MEGACONVERTER = GTRecipeTypes
            .register("botania_orechid_megaconverter", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(2, 4, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MINER);

    public static GTRecipeType BOTANIA_BALANCED_CLAYWORKS = GTRecipeTypes
            .register("botania_balanced_clayworks", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(1, 1, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.BATH);

    public static void init() {}
}
