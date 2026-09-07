package com.panzer.mods.dice_and_delish.client.renderer;

public final class ItemLodState {
    private static volatile boolean pastLod1 = false;

    private ItemLodState() {}

    public static boolean isPastLod1() {
        return pastLod1;
    }

    public static void setPastLod1(boolean value) {
        pastLod1 = value;
    }
}
