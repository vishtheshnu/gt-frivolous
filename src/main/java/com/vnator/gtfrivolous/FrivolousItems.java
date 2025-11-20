package com.vnator.gtfrivolous;

import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.IComponentItem;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.network.chat.Component;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.vnator.gtfrivolous.common.data.CreativeModeTabs;

import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

public class FrivolousItems {

    static {
        REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    public static final ItemEntry<ComponentItem> LV_BOTANIC_CIRCUIT = REGISTRATE
            .item("circuit_botanic_lv", ComponentItem::create)
            .lang("LV Botanic Circuit")
            .tag(CustomTags.LV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_botanic_lv.0"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_botanic_lv.1"));
            })))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LIVINGWOOD_CIRCUIT_BOARD = REGISTRATE
            .item("livingwood_circuit_board", ComponentItem::create)
            .lang("Livingwood Circuit Board")
            .tag()
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LIVINGWOOD_PRINTED_CIRCUIT_BOARD = REGISTRATE
            .item("livingwood_printed_circuit_board", ComponentItem::create)
            .lang("Livingwood Printed Circuit Board")
            .tag()
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> MANAGLASS_VACUUM_TUBE = REGISTRATE
            .item("managlass_vacuum_tube", ComponentItem::create)
            .lang("Managlass Vacuum Tube")
            .tag()
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> MANAGLASS_TUBE = REGISTRATE
            .item("managlass_tube", ComponentItem::create)
            .lang("Managlass Tube")
            .tag()
            .defaultModel()
            .register();

    public static <T extends IComponentItem> NonNullConsumer<T> attach(IItemComponent components) {
        return item -> item.attachComponents(components);
    }

    public static void init() {}
}
