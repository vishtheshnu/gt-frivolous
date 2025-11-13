package com.vnator.gtfrivolous.common.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import com.vnator.gtfrivolous.GTFrivolous;
import vazkii.botania.common.item.BotaniaItems;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

public class FrivolousMaterials {

    public static Material MANA_STEEL;

    public static void register() {
        MANA_STEEL = new Material.Builder(GTFrivolous.id("manasteel"))
                .ingot()
                .ignoredTagPrefixes()
                .liquid()
                .element(FrivolousElements.ELE_MANA_STEEL)
                .flags(GENERATE_PLATE, GENERATE_ROD, GENERATE_FRAME, GENERATE_BOLT_SCREW)
                .color(0x4679f4).secondaryColor(0x8db2ff)
                .register();
    }

    /**
     * Called as part of PostMaterialEvent to perform material modifications
     */
    public static void modify() {
        TagPrefix.ingot.setIgnored(MANA_STEEL, () -> BotaniaItems.manaSteel);
    }
}
