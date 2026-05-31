package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.electric.HullMachine;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.model.generators.BlockModelBuilder;

import com.klikli_dev.occultism.registry.OccultismBlocks;
import com.vnator.gtfrivolous.GTFrivolous;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicHatch;
import com.vnator.gtfrivolous.api.machine.botania_mana.BotanicMachine;
import com.vnator.gtfrivolous.api.machine.botania_mana.ManaPoolBindableMachine;
import com.vnator.gtfrivolous.api.machine.frivolous.StrainerMachine;
import com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes;
import com.vnator.gtfrivolous.api.machine.multiblock.BotaniaWorkableMultiblockMachine;
import com.vnator.gtfrivolous.api.machine.part.ManaPartAbility;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.BotaniaFlowerBlocks;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

import java.util.*;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.VN;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_ITEM_HATCH;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.casingTextures;
import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

/**
 * Performs registration of this addon's machines
 */
public class FrivolousMachines {

    private static final String BOTANIC_MACHINE_TOOLTIP = "gtfrivolous.machine.botanic";
    private static final ResourceLocation LIVINGROCK_CASE = GTFrivolous.id("block/casings/solid/livingrock");
    private static final ResourceLocation OTHERSTEEL_CASE = GTFrivolous
            .id("block/casings/solid/machine_casing_othersteel");

    static {
        REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    /* Custom Machines */
    public static final MachineDefinition[] STRAINER = GTMachineUtils.registerTieredMachines(
            REGISTRATE, "strainer",
            (handler, tier) -> new StrainerMachine(handler, tier, Blocks.WATER),
            (tier, builder) -> builder
                    .langValue("Strainer")
                    .editableUI(StrainerMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id("strainer"),
                            FrivolousRecipeTypes.STRAINER))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(FrivolousRecipeTypes.STRAINER)
                    .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                    .workableCasingModel(
                            GTFrivolous.id("block/casings/voltage/lv/side"), // TODO replace with wood casing model,
                                                                             // create wood machine textures
                            GTCEu.id("block/machines/sifter"))
                    .tooltips(new Component[] { Component.translatable("gtfrivolous.machine.strainer.tooltip") })
                    .register(),
            1);

