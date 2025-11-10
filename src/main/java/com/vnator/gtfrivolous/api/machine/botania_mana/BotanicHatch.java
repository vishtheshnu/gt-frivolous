package com.vnator.gtfrivolous.api.machine.botania_mana;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.internal.ManaNetwork;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.helper.MathHelper;

import java.util.Objects;

public class BotanicHatch extends TieredIOPartMachine implements WandBindable, ManaConsumer {

    private static final int BINDING_RADIUS_BASE = 10;
    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            BotanicHatch.class,
            TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @DescSynced
    private final int maxMana;

    @Persisted
    @DescSynced
    private int mana;

    @Persisted
    @DescSynced
    protected @Nullable BlockPos bindingPos;

    public BotanicHatch(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
        maxMana = 1000 * (int) Math.pow(4, tier);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    //////////////////////////////////////
    // ****** Botania Logic ******//
    //////////////////////////////////////

    @Override
    public void onLoad() {
        super.onLoad();
        if (bindingPos == null || !isValidBinding()) {
            setBindingPos(findClosestTarget());
        }
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
    public int getBindingRadius() {
        return BINDING_RADIUS_BASE * tier;
    }

    @Override
    public boolean canSelect(Player player, ItemStack wand, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public boolean bindTo(Player player, ItemStack wand, BlockPos blockPos, Direction side) {
        if (wouldBeValidBinding(blockPos)) {
            setBindingPos(blockPos);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable BlockPos getBinding() {
        return bindingPos;
    }

    public @Nullable BlockPos getBindingPos() {
        return bindingPos;
    }

    public void setBindingPos(@Nullable BlockPos bindingPos) {
        boolean changed = !Objects.equals(this.bindingPos, bindingPos);

        this.bindingPos = bindingPos;

        if (changed) {
            // TODO see if this is enough to perform update
            onChanged();
        }
    }

    public @Nullable ManaPool findBindCandidateAt(BlockPos pos) {
        var level = getLevel();
        if (level == null || pos == null) {
            return null;
        }

        BlockEntity be = level.getBlockEntity(pos);
        return be instanceof ManaPool ? (ManaPool) be : null;
    }

    @Override
    public @Nullable ManaPool findBoundTile() {
        return findBindCandidateAt(bindingPos);
    }

    public boolean wouldBeValidBinding(@Nullable BlockPos pos) {
        var level = getLevel();
        if (level == null || pos == null || !level.isLoaded(pos) ||
                MathHelper.distSqr(getPos(), pos) > (long) getBindingRadius() * getBindingRadius()) {
            return false;
        } else {
            return findBindCandidateAt(pos) != null;
        }
    }

    public boolean isValidBinding() {
        return wouldBeValidBinding(bindingPos);
    }

    public @Nullable BlockPos findClosestTarget() {
        ManaNetwork network = BotaniaAPI.instance().getManaNetworkInstance();
        var closestPool = network.getClosestPool(getPos(), getLevel(), getBindingRadius());
        return closestPool == null ? null : closestPool.getManaReceiverPos();
    }

    public int getColor() {
        return 0x000FFF;
    }

    public ItemStack getHudIcon() {
        ManaPool boundTile = findBoundTile();
        if (boundTile != null) {
            return new ItemStack(((BlockEntity) boundTile).getBlockState().getBlock().asItem());
        }
        return new ItemStack(BotaniaBlocks.manaPool.asItem());
    }

    public static class BindableMachineWandHud<F extends BotanicHatch> implements WandHUD {

        protected final F flower;

        public BindableMachineWandHud(F flower) {
            this.flower = flower;
        }

        public void renderHUD(GuiGraphics gui, Minecraft mc, int minLeft, int minRight, int minDown) {
            String name = I18n.get(flower.getDefinition().getName());
            int color = flower.getColor();

            int centerX = mc.getWindow().getGuiScaledWidth() / 2;
            int centerY = mc.getWindow().getGuiScaledHeight() / 2;
            int left = (Math.max(102, mc.font.width(name)) + 4) / 2;
            // padding + item
            int right = left + 20;

            left = Math.max(left, minLeft);
            right = Math.max(right, minRight);

            RenderHelper.renderHUDBox(gui, centerX - left, centerY + 8, centerX + right,
                    centerY + Math.max(30, minDown));

            BotaniaAPIClient.instance().drawComplexManaHUD(gui, color, flower.getMana(), flower.getMaxMana(),
                    name, flower.getHudIcon(), flower.isValidBinding());
        }

        @Override
        public void renderHUD(GuiGraphics gui, Minecraft mc) {
            renderHUD(gui, mc, 0, 0, 0);
        }
    }
}
