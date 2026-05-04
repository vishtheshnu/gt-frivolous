package com.vnator.gtfrivolous.api.machine.frivolous;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;

import net.minecraft.world.level.block.Block;

import java.util.Objects;

public class StrainerMachine extends SimpleTieredMachine {

    private final Block aboveBlock;

    public StrainerMachine(IMachineBlockEntity holder, int tier, Block aboveBlock, Object... args) {
        super(holder, tier, GTMachineUtils.defaultTankSizeFunction, args);
        this.aboveBlock = aboveBlock;
        energyContainer.resetBasicInfo(0, 0, 0, 0, 0);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        subscribeServerTick(() -> setWorkingEnabled(isRequiredBlockAbove()));
    }

    private boolean isRequiredBlockAbove() {
        Block actualBlock = Objects.requireNonNull(getLevel()).getBlockState(getPos().above()).getBlock();
        return actualBlock == aboveBlock;
    }
}
