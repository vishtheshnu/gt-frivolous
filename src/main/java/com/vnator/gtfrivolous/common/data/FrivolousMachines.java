package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.vnator.gtfrivolous.GTFrivolous;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicMachine;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs registration of this addon's machines
 */
public class FrivolousMachines {
    static {
        GTFrivolous.REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    public static final MachineDefinition[] botanicCentrifuge = GTMachineUtils.registerTieredMachines(
            GTFrivolous.REGISTRATE, "botanic_centrifuge",
            (holder, tier) -> new BotanicMachine(holder, tier),
            (tier, builder) -> builder
                    .langValue("Botanic Centrifuge %s".formatted(GTValues.LVT[tier]))
                    .editableUI(BotanicMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id("centrifuge"),
                            GTRecipeTypes.CENTRIFUGE_RECIPES))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
                    .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                    .workableTieredHullModel(GTCEu.id("block/machines/centrifuge"))
                    .tooltips(tooltipsBotanic(tier, GTRecipeTypes.CENTRIFUGE_RECIPES, true))
                    .register(),
            1, 2
    );

    public static Component[] tooltipsBotanic(int tier, GTRecipeType recipeType, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents.add(Component.translatable("gtfrivolous.universal.botanic"));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static Component[] workableTiered(int tier, long voltage, long energyCapacity, GTRecipeType recipeType,
                                             long tankCapacity, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents
                .add(input ?
                        Component.translatable("gtceu.universal.tooltip.voltage_in",
                                FormattingUtil.formatNumbers(voltage), GTValues.VNF[tier]) :
                        Component.translatable("gtceu.universal.tooltip.voltage_out",
                                FormattingUtil.formatNumbers(voltage), GTValues.VNF[tier]));
        tooltipComponents
                .add(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                        FormattingUtil.formatNumbers(energyCapacity)));
        if (recipeType.getMaxInputs(FluidRecipeCapability.CAP) > 0 ||
                recipeType.getMaxOutputs(FluidRecipeCapability.CAP) > 0)
            tooltipComponents
                    .add(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            FormattingUtil.formatNumbers(tankCapacity)));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static void init() {

    }
}
