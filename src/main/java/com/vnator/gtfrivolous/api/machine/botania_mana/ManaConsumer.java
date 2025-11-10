package com.vnator.gtfrivolous.api.machine.botania_mana;

import vazkii.botania.api.mana.ManaPool;

/**
 * Custom GT machines implement this if they can consume mana
 */
public interface ManaConsumer {

    int getMana();

    void addMana(int mana);

    int getMaxMana();

    int getBindingRadius();

    ManaPool findBoundTile();

    default void drawManaFromPool() {
        ManaPool pool = findBoundTile();
        if (pool != null) {
            int manaInPool = pool.getCurrentMana();
            int manaMissing = getMaxMana() - getMana();
            int manaToRemove = Math.min(manaMissing, manaInPool);
            pool.receiveMana(-manaToRemove);
            addMana(manaToRemove);
        }
    }
}
