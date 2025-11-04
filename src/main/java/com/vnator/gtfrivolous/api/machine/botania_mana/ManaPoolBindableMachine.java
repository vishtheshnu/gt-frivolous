package com.vnator.gtfrivolous.api.machine.botania_mana;

import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
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
import vazkii.botania.api.BotaniaAPIClient;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.common.helper.MathHelper;

import java.util.Objects;

public abstract class ManaPoolBindableMachine extends SimpleTieredMachine implements WandBindable {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(ManaPoolBindableMachine.class,
            SimpleTieredMachine.MANAGED_FIELD_HOLDER);

    @Persisted @DescSynced
    protected @Nullable BlockPos bindingPos;

    public ManaPoolBindableMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, GTMachineUtils.defaultTankSizeFunction, args);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    //////////////////////////////////////
    // ****** Botania Logic ******//
    //////////////////////////////////////

    public abstract int getBindingRadius();

    /**
     * Returns the BlockPos of the nearest target within the binding radius, or `null` if there aren't any.
     */
    public abstract @Nullable BlockPos findClosestTarget();

    public abstract int getMana();

    public abstract void addMana(int mana);

    public abstract int getMaxMana();

    public abstract int getColor();

    public abstract ItemStack getDefaultHudIcon();

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

    public @Nullable ManaPool findBoundTile() {
        return findBindCandidateAt(bindingPos);
    }

    public boolean wouldBeValidBinding(@Nullable BlockPos pos) {
        var level = getLevel();
        if (level == null || pos == null || !level.isLoaded(pos) || MathHelper.distSqr(getPos(), pos) > (long) getBindingRadius() * getBindingRadius()) {
            return false;
        } else {
            return findBindCandidateAt(pos) != null;
        }
    }

    public boolean isValidBinding() {
        return wouldBeValidBinding(bindingPos);
    }

    @Override
    public boolean canSelect(Player player, ItemStack itemStack, BlockPos blockPos, Direction direction) {
        return true;
    }

    @Override
    public boolean bindTo(Player player, ItemStack itemStack, BlockPos blockPos, Direction direction) {
        if (wouldBeValidBinding(blockPos)) {
            setBindingPos(blockPos);
            return true;
        }
        return false;
    }

    @Override
    public @Nullable BlockPos getBinding() {
        //Used for Wand of the Forest overlays; only return the binding if it's valid.
        return isValidBinding() ? bindingPos : null;
    }

    public ItemStack getHudIcon() {
        ManaPool boundTile = findBoundTile();
        if (boundTile != null) {
            return new ItemStack(((BlockEntity) boundTile).getBlockState().getBlock().asItem());
        }
        return getDefaultHudIcon();
    }

    public static class BindableFlowerWandHud<F extends ManaPoolBindableMachine> implements WandHUD {
        protected final F flower;

        public BindableFlowerWandHud(F flower) {
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

            RenderHelper.renderHUDBox(gui, centerX - left, centerY + 8, centerX + right, centerY + Math.max(30, minDown));

            BotaniaAPIClient.instance().drawComplexManaHUD(gui, color, flower.getMana(), flower.getMaxMana(),
                    name, flower.getHudIcon(), flower.isValidBinding());
        }

        @Override
        public void renderHUD(GuiGraphics gui, Minecraft mc) {
            renderHUD(gui, mc, 0, 0, 0);
        }
    }

}
