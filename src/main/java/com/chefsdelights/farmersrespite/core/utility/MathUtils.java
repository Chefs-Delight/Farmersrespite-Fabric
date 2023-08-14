package com.chefsdelights.farmersrespite.core.utility;

import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public final class MathUtils {
    public static final Random RAND = new Random();

    public MathUtils() {
    }

    public static int calcRedstoneFromItemHandler(Container inventory) {
        if (inventory == null) {
            return 0;
        } else {
            int itemCount = 0;
            float f = .0f;

            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (!itemStack.isEmpty()) {
                    f += (float) itemStack.getCount() / (float) Math.min(inventory.getMaxStackSize(), itemStack.getMaxStackSize());
                    itemCount++;
                }
            }

            if (inventory.getContainerSize() > 0) {
                f = f / (float) inventory.getContainerSize();
            }

            return Mth.floor(f * 14.f) + (itemCount > 0 ? 1 : 0);
        }
    }
}