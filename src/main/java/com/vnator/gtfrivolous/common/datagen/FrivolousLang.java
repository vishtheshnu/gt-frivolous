package com.vnator.gtfrivolous.common.datagen;

import net.minecraft.network.chat.Component;

import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

public class FrivolousLang {

    public static Component MANA_STORED = REGISTRATE.addRawLang("gtfrivolous.multiblock.mana.mana_stored",
            "Mana: %s / %s");
    public static Component PURITY_PROCESSOR_INFO = REGISTRATE
            .addRawLang("gtfrivolous.multiblock.purity_processor.info", "Pure Daisy in Multiblock form");
    public static Component MAGICAL_GREENHOUSE_INFO = REGISTRATE
            .addRawLang("gtfrivolous.multiblock.magical_greenhouse.info", "Unconventional Plant Growth");
    public static Component PURITY_ORECHID_MEGACONVERTER = REGISTRATE
            .addRawLang("gtfrivolous.multiblock.orechid_megaconverter.info", "Magical Void Miner");
    public static Component PURITY_BALANCED_CLAYWORKS = REGISTRATE
            .addRawLang("gtfrivolous.multiblock.balanced_clayworks.info", "Perfectly Balanced Clay");
    public static Component PURITY_PROCESSOR_TOOLTIP = REGISTRATE
            .addRawLang("gtfrivolous.multiblock.botania.purity_processor.tooltip", "Pure Daisy in Multiblock form");

    public static void init() {}
}
