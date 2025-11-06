package com.vnator.gtfrivolous.mixin.botania;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.block.Bound;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.item.WandOfTheForestItem;

import java.util.Optional;

import static vazkii.botania.common.item.WandOfTheForestItem.*;

@Debug(export = true)
@Mixin(value = WandOfTheForestItem.class, remap = false)
public class WandOfTheForestMixin {

    @Inject(
            method = "Lvazkii/botania/common/item/WandOfTheForestItem;tryCompleteBinding(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/context/UseOnContext;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private static void tryCompleteBinding_bindBotanicMachine(BlockPos src, ItemStack stack, UseOnContext ctx,
                                                              CallbackInfoReturnable<Boolean> cir) {
        BlockPos dest = ctx.getClickedPos();
        if (!dest.equals(src)) {
            setBindingAttempt(stack, Bound.UNBOUND_POS);

            BlockEntity srcTile = ctx.getLevel().getBlockEntity(src);
            if (srcTile instanceof MetaMachineBlockEntity metaMachineEntity &&
                    metaMachineEntity.getMetaMachine() instanceof WandBindable bindable) {
                if (bindable.bindTo(ctx.getPlayer(), stack, dest, ctx.getClickedFace())) {
                    doParticleBeamWithOffset(ctx.getLevel(), src, dest);
                    setBindingAttempt(stack, Bound.UNBOUND_POS);
                }
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(
            method = "Lvazkii/botania/common/item/WandOfTheForestItem;inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;IZ)V",
            at = @At("HEAD"),
            cancellable = true)
    private void inventoryTick_allowBindBotanicMachine(ItemStack stack, Level world, Entity entity, int slot,
                                                       boolean selected,
                                                       CallbackInfo ci) {
        getBindingAttempt(stack).ifPresent(coords -> {
            BlockEntity tile = world.getBlockEntity(coords);
            if (tile instanceof MetaMachineBlockEntity metaMachineEntity &&
                    metaMachineEntity.getMetaMachine() instanceof WandBindable) {
                ci.cancel();
            }
        });
    }

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void useOn_bindBotanicMachine(UseOnContext ctx,
                                          CallbackInfoReturnable<InteractionResult> cir) {
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction side = ctx.getClickedFace();
        Optional<BlockPos> boundPos = getBindingAttempt(stack);

        System.out.println("Inside capability!");
        if (player != null) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (WandOfTheForestItem.getBindMode(stack))
                if (tile instanceof MetaMachineBlockEntity) {
                    MetaMachine metaMachine = ((MetaMachineBlockEntity) tile).getMetaMachine();
                    if (metaMachine instanceof WandBindable bindable) {
                        if (player.isShiftKeyDown() && bindable.canSelect(player, stack, pos, side)) {
                            if (boundPos.filter(pos::equals).isPresent()) {
                                setBindingAttempt(stack, Bound.UNBOUND_POS);
                            } else {
                                setBindingAttempt(stack, pos);
                            }

                            if (world.isClientSide) {
                                player.playSound(BotaniaSounds.ding, 0.11F, 1F);
                            }

                            cir.setReturnValue(InteractionResult.SUCCESS);
                        }
                    }
                }
        }
    }
}
