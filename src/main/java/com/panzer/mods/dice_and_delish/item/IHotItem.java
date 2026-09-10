package com.panzer.mods.dice_and_delish.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IHotItem {
    boolean isHot(ItemStack stack, Level level);
}
