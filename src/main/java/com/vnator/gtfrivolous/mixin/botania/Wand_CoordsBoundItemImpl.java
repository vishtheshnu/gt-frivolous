package com.vnator.gtfrivolous.mixin.botania;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.block.Bound;
import vazkii.botania.client.core.proxy.ClientProxy;
import vazkii.botania.common.item.WandOfTheForestItem;

@Debug(export = true)
@Mixin(value = WandOfTheForestItem.CoordBoundItemImpl.class, remap = false)
public class Wand_CoordsBoundItemImpl {

    @Inject(
            method = "Lvazkii/botania/common/item/WandOfTheForestItem$CoordBoundItemImpl;getBinding(Lnet/minecraft/world/level/Level;)Lnet/minecraft/core/BlockPos;",
            at = @At("TAIL"),
            cancellable = true)
    private void getBinding_botanicMachine(Level world,
                                           CallbackInfoReturnable<BlockPos> cir) {
        var pos = ClientProxy.INSTANCE.getClientHit();
        if (pos instanceof BlockHitResult bHit && pos.getType() == HitResult.Type.BLOCK) {
            BlockEntity tile = world.getBlockEntity(bHit.getBlockPos());
            if (tile instanceof MetaMachineBlockEntity metaMachineEntity &&
                    metaMachineEntity.getMetaMachine() instanceof Bound boundMachine) {
                cir.setReturnValue(boundMachine.getBinding());
            }
        }
    }
}
