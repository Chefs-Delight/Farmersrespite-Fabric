package com.chefsdelights.farmersrespite.common.block.entity.inventory;

import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

public interface ItemHandler extends WorldlyContainer {

    int getContainerSize();

    ItemStack insertItemStack(int slot, ItemStack itemStack, boolean simulate);

    ItemStack extractItemStack(int slot, int amount, boolean simulate);

    int getMaxCountForSlot(int slot);
}