package com.vnator.gtfrivolous.common.data.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;

import com.vnator.gtfrivolous.common.data.FrivolousBlocks;
import com.vnator.gtfrivolous.common.data.FrivolousMachines;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.vnator.gtfrivolous.common.data.materials.FrivolousMaterials.*;

public enum FrivolousCraftingComponents {

    ;

    // TODO fill this out further as more materials and tiers are implemented
    public static CraftingComponent HULL_PLATE = CraftingComponent.of("frivolous_hull_plate", plate, Wood)
            .add(ULV, plate, Wood)
            .add(LV, plate, WroughtIron)
            .add(MV, plate, MANA_STEEL);

    public static CraftingComponent PLATE = CraftingComponent.of("frivolous_plate", plate, Iron)
            .add(ULV, plate, WroughtIron)
            .add(LV, plate, MANA_STEEL)
            .add(MV, plate, TERRA_STEEL);

    public static CraftingComponent HULL = CraftingComponent
            .of("frivolous_hull", FrivolousMachines.magicHulls[LV].asStack())
            .add(LV, FrivolousMachines.magicHulls[LV].asStack())
            .add(MV, FrivolousMachines.magicHulls[MV].asStack());

    public static CraftingComponent CASING = CraftingComponent
            .of("frivolous_casing", FrivolousBlocks.MACHINE_CASING_MAGIC_LV.asStack())
            .add(LV, FrivolousBlocks.MACHINE_CASING_MAGIC_LV.asStack())
            .add(MV, FrivolousBlocks.MACHINE_CASING_MAGIC_MV.asStack());
}
