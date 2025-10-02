package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.common.data.GTCreativeModeTabs;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.vnator.gtfrivolous.GTFrivolous;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

public class CreativeModeTabs {
    public static RegistryEntry<CreativeModeTab> GT_FRIVOLOUS = REGISTRATE.defaultCreativeTab(GTFrivolous.MOD_ID,
            builder -> builder
                    .displayItems(new GTCreativeModeTabs.RegistrateDisplayItemsGenerator(GTFrivolous.MOD_ID, REGISTRATE))
                    .title(REGISTRATE.addLang("itemGroup", GTFrivolous.id("creative_tab"), "GT Frivolous"))
                    .icon(() -> new ItemStack(Items.FISHING_ROD))
                    .build())
            .register();

    public static void init() {}
}
