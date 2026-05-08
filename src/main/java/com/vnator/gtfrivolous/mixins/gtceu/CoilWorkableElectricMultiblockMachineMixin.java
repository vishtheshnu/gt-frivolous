package com.vnator.gtfrivolous.mixins.gtceu;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;

import com.vnator.gtfrivolous.api.machine.botania_mana.ManaConsumer;
import com.vnator.gtfrivolous.api.machine.botania_mana.ManaEnergyRecipeHandler;
import com.vnator.gtfrivolous.api.machine.part.ManaPartAbility;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.vnator.gtfrivolous.api.machine.botania_mana.ManaEnergyRecipeHandler.DEFAULT_EU_TO_MANA_CONVERSION_RATE;

@Debug(export = true)
@Mixin(value = CoilWorkableElectricMultiblockMachine.class, remap = false)
public class CoilWorkableElectricMultiblockMachineMixin {

    @Nullable
    private ManaEnergyRecipeHandler manaEnergy;
    private ManaConsumer manaHatch;

    @Inject(method = "onStructureFormed", at = @At(value = "TAIL"))
    public void onStructureFormed(CallbackInfo ci) {
        CoilWorkableElectricMultiblockMachine self = (CoilWorkableElectricMultiblockMachine) (Object) this;
        for (var part : self.getParts()) {
            if (!ManaPartAbility.MANA_HATCH.isApplicable(part.self().getDefinition().getBlock())) continue;
            if (part instanceof ManaConsumer manaConsumer) {
                manaHatch = manaConsumer;
                manaEnergy = new ManaEnergyRecipeHandler(manaConsumer, DEFAULT_EU_TO_MANA_CONVERSION_RATE);
                self.addHandlerList(RecipeHandlerList.of(IO.IN, manaEnergy));
                self.subscribeServerTick(manaHatch::drawManaFromPool);
                return;
            }
        }
    }
}
