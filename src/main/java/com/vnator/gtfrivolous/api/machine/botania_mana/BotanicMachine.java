package com.vnator.gtfrivolous.api.machine.botania_mana;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;

import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.internal.ManaNetwork;
import vazkii.botania.common.block.BotaniaBlocks;

import static com.vnator.gtfrivolous.api.machine.botania_mana.ManaEnergyRecipeHandler.DEFAULT_EU_TO_MANA_CONVERSION_RATE;

public class BotanicMachine extends ManaPoolBindableMachine {

    private static final int LINK_RANGE = 10;
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(BotanicMachine.class,
            ManaPoolBindableMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    private int mana;
    @Persisted
    @DescSynced
    private final int maxMana;

    @Nullable
    private ManaEnergyRecipeHandler manaEnergy;

    public BotanicMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
        maxMana = 1000 * (int) Math.pow(4, tier);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    //////////////////////////////////////
    // ****** Recipe Logic ******//
    //////////////////////////////////////
    @Override
    public void onLoad() {
        super.onLoad();

        manaEnergy = new ManaEnergyRecipeHandler(this, DEFAULT_EU_TO_MANA_CONVERSION_RATE);
        addHandlerList(RecipeHandlerList.of(IO.IN, manaEnergy));
        subscribeServerTick(this::drawManaFromPool);

        if (bindingPos == null || !isValidBinding()) {
            setBindingPos(findClosestTarget());
        }
    }

    //////////////////////////////////////
    // ****** Botania Logic ******//
    //////////////////////////////////////

    @Override
    public int getBindingRadius() {
        return LINK_RANGE;
    }

    @Override
    public @Nullable BlockPos findClosestTarget() {
        ManaNetwork network = BotaniaAPI.instance().getManaNetworkInstance();
        var closestPool = network.getClosestPool(getPos(), getLevel(), getBindingRadius());
        return closestPool == null ? null : closestPool.getManaReceiverPos();
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void addMana(int mana) {
        this.mana = Math.min(getMaxMana(), this.mana + mana);
        onChanged();
        notifyBlockUpdate();
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public int getColor() {
        return switch (tier) {
            case 1 -> 0x000FFF; // blue, I guess
            case 2 -> 0x00FF0F; // green, sort of
            default -> 0x000FFF;
        };
    }

    @Override
    public ItemStack getDefaultHudIcon() {
        return new ItemStack(BotaniaBlocks.manaPool.asItem());
    }
}
