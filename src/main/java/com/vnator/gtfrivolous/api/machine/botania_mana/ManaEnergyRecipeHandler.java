package com.vnator.gtfrivolous.api.machine.botania_mana;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.utils.GTMath;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManaEnergyRecipeHandler implements IRecipeHandler<EnergyStack> {

    public static final double DEFAULT_EU_TO_MANA_CONVERSION_RATE = 1.0;
    private final ManaConsumer machine;
    private final double conversionRate; // Mana/EU

    public ManaEnergyRecipeHandler(ManaConsumer machine, double conversionRate) {
        this.machine = machine;
        this.conversionRate = conversionRate;
    }

    @Override
    public List<EnergyStack> handleRecipeInner(IO io, GTRecipe recipe, List<EnergyStack> left, boolean simulate) {
        int currentMana = machine.getMana();
        for (var it = left.listIterator(); it.hasNext();) {
            EnergyStack stack = it.next();
            if (stack.isEmpty()) {
                it.remove();
                continue;
            }

            long stackEU = stack.getTotalEU();
            int stackMana = GTMath.saturatedCast((long) Math.ceil(stackEU * conversionRate));
            if (stackMana > 0) {
                if (io == IO.IN) {
                    // Consuming Mana
                    if (currentMana >= stackMana) {
                        if (!simulate) {
                            machine.addMana(-stackMana);
                        }
                        currentMana -= stackMana;
                        it.remove();
                    }
                } else if (io == IO.OUT) {
                    // Producing Mana
                    // TODO hook up actual mana pool in machine/multiblock when time to create
                    if (currentMana + stackMana <= machine.getMaxMana()) {
                        if (!simulate) {
                            machine.addMana(stackMana);
                        }
                        currentMana += stackMana;
                        it.remove();
                    }
                }
            }
        }
        return left.isEmpty() ? null : left;
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(getTotalContentAmount());
    }

    @Override
    public double getTotalContentAmount() {
        return (long) (machine.getMana() * conversionRate);
    }

    @Override
    public RecipeCapability<EnergyStack> getCapability() {
        return EURecipeCapability.CAP;
    }
}
