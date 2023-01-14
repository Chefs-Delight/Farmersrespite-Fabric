package com.chefsdelights.farmersrespite.common.block.entity.inventory;

import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.ItemStackHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public class KettlePotInventory extends ItemStackHandler {
    private static final int SLOTS_INPUT = 2;
    private static final int SLOT_CONTAINER_INPUT = 3;
    private static final int SLOT_MEAL_OUTPUT = 4;
    private final KettleBlockEntity kettleBlockEntity;

    public KettlePotInventory(KettleBlockEntity kettleBlockEntity) {
        super(9);
        this.kettleBlockEntity = kettleBlockEntity;
    }

    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return new int[]{SLOT_MEAL_OUTPUT};
        }

        if (side == Direction.UP) {
            return IntStream.range(0, SLOTS_INPUT).toArray();
        }

        return new int[]{SLOT_CONTAINER_INPUT};
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        if (dir == null || dir.equals(Direction.UP)) {
            return slot < SLOTS_INPUT;
        } else {
            return slot == SLOT_CONTAINER_INPUT;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        if (dir == null || dir.equals(Direction.UP)) {
            return slot < SLOTS_INPUT;
        } else {
            return slot == SLOT_MEAL_OUTPUT;
        }
    }

    protected void onInventorySlotChanged(int slot) {
        if (slot >= 0 && slot < CookingPotBlockEntity.MEAL_DISPLAY_SLOT) {
            this.kettleBlockEntity.setCheckNewRecipe(true);
        }

        this.kettleBlockEntity.inventoryChanged();
    }
}

