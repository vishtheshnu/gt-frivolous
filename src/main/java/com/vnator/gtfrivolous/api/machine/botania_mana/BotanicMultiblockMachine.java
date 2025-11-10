package com.vnator.gtfrivolous.api.machine.botania_mana;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class BotanicMultiblockMachine extends WorkableMultiblockMachine implements IDisplayUIMachine {

    public BotanicMultiblockMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        // getMultiblockState().getMatchContext().get()
        // for (var part : getParts()) {
        // part.addedToController();
        // }

        // Confirm one of the structure blocks' entities is instanceof ManaPool
        // If so, save it, have it hooked up to the ManaConsumable methods, and construct and save
        // ManaEnergyRecipeHandler

        // Otherwise if missing, call onStructureInvalid()
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        IDisplayUIMachine.super.addDisplayText(textList);
    }

    @Override
    public IGuiTexture getScreenTexture() {
        // TODO create custom botania/manasteel/terrasteel screen texture
        return IDisplayUIMachine.super.getScreenTexture();
    }

    @Override
    public ModularUI createUI(Player entityPlayer) {
        // TODO consider using custom botania texture with rest of contents of super method
        return IDisplayUIMachine.super.createUI(entityPlayer);
    }
}
