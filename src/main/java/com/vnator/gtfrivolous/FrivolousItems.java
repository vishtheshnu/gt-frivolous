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

    public static final ItemEntry<ComponentItem> LEAF_MESH = REGISTRATE
            .item("leaf_mesh", ComponentItem::create)
            .lang("Leaf Mesh")
            .tag()
            .defaultModel()
            .register();

    // Circuits

    public static final ItemEntry<ComponentItem> LV_BOTANIC_CIRCUIT = REGISTRATE
            .item("circuit_botanic_lv", ComponentItem::create)
            .lang("Botanic Circuit")
            .tag(CustomTags.LV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_botanic_lv.0"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_lv"));
            })))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LV_BOTANIC_PROCESSOR = REGISTRATE
            .item("circuit_botanic_mv", ComponentItem::create)
            .lang("Botanic Processor")
            .tag(CustomTags.MV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_botanic_mv.0"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_mv"));
            })))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LV_SPIRIT_CIRCUIT = REGISTRATE
            .item("circuit_spirit_lv", ComponentItem::create)
            .lang("Spirit Circuit")
            .tag(CustomTags.LV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_spirit"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_lv"));
            })))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LV_SPIRIT_PROCESSOR = REGISTRATE
            .item("circuit_spirit_mv", ComponentItem::create)
            .lang("Spirit Processor")
            .tag(CustomTags.MV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_spirit"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_mv"));
            })))
            .defaultModel()
            .register();

    public static final ItemEntry<ComponentItem> LV_SPIRIT_ASSEMBLY = REGISTRATE
            .item("circuit_spirit_hv", ComponentItem::create)
            .lang("Spirit Assembly")
            .tag(CustomTags.HV_CIRCUITS)
            .onRegister(attach(new TooltipBehavior(tooltips -> {
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_spirit"));
                tooltips.add(Component.translatable("gtfrivolous.lore.circuit_hv"));
            })))
            .defaultModel()
            .register();

    // Circuitry Components

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

    public static final ItemEntry<ComponentItem> MYSTICAL_PETAL_MULCH = REGISTRATE
            .item("mystical_petal_mulch", ComponentItem::create)
            .lang("Mystical Petal Mulch")
            .tag()
            .defaultModel()
            .register();

    public static <T extends IComponentItem> NonNullConsumer<T> attach(IItemComponent components) {
        return item -> item.attachComponents(components);
    }

    public static void init() {}
}
