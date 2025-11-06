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

import static vazkii.botania.common.item.WandOfTheForestItem.*;

@Debug(export = true)
@Mixin(value = WandOfTheForestItem.class, remap = false)
public class WandOfTheForestMixin {

    /**
     * Rotating a BlockEntity is of higher priority than performing a wand binding,
     * so this mixin is required to allow rebinding botanic machines.
     */
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

        if (player != null) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (WandOfTheForestItem.getBindMode(stack)
                    && tile instanceof MetaMachineBlockEntity machineTile
                    && machineTile.getMetaMachine() instanceof WandBindable bindable){
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