    public static final MachineDefinition[] DIRT_SIFTER = GTMachineUtils.registerTieredMachines(
            REGISTRATE, "dirt_sifter",
            (handler, tier) -> new StrainerMachine(handler, tier, Blocks.DIRT),
            (tier, builder) -> builder
                    .langValue("Dirt Sifter")
                    .editableUI(StrainerMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id("dift_sifter"),
                            FrivolousRecipeTypes.DIRT_SIFTER))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(FrivolousRecipeTypes.DIRT_SIFTER)
                    .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                    .workableCasingModel(
                            GTFrivolous.id("block/casings/voltage/lv/side"), // TODO replace with wood casing model,
                                                                             // create wood machine textures
                            GTCEu.id("block/machines/forge_hammer"))
                    .tooltips(new Component[] { Component.translatable("gtfrivolous.machine.dirt_sifter.tooltip") })
                    .register(),
            1);

    /* Hatches */
    public static final MachineDefinition BOTANIA_MANA_HATCH = REGISTRATE
            .machine("mana_hatch", (holder) -> new BotanicHatch(holder, GTValues.LV, IO.IN, 2))
            .langValue("Mana Hatch")
            .rotationState(RotationState.ALL)
            .tier(GTValues.LV)
            .modelProperty(IS_FORMED, false)
            .colorOverlayTieredHullModel(GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"), null,
                    GTCEu.id("block/overlay/machine/" + OVERLAY_ITEM_HATCH))
            .abilities(ManaPartAbility.MANA_HATCH, PartAbility.INPUT_ENERGY)
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

    public static final MultiblockMachineDefinition OCCULTISM_SPIRIT_INFUSER = REGISTRATE
            .multiblock("occultism_spirit_infuser", BotaniaWorkableMultiblockMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .appearanceBlock(OccultismBlocks.OTHERSTONE)
            .recipeType(FrivolousRecipeTypes.OCCULTISM_MICRO_RITUAL_CHAMBER)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("###S###", "###P###", "###S###", "###R###")
                    .aisle("##SSS##", "##P#P##", "##SSS##", "##VCV##")
                    .aisle("#SSSSS#", "#P###P#", "#SSSSS#", "#VYWYV#")
                    .aisle("SSSSSSS", "P#####P", "SSSSSSS", "RCW#WCR")
                    .aisle("#SSSSS#", "#P###P#", "#SSSSS#", "#VYWYV#")
                    .aisle("##SSS##", "##P#P##", "##SSS##", "##VCV##")
                    .aisle("###S###", "###~###", "###S###", "###R###")
                    .where('#', Predicates.any())
                    .where('S', Predicates.blocks(OccultismBlocks.OTHERSTONE.get()))
                    .where('P', Predicates.blocks(OccultismBlocks.OTHERSTONE.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setPreviewCount(1))
                            .or(Predicates.abilities(ManaPartAbility.MANA_HATCH).setPreviewCount(1)))
                    .where('~', Predicates.controller(Predicates.blocks(definition.getBlock())))
                    .where('R', Predicates.blocks(OccultismBlocks.CHALK_GLYPH_RED.get()))
                    .where('V', Predicates.blocks(OccultismBlocks.CHALK_GLYPH_PURPLE.get()))
                    .where('Y', Predicates.blocks(OccultismBlocks.CHALK_GLYPH_GOLD.get()))
                    .where('W', Predicates.blocks(OccultismBlocks.CHALK_GLYPH_WHITE.get()))
                    .where('C', Predicates.blocks(OccultismBlocks.SPIRIT_ATTUNED_CRYSTAL.get()))
                    .build())
            .model(GTMachineModels.createWorkableCasingMachineModel(
                    OTHERSTEEL_CASE,
                    GTCEu.id("block/machines/ore_washer")))
            .tooltips(Component.translatable("gtfrivolous.multiblock.occultism.spirit_infuser.tooltip"))
            .register();

    /* GT Machine Variants */
    public static final MachineDefinition[] magicHulls = GTMachineUtils.registerTieredMachines(
            REGISTRATE, "magic_hull", HullMachine::new,
            (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .model(createOverlayTieredHullMachineModel(GTCEu.id("block/machine/part/hull"), tier))
                    .modelProperty(IS_FORMED, false)
                    .langValue("%s §fMagical Machine Hull".formatted(VN[tier].toLowerCase(Locale.ROOT)))
                    .tooltips(Component.translatable("gtceu.machine.hull.tooltip"))
                    .register(),
            1, 2);

    public static final MachineDefinition[] botanicCentrifuge = generateBotanicMachines("centrifuge",
            GTRecipeTypes.CENTRIFUGE_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicAlloySmelter = generateBotanicMachines("alloy_smelter",
            GTRecipeTypes.ALLOY_SMELTER_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicAssembler = generateBotanicMachines("assembler",
            GTRecipeTypes.ASSEMBLER_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicBender = generateBotanicMachines("bender",
            GTRecipeTypes.BENDER_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicChemicalReactor = generateBotanicMachines("chemical_reactor",
            GTRecipeTypes.CHEMICAL_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicCompressor = generateBotanicMachines("compressor",
            GTRecipeTypes.COMPRESSOR_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicElectrolyzer = generateBotanicMachines("electrolyzer",
            GTRecipeTypes.ELECTROLYZER_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicFermenter = generateBotanicMachines("fermenter",
            GTRecipeTypes.FERMENTING_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicLathe = generateBotanicMachines("lathe", GTRecipeTypes.LATHE_RECIPES,
            1, 2);
    public static final MachineDefinition[] botanicMixer = generateBotanicMachines("mixer", GTRecipeTypes.MIXER_RECIPES,
            1, 2);
    public static final MachineDefinition[] botanicPolarizer = generateBotanicMachines("polarizer",
            GTRecipeTypes.POLARIZER_RECIPES, 1, 2);
    public static final MachineDefinition[] botanicWiremill = generateBotanicMachines("wiremill",
            GTRecipeTypes.WIREMILL_RECIPES, 1, 2);

    public static final List<MachineDefinition[]> botanicMachines = List.of(
            botanicCentrifuge, botanicAlloySmelter, botanicAssembler, botanicBender, botanicChemicalReactor,
            botanicCompressor, botanicElectrolyzer, botanicFermenter, botanicLathe, botanicMixer,
            botanicPolarizer, botanicWiremill);

    public static Component[] tooltipsBotanic(int tier, GTRecipeType recipeType, boolean input) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents.add(Component.translatable(BOTANIC_MACHINE_TOOLTIP));
        return tooltipComponents.toArray(Component[]::new);
    }

    public static void registerBotaniaWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
        botanicMachines.forEach(botanicMachine -> consumer
                .accept(be -> new ManaPoolBindableMachine.BindableMachineWandHud<>(extractBotanicMachineFromEntity(be)),
                        getBlockEntityTypes(botanicMachine)));

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

    private static MachineBuilder.ModelInitializer createOverlayTieredHullMachineModel(ResourceLocation overlayModel,
                                                                                       int tier) {
        return (ctx, prov, builder) -> {
            BlockModelBuilder model = prov.models().nested()
                    .parent(prov.models().getExistingFile(overlayModel));
            casingTextures(model, GTFrivolous.id("block/casings/voltage/" + VN[tier].toLowerCase(Locale.ROOT) + "/"));
            builder.forAllStatesModels(state -> model);

            builder.addReplaceableTextures("bottom", "top", "side");
        };
    }

    private static MachineDefinition[] generateBotanicMachines(String name, GTRecipeType recipeType, int... tiers) {
        String caps = Arrays.stream(name.split("_"))
                .map(str -> str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1))
                .reduce("", (a, b) -> a + " " + b)
                .trim();
        return GTMachineUtils.registerTieredMachines(
                REGISTRATE, "botanic_" + name,
                BotanicMachine::new,
                (tier, builder) -> builder
                        .tier(tier)
                        .langValue("Botanic %s %s".formatted(caps, GTValues.LVT[tier]))
                        .editableUI(BotanicMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name),
                                recipeType))
                        .rotationState(RotationState.NON_Y_AXIS)
                        .recipeType(recipeType)
                        .recipeModifier(GTRecipeModifiers.OC_NON_PERFECT)
                        .workableCasingModel(
                                GTFrivolous.id("block/casings/voltage/" + VN[tier].toLowerCase(Locale.ROOT) + "/side"),
                                GTCEu.id("block/machines/" + name))
                        .tooltips(tooltipsBotanic(tier, recipeType, true))
                        .register(),
                tiers);
    }

    public static void init() {}
}
