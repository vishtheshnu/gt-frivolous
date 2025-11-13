package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.vnator.gtfrivolous.GTFrivolous;

import java.util.Locale;
import java.util.function.Supplier;

import static com.vnator.gtfrivolous.GTFrivolous.REGISTRATE;

public class FrivolousBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> CreativeModeTabs.GT_FRIVOLOUS);
    }

    public static final BlockEntry<Block> CASING_MANASTEEL = createCasingBlock("manasteel_machine_casing",
            GTFrivolous.id("block/casings/solid/machine_casing_manasteel"));

    public static final BlockEntry<Block> MACHINE_CASING_MAGIC_LV = createMagicMachineCasingBlock(1);

    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return createCasingBlock(name, Block::new, texture, () -> Blocks.IRON_BLOCK,
                () -> RenderType::cutoutMipped);
    }

    public static BlockEntry<Block> createCasingBlock(String name,
                                                      NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                      ResourceLocation texture,
                                                      NonNullSupplier<? extends Block> properties,
                                                      Supplier<Supplier<RenderType>> type) {
        return REGISTRATE.block(name, blockSupplier)
                .initialProperties(properties)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(type)
                .exBlockstate(GTModels.cubeAllModel(texture))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    private static BlockEntry<Block> createMagicMachineCasingBlock(int tier) {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);
        return REGISTRATE
                .block("%s_magic_machine_casing".formatted(tierName), Block::new)
                .lang("%s Magic Machine Casing".formatted(GTValues.VN[tier]))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .blockstate(GTModels.createSidedCasingModel(GTFrivolous.id("block/casings/voltage/"+tierName)))
                .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static void init() {}
}
