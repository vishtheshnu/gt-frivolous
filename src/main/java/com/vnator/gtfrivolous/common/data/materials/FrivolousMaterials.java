package com.vnator.gtfrivolous.common.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.BlastProperty;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.FluidState;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import com.vnator.gtfrivolous.GTFrivolous;
import vazkii.botania.common.item.BotaniaItems;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousElements.ELE_OTHER_STEEL;

public class FrivolousMaterials {

    public static Material MANA_STEEL;
    public static Material TERRA_STEEL;
    public static Material OTHER_STEEL;

    public static Material MYSTICAL_BIOMASS;
    public static Material MYSTICAL_BACTERIA;
    public static Material ECTO_PLASTIC;

    public static void register() {
        MANA_STEEL = new Material.Builder(GTFrivolous.id("manasteel"))
                .ingot()
                .ignoredTagPrefixes()
                .liquid()
                .element(FrivolousElements.ELE_MANA_STEEL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW)
                .color(0x4679f4).secondaryColor(0xffffff)
                .register();
        TERRA_STEEL = new Material.Builder(GTFrivolous.id("terrasteel"))
                .dust()
                .blast((builder) -> builder
                        .temp(1700, BlastProperty.GasTier.LOW)
                        .blastStats(120, 800))
                .ignoredTagPrefixes()
                .liquid()
                .formula("MsBeC")
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW)
                .color(0x336f2e).secondaryColor(0xffffff)
                .register();

        OTHER_STEEL = new Material.Builder(GTFrivolous.id("othersteel"))
                .dust()
                .ingot()
                .blast((builder) -> builder
                        .temp(1700, BlastProperty.GasTier.LOW)
                        .blastStats(480, 1000))
                .element(ELE_OTHER_STEEL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW)
                .color(0x3d404a).secondaryColor(0x292c33)
                .register();

        MYSTICAL_BIOMASS = new Material.Builder(GTFrivolous.id("mystical_biomass"))
                .fluid(FluidStorageKeys.LIQUID, new FluidBuilder()
                        .state(FluidState.LIQUID)
                        .color(0x8aedff))
                .register();
        MYSTICAL_BACTERIA = new Material.Builder(GTFrivolous.id("mystical_bacteria"))
                .fluid(FluidStorageKeys.LIQUID, new FluidBuilder()
                        .state(FluidState.LIQUID)
                        .color(0x547a9e))
                .register();
        ECTO_PLASTIC = new Material.Builder(GTFrivolous.id("ecto_plastic"))
                .ingot()
                .flags(GENERATE_PLATE)
                .fluid(FluidStorageKeys.LIQUID, new FluidBuilder()
                        .state(FluidState.LIQUID)
                        .color(0xe6ffff))
                .register();
    }

    /**
     * Called as part of PostMaterialEvent to perform material modifications
     */
    public static void modify() {
        TagPrefix.ingot.setIgnored(MANA_STEEL, () -> BotaniaItems.manaSteel);
        TagPrefix.ingot.setIgnored(TERRA_STEEL, () -> BotaniaItems.terrasteel);
    }
}
