package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import com.vnator.gtfrivolous.GTFrivolous;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicHatch;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicMachine;
import com.vnator.gtfrivolous.api.machine.botania_mana.ManaPoolBindableMachine;
import com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes;
import com.vnator.gtfrivolous.api.machine.multiblock.BotaniaWorkableMultiblockMachine;
import com.vnator.gtfrivolous.api.machine.part.ManaPartAbility;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_ITEM_HATCH;
import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

/**
 * Performs registration of this addon's machines
 */
public class FrivolousMachines {

    private static final String BOTANIC_MACHINE_TOOLTIP = "gtfrivolous.machine.botanic";
    private static final ResourceLocation LIVINGROCK_CASE = GTFrivolous.id("block/casings/solid/livingrock");

    static {
        REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    /* Hatches */
    public static final MachineDefinition BOTANIA_MANA_HATCH = REGISTRATE
            .machine("mana_hatch", (holder) -> new BotanicHatch(holder, GTValues.LV, IO.IN))
            .langValue("Mana Hatch")
            .rotationState(RotationState.ALL)
            .tier(GTValues.LV)
            .modelProperty(GTMachineModelProperties.IS_FORMED, false)
            .colorOverlayTieredHullModel(GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"), null,
                    GTCEu.id("block/overlay/machine/" + OVERLAY_ITEM_HATCH))
            .abilities(ManaPartAbility.MANA_HATCH)
            .register();

    /* Multiblocks */
    public static final MultiblockMachineDefinition BOTANIA_PURITY_PROCESSOR = REGISTRATE
            .multiblock("botania_purity_processor", BotaniaWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(() -> BotaniaBlocks.livingrock)
            .recipeType(FrivolousRecipeTypes.BOTANIA_PURITY_PROCESSOR)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("RRR", "S#S")
                    .aisle("RDR", "#F#")
                    .aisle("RCR", "S#S")
                    .where('C', Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where('D', Predicates.blocks(Blocks.DIRT))
                    .where('R', Predicates.blocks(BotaniaBlocks.livingrock)
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(ManaPartAbility.MANA_HATCH).setPreviewCount(1)))
                    .where('S', Predicates.blocks(BotaniaBlocks.livingrock))
                    .where('F', Predicates.blocks(BotaniaFlowerBlocks.pureDaisy))
                    .where('#', Predicates.any())
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    LIVINGROCK_CASE,
                    GTCEu.id("block/machines/fermenter")))
            .tooltips(Component.translatable("gtfrivolous.multiblock.botania.purity_processor.tooltip"))
            .register();

    public static final MultiblockMachineDefinition BOTANIA_MAGICAL_GREENHOUSE = REGISTRATE
            .multiblock("botania_magical_greenhouse", BotaniaWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(() -> BotaniaBlocks.livingrock)
            .recipeType(FrivolousRecipeTypes.BOTANIA_MAGICAL_GREENHOUSE)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("RRR", "S#S")
                    .aisle("RDR", "#F#")
                    .aisle("RCR", "S#S")
                    .where('C', Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where('D', Predicates.blocks(Blocks.DIRT))
                    .where('R', Predicates.blocks(BotaniaBlocks.livingrock)
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(ManaPartAbility.MANA_HATCH).setPreviewCount(1)))
                    .where('S', Predicates.blocks(BotaniaBlocks.livingrock))
                    .where('F', Predicates.blocks(BotaniaFlowerBlocks.agricarnation))
                    .where('#', Predicates.any())
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    LIVINGROCK_CASE,
                    GTCEu.id("block/machines/brewery")))
            .tooltips(Component.translatable("gtfrivolous.multiblock.botania.magical_greenhouse.tooltip"))
            .register();

    public static final MultiblockMachineDefinition BOTANIA_ORECHID_MEGACONVERTER = REGISTRATE
            .multiblock("botania_orechid_megaconverter", BotaniaWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(() -> BotaniaBlocks.livingrock)
            .recipeType(FrivolousRecipeTypes.BOTANIA_ORECHID_MEGACONVERTER)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("RRR", "S#S")
                    .aisle("RDR", "#F#")
                    .aisle("RCR", "S#S")
                    .where('C', Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where('D', Predicates.blocks(Blocks.DIRT))
                    .where('R', Predicates.blocks(BotaniaBlocks.livingrock)
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(ManaPartAbility.MANA_HATCH).setPreviewCount(1)))
                    .where('S', Predicates.blocks(BotaniaBlocks.livingrock))
                    .where('F', Predicates.blocks(BotaniaFlowerBlocks.orechid))
                    .where('#', Predicates.any())
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    LIVINGROCK_CASE,
                    GTCEu.id("block/machines/miner")))
            .tooltips(Component.translatable("gtfrivolous.multiblock.botania.orechid_megaconverter.tooltip"))
            .register();

    public static final MultiblockMachineDefinition BOTANIA_BALANCED_CLAYWORKS = REGISTRATE
            .multiblock("botania_balanced_clayworks", BotaniaWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(() -> BotaniaBlocks.livingrock)
            .recipeType(FrivolousRecipeTypes.BOTANIA_BALANCED_CLAYWORKS)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("RRR", "S#S")
                    .aisle("RDR", "#F#")
                    .aisle("RCR", "S#S")
                    .where('C', Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where('D', Predicates.blocks(Blocks.DIRT))
                    .where('R', Predicates.blocks(BotaniaBlocks.livingrock)
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(ManaPartAbility.MANA_HATCH).setPreviewCount(1)))
                    .where('S', Predicates.blocks(BotaniaBlocks.livingrock))
                    .where('F', Predicates.blocks(BotaniaFlowerBlocks.clayconia))
                    .where('#', Predicates.any())
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    LIVINGROCK_CASE,
                    GTCEu.id("block/machines/ore_washer")))
            .tooltips(Component.translatable("gtfrivolous.multiblock.botania.balanced_clayworks.tooltip"))
            .register();

    /* Machines */
    public static final MachineDefinition[] botanicCentrifuge = GTMachineUtils.registerTieredMachines(
            REGISTRATE, "botanic_centrifuge",
            BotanicMachine::new,
            (tier, builder) -> builder
                    .langValue("Botanic Centrifuge %s".formatted(GTValues.LVT[tier]))
                    .editableUI(BotanicMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id("centrifuge"),
                            GTRecipeTypes.CENTRIFUGE_RECIPES))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.CENTRIFUGE_RECIPES)
                    .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                    .workableCasingModel(GTFrivolous.id("block/casings/voltage/lv"), GTCEu.id("block/machines/centrifuge"))
                    //.workableTieredHullModel(GTCEu.id("block/machines/centrifuge"))
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
        consumer.accept(be -> new BotanicHatch.BindableMachineWandHud<>(extractBotanicHatchFromEntity(be)),
                BOTANIA_MANA_HATCH.getBlockEntityType());
    }

    private static ManaPoolBindableMachine extractBotanicMachineFromEntity(BlockEntity entity) {
        return (ManaPoolBindableMachine) ((MetaMachineBlockEntity) entity).getMetaMachine();
    }

    private static BotanicHatch extractBotanicHatchFromEntity(BlockEntity entity) {
        return (BotanicHatch) ((MetaMachineBlockEntity) entity).getMetaMachine();
    }

    private static BlockEntityType<?>[] getBlockEntityTypes(MachineDefinition[] defs) {
        return Stream.of(defs)
                .filter(Objects::nonNull)
                .map(MachineDefinition::getBlockEntityType)
                .toArray(BlockEntityType[]::new);
    }

    public static void init() {}
}
