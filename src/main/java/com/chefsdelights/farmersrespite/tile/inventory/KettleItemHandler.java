package com.chefsdelights.farmersrespite.tile.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KettleItemHandler implements IItemHandler {
	private static final int SLOTS_INPUT = 2;
	private static final int SLOT_CONTAINER_INPUT = 3;
	private static final int SLOT_MEAL_OUTPUT = 4;
	private final IItemHandler itemHandler;
	private final Direction side;

	public KettleItemHandler(IItemHandler itemHandler, @Nullable Direction side) {
		this.itemHandler = itemHandler;
		this.side = side;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return itemHandler.isItemValid(slot, stack);
	}

	@Override
	public int getSlots() {
		return itemHandler.getSlots();
	}

	@Override
	@Nonnull
	public ItemStack getStackInSlot(int slot) {
		return itemHandler.getStackInSlot(slot);
	}

	@Override
	@Nonnull
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if (side == null || side.equals(Direction.UP)) {
			return slot < SLOTS_INPUT ? itemHandler.insertItem(slot, stack, simulate) : stack;
		} else {
			return slot == SLOT_CONTAINER_INPUT ? itemHandler.insertItem(slot, stack, simulate) : stack;
		}
	}

	@Override
	@Nonnull
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (side == null || side.equals(Direction.UP)) {
			return slot < SLOTS_INPUT ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
		} else {
			return slot == SLOT_MEAL_OUTPUT ? itemHandler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return itemHandler.getSlotLimit(slot);
	}
}
