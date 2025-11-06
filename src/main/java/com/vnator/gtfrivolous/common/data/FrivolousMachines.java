package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.vnator.gtfrivolous.GTFrivolous;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicMachine;
import com.vnator.gtfrivolous.api.machine.botania_mana.ManaPoolBindableMachine;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Performs registration of this addon's machines
 */
public class FrivolousMachines {

    private static final String BOTANIC_MACHINE_TOOLTIP = "gtfrivolous.machine.botanic";

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
            1, 2);

    public static Component[] tooltipsBotanic(int tier, GTRecipeType recipeType, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents.add(Component.translatable(BOTANIC_MACHINE_TOOLTIP));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static void registerBotaniaWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
        consumer.accept(be -> new ManaPoolBindableMachine.BindableMachineWandHud<>(extractBotanicMachineFromEntity(be)),
                getBlockEntityTypes(botanicCentrifuge));
    }

    private static ManaPoolBindableMachine extractBotanicMachineFromEntity(BlockEntity entity) {
        return (ManaPoolBindableMachine) ((MetaMachineBlockEntity) entity).getMetaMachine();
    }

    private static BlockEntityType<?>[] getBlockEntityTypes(MachineDefinition[] defs) {
        return Stream.of(defs)
                .filter(Objects::nonNull)
                .map(MachineDefinition::getBlockEntityType)
                .toArray(BlockEntityType[]::new);
    }

    public static void init() {}
}
