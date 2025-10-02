package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.vnator.gtfrivolous.GTFrivolous;

/**
 * Performs registration of this addon's machines
 */
public class Machines {
    static {
        GTFrivolous.REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    // TODO (maybe) custom init factory where I set modifier for frivolous type machine
    public static final MachineDefinition[] frivolousMachineWiremill = GTMachineUtils.registerSimpleMachines(
            GTFrivolous.REGISTRATE, "frivolous_wiremill", GTRecipeTypes.WIREMILL_RECIPES);

    public static void init() {

    }
}
