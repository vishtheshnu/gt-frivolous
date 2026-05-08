package com.vnator.gtfrivolous.api.machine;

import com.gregtechceu.gtceu.api.misc.EnergyContainerList;

import java.util.List;

/**
 * Zero-size energy container that still returns a max voltage equivalent to the parameter tier.
 * Used by alternative energy hatches in multiblocks that don't accept or store EU
 * Eg. BotanicHatch that runs on Botania Mana
 */
public class DummyEnergyContainer extends EnergyContainerList {

    public DummyEnergyContainer(int tier) {
        super(List.of());
    }
}
