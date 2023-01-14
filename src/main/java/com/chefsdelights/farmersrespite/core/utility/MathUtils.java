package com.chefsdelights.farmersrespite.core.utility;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public final class MathUtils {
    public static final Random RAND = new Random();

    public MathUtils() {
    }

    public static int calcRedstoneFromItemHandler(@Nullable ItemStackHandler handler) {
        if (handler == null) {
            return 0;
        } else {
            int i = 0;
            float f = 0.0F;
            for(int j = 0; j < handler.getSlots(); ++j) {
                ItemStack itemstack = handler.getStackInSlot(j);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)Math.min(handler.getSlotLimit(j), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f /= (float)handler.getSlots();
            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }
}
