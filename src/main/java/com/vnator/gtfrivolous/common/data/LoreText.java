package com.vnator.gtfrivolous.common.data;

import static com.gregtechceu.gtceu.api.GTValues.MAX_PLUS_FORMAT;
import static net.minecraft.ChatFormatting.*;
import static net.minecraft.ChatFormatting.BLUE;
import static net.minecraft.ChatFormatting.BOLD;
import static net.minecraft.ChatFormatting.DARK_AQUA;
import static net.minecraft.ChatFormatting.DARK_GREEN;
import static net.minecraft.ChatFormatting.DARK_PURPLE;
import static net.minecraft.ChatFormatting.DARK_RED;
import static net.minecraft.ChatFormatting.GREEN;
import static net.minecraft.ChatFormatting.LIGHT_PURPLE;
import static net.minecraft.ChatFormatting.RED;
import static net.minecraft.ChatFormatting.YELLOW;

public class LoreText {

    public static final String[] VN;
    public static final String[] PREFIXES;

    public static final String[] VNF = new String[] {
            DARK_GRAY + "ULV",
            AQUA + "LV",
            GREEN + "MV",
            RED + "HV",
            DARK_PURPLE + "EV",
            BLUE + "IV",
            LIGHT_PURPLE + "LuV",
            RED + "ZPM",
            DARK_AQUA + "UV",
            DARK_RED + "UHV",
            GREEN + "UEV",
            DARK_GREEN + "UIV",
            YELLOW + "UXV",
            BLUE.toString() + BOLD + "OpV",
            RED.toString() + BOLD + "MAX",
            MAX_PLUS_FORMAT.apply(1),
            MAX_PLUS_FORMAT.apply(2),
            MAX_PLUS_FORMAT.apply(3),
            MAX_PLUS_FORMAT.apply(4),
            MAX_PLUS_FORMAT.apply(5),
            MAX_PLUS_FORMAT.apply(6),
            MAX_PLUS_FORMAT.apply(7),
            MAX_PLUS_FORMAT.apply(8),
            MAX_PLUS_FORMAT.apply(9),
            MAX_PLUS_FORMAT.apply(10),
            MAX_PLUS_FORMAT.apply(11),
            MAX_PLUS_FORMAT.apply(12),
            MAX_PLUS_FORMAT.apply(13),
            MAX_PLUS_FORMAT.apply(14),
            MAX_PLUS_FORMAT.apply(15),
            MAX_PLUS_FORMAT.apply(16),
    };

    static {
        VN = new String[] { "ULM", "LM", "MM", "HM", "EM", "IM", "LuM", "ZPM", "UM", "UHM", "UEM", "UIM", "UXM", "OpM",
                "MAX" };

        PREFIXES = new String[] { "Magical", "Botanic", "Botanic", "Bloody", "Bloody" };

    }
}
