package com.vnator.gtfrivolous.api.machine.multiblock;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;

import net.minecraft.network.chat.Component;

import com.vnator.gtfrivolous.api.machine.botania_mana.ManaConsumer;
import com.vnator.gtfrivolous.api.machine.botania_mana.ManaEnergyRecipeHandler;
import com.vnator.gtfrivolous.api.machine.part.ManaPartAbility;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.vnator.gtfrivolous.api.machine.botania_mana.ManaEnergyRecipeHandler.DEFAULT_EU_TO_MANA_CONVERSION_RATE;

public class BotaniaWorkableMultiblockMachine extends WorkableMultiblockMachine implements IDisplayUIMachine {

    @Nullable
    private ManaEnergyRecipeHandler manaEnergy;
    private ManaConsumer manaHatch;

    public BotaniaWorkableMultiblockMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (var part : getParts()) {
            if (!ManaPartAbility.MANA_HATCH.isApplicable(part.self().getDefinition().getBlock())) continue;
            if (part instanceof ManaConsumer manaConsumer) {
                manaHatch = manaConsumer;
                manaEnergy = new ManaEnergyRecipeHandler(manaConsumer, DEFAULT_EU_TO_MANA_CONVERSION_RATE);
                addHandlerList(RecipeHandlerList.of(IO.IN, manaEnergy));
                subscribeServerTick(manaHatch::drawManaFromPool);
                return;
            }
        }

        if (manaHatch == null) {
            onStructureInvalid();
        }
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (isFormed()) {
            if (manaHatch != null && manaHatch.getMana() > 0) {
                textList.add(Component.translatable("gtfrivolous.multiblock.mana.mana_stored",
                        manaHatch.getMana(),
                        manaHatch.getMaxMana()));
            }

            if (!isWorkingEnabled()) {
                textList.add(Component.translatable("gtceu.multiblock.work_paused"));
            } else if (isActive()) {
                textList.add(Component.translatable("gtceu.multiblock.running"));
                int currentProgress = (int) (recipeLogic.getProgressPercent() * 100);
                double maxInSec = (float) recipeLogic.getDuration() / 20.0f;
                double currentInSec = (float) recipeLogic.getProgress() / 20.0f;
                textList.add(
                        Component.translatable("gtceu.multiblock.progress", String.format("%.2f", (float) currentInSec),
                                String.format("%.2f", (float) maxInSec), currentProgress));
            } else {
                textList.add(Component.translatable("gtceu.multiblock.idling"));
            }
        }
    }
}
