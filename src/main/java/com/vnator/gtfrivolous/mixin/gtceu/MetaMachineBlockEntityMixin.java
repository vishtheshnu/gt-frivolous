package com.vnator.gtfrivolous.mixin.gtceu;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import com.vnator.gtfrivolous.api.machine.botania_mana.ManaPoolBindableMachine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.BotaniaForgeClientCapabilities;

@Debug(export = true)
@Mixin(value = MetaMachineBlockEntity.class, remap = false)
public class MetaMachineBlockEntityMixin {

    // Specifically target the getCapability(Machine, Capability, Direction) method
    @Inject(method = "getCapability(Lcom/gregtechceu/gtceu/api/machine/MetaMachine;Lnet/minecraftforge/common/capabilities/Capability;Lnet/minecraft/core/Direction;)Lnet/minecraftforge/common/util/LazyOptional;",
            at = @At("TAIL"),
            cancellable = true)
    private static <T> void injectCapability(MetaMachine machine,
                                             @NotNull Capability<T> cap,
                                             @Nullable Direction side,
                                             CallbackInfoReturnable<LazyOptional<T>> cir) {
        if (cap == BotaniaForgeClientCapabilities.WAND_HUD) {
            if (machine instanceof ManaPoolBindableMachine botaniaMachine) {
                cir.setReturnValue(BotaniaForgeClientCapabilities.WAND_HUD.orEmpty(cap,
                        LazyOptional.of(() -> new ManaPoolBindableMachine.BindableFlowerWandHud<>(botaniaMachine))));
                return;
            }

        }
        cir.setReturnValue(LazyOptional.empty());
    }
}
