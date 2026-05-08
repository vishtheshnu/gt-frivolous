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

public class FrivolousMaterials {

    public static Material MANA_STEEL;
    public static Material TERRA_STEEL;

    public static Material MAGICAL_BIOMASS;

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
                // .element(FrivolousElements.ELE_MANA_STEEL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW)
                .color(0x336f2e).secondaryColor(0xffffff)
                .register();

        MAGICAL_BIOMASS = new Material.Builder(GTFrivolous.id("magical_biomass"))
                .fluid(FluidStorageKeys.LIQUID, new FluidBuilder()
                        .state(FluidState.LIQUID)
                        .color(0x8aedff))
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
