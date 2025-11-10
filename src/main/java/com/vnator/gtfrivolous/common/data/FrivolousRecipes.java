package com.vnator.gtfrivolous.common.data;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import vazkii.botania.common.block.BotaniaBlocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.vnator.gtfrivolous.api.machine.gtbridge.FrivolousRecipeTypes.*;

public class FrivolousRecipes {

    private static final Map<Item, List<Item>> GREENHOUSE_IO = new HashMap<>();
    private static final Map<Item, List<Material>> ORECHID_IO = new HashMap<>();

    public static void init(Consumer<FinishedRecipe> provider) {
        initConstants();

        BOTANIA_PURITY_PROCESSOR.recipeBuilder("livingrock")
                .inputItems(Items.STONE, 16)
                .outputItems(BotaniaBlocks.livingrock.asItem(), 16)
                .EUt(10)
                .duration(300)
                .save(provider);

        BOTANIA_PURITY_PROCESSOR.recipeBuilder("livingwood")
                .inputItems(TagUtil.createItemTag("logs", true), 16)
                .outputItems(BotaniaBlocks.livingwoodLog.asItem(), 16)
                .EUt(10)
                .duration(300)
                .save(provider);

        for (var greenhouseEntry : GREENHOUSE_IO.entrySet()) {
            Item input = greenhouseEntry.getKey();

            GTRecipeBuilder recipeBuilder = BOTANIA_MAGICAL_GREENHOUSE.recipeBuilder("greenhouse_" + input.toString())
                    .notConsumable(input)
                    .EUt(30)
                    .duration(300);
            for (Item output : greenhouseEntry.getValue()) {
                recipeBuilder.outputItems(output, 16);
            }
            recipeBuilder.save(provider);
        }

        for (var orechidEntry : ORECHID_IO.entrySet()) {
            Item input = orechidEntry.getKey();
            int circuit = 1;
            for (Material output : orechidEntry.getValue()) {
                BOTANIA_ORECHID_MEGACONVERTER.recipeBuilder(input.toString() + "_" + output.getName())
                        .inputItems(input, 1)
                        .outputItems(TagPrefix.rawOre, output, 1)
                        .circuitMeta(circuit)
                        .EUt(22)
                        .duration(2)
                        .save(provider);
                circuit++;
            }
        }

        BOTANIA_BALANCED_CLAYWORKS.recipeBuilder("clay")
                .inputItems(Items.SAND, 16)
                .outputItems(Items.CLAY_BALL, 16)
                .EUt(10)
                .duration(80)
                .save(provider);
    }

    private static void initConstants() {
        GREENHOUSE_IO.put(Items.ACACIA_SAPLING, List.of(Blocks.ACACIA_LOG.asItem()));
        GREENHOUSE_IO.put(Items.BIRCH_SAPLING, List.of(Blocks.BIRCH_LOG.asItem()));
        GREENHOUSE_IO.put(Items.CHERRY_SAPLING, List.of(Blocks.CHERRY_LOG.asItem()));
        GREENHOUSE_IO.put(Items.DARK_OAK_SAPLING, List.of(Blocks.DARK_OAK_LOG.asItem()));
        GREENHOUSE_IO.put(Items.OAK_SAPLING, List.of(Blocks.OAK_LOG.asItem()));
        GREENHOUSE_IO.put(Items.JUNGLE_SAPLING, List.of(Blocks.JUNGLE_LOG.asItem()));
        GREENHOUSE_IO.put(Items.SPRUCE_SAPLING, List.of(Blocks.SPRUCE_LOG.asItem()));
        GREENHOUSE_IO.put(GTBlocks.RUBBER_SAPLING.get().asItem(),
                List.of(GTBlocks.RUBBER_LOG.asItem(), GTItems.STICKY_RESIN.asItem()));
        GREENHOUSE_IO.put(Items.WHEAT_SEEDS, List.of(Items.WHEAT));
        GREENHOUSE_IO.put(Items.BEETROOT_SEEDS, List.of(Items.BEETROOT));
        GREENHOUSE_IO.put(Items.MELON_SEEDS, List.of(Items.MELON));
        GREENHOUSE_IO.put(Items.PUMPKIN_SEEDS, List.of(Items.PUMPKIN));
        GREENHOUSE_IO.put(Items.TORCHFLOWER_SEEDS, List.of(Items.TORCHFLOWER));

        ORECHID_IO.put(Items.STONE, List.of(Iron, Pyrite, Copper, Chalcopyrite));
        ORECHID_IO.put(Items.ANDESITE, List.of(Sphalerite, Pyrite, Sulfur));
        ORECHID_IO.put(Items.GRAVEL, List.of(Coal, Graphite, Diamond));
        ORECHID_IO.put(Items.SAND, List.of(Redstone, Ruby, Cinnabar));
    }
}
