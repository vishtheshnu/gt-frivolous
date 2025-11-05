package com.vnator.gtfrivolous.mixin.botania;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.block.Bound;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.common.item.WandOfTheForestItem;

import java.util.Optional;

import static vazkii.botania.common.item.WandOfTheForestItem.getBindingAttempt;

@Debug(export = true)
@Mixin(value = WandOfTheForestItem.class, remap = false)
public class WandOfTheForestMixin {

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At("HEAD"),
            cancellable = true)
    private void injectCapability(UseOnContext ctx,
                                  CallbackInfoReturnable<InteractionResult> cir) {
        Player player = ctx.getPlayer();
        ItemStack stack = ctx.getItemInHand();
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction side = ctx.getClickedFace();
        Optional<BlockPos> boundPos = getBindingAttempt(stack);

        System.out.println("Inside capability!");
        if (player != null && !player.isSecondaryUseActive()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (WandOfTheForestItem.getBindMode(stack) && tile instanceof MetaMachineBlockEntity &&
                    ((MetaMachineBlockEntity) tile).getMetaMachine() instanceof WandBindable bindable) {
                if (player.isShiftKeyDown() && bindable.canSelect(player, stack, pos, side)) {
                    if (boundPos.filter(pos::equals).isPresent()) {
                        WandOfTheForestItem.setBindingAttempt(stack, Bound.UNBOUND_POS);
                    } else {
                        WandOfTheForestItem.setBindingAttempt(stack, pos);
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
