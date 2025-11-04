package com.vnator.gtfrivolous.common.data.materials;

import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class FrivolousElements {

    // 🥺 TODO see how I can add emojis or other unicode characters as element names
    public static final Element ELE_MANA_STEEL = createAndRegister(26, 56, -1, null, "Manasteel", "Ms", false);

    public static Element createAndRegister(long protons, long neutrons, long halfLifeSeconds, String decayTo,
                                            String name, String symbol, boolean isIsotope) {
        Element element = new Element(protons, neutrons, halfLifeSeconds, decayTo, name, symbol, isIsotope);
        GTRegistries.ELEMENTS.register(name, element);
        return element;
    }

    public static void init() {}
}
