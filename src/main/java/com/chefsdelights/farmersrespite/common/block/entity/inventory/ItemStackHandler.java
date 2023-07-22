package com.chefsdelights.farmersrespite.common.block.entity.inventory;

import com.nhoryzon.mc.farmersdelight.exception.SlotInvalidRangeException;
import com.nhoryzon.mc.farmersdelight.util.CompoundTagUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ItemStackHandler implements ItemHandler {

    protected NonNullList<ItemStack> inventory;

    public ItemStackHandler() {
        this(1);
    }

    public ItemStackHandler(int inventorySize) {
        inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean canItemStacksStack(ItemStack left, ItemStack right) {
        if (left.isEmpty() || !left.sameItemStackIgnoreDurability(right) || left.hasTag() != right.hasTag()) {
            return false;
        }

        return (!left.hasTag() || left.getTag().equals(right.getTag()));
    }

    public static ItemStack copyStackWithNewSize(ItemStack itemStack, int newSize) {
        if (newSize == 0) {
            return ItemStack.EMPTY;
        }

        ItemStack copy = itemStack.copy();
        copy.setCount(newSize);

        return copy;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        validateSlotIndex(slot);
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return extractItemStack(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = getItem(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        return extractItemStack(slot, stack.getCount(), false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        inventory.set(slot, stack);
        onInventorySlotChanged(slot);
    }

    @Override
    public void setChanged() {
        // Do nothing when the itemstack handler is marked as dirty
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    public ItemStack insertItemStack(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty() || !canPlaceItem(slot, stack)) {
            return stack;
        }

        validateSlotIndex(slot);

        ItemStack invItemStack = inventory.get(slot);
        int limit = getStackLimit(slot, invItemStack);

        if (!invItemStack.isEmpty()) {
            if (!canItemStacksStack(stack, invItemStack)) {
                return stack;
            }

            limit -= invItemStack.getCount();
        }

        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (invItemStack.isEmpty()) {
                inventory.set(slot, reachedLimit ? copyStackWithNewSize(stack, limit) : stack);
            } else {
                invItemStack.grow(reachedLimit ? limit : stack.getCount());
            }
            onInventorySlotChanged(slot);
        }

        return reachedLimit ? copyStackWithNewSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItemStack(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return ItemStack.EMPTY;
        }

        validateSlotIndex(slot);

        ItemStack invItemStack = inventory.get(slot);

        if (invItemStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int nbrToExtract = Math.min(amount, invItemStack.getMaxStackSize());

        if (invItemStack.getCount() <= nbrToExtract) {
            if (!simulate) {
                inventory.set(slot, ItemStack.EMPTY);
                onInventorySlotChanged(slot);

                return invItemStack;
            } else {
                return invItemStack.copy();
            }
        } else {
            if (!simulate) {
                inventory.set(slot, copyStackWithNewSize(invItemStack, invItemStack.getCount() - nbrToExtract));
                onInventorySlotChanged(slot);
            }

            return copyStackWithNewSize(invItemStack, nbrToExtract);
        }
    }

    @Override
    public int getMaxCountForSlot(int slot) {
        return 64;
    }

    public NonNullList<ItemStack> getItems() {
        return inventory;
    }

    protected void onInventoryLoaded() {
        // Do nothing on basic itemstack handler when inventory is loaded
    }

    protected void onInventorySlotChanged(int slot) {
        // Do nothing on basic itemstack handler when inventory slot is changed
    }

    public void setSize(int size) {
        inventory = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= inventory.size())
            throw new SlotInvalidRangeException(slot, inventory.size());
    }

    protected int getStackLimit(int slot, ItemStack stack) {
        return Math.min(getMaxCountForSlot(slot), stack.getMaxStackSize());
    }

    public CompoundTag writeNbt(CompoundTag nbtCompound) {
        ListTag itemListTag = new ListTag();
        for (int i = 0; i < inventory.size(); i++) {
            if (!inventory.get(i).isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt(CompoundTagUtils.TAG_KEY_SLOT, i);
                inventory.get(i).save(itemTag);
                itemListTag.add(itemTag);
            }
        }

        nbtCompound.put(CompoundTagUtils.TAG_KEY_ITEM_LIST, itemListTag);
        nbtCompound.putInt(CompoundTagUtils.TAG_KEY_SIZE, inventory.size());

        return nbtCompound;
    }

    public void readNbt(CompoundTag tag) {
        setSize(tag.contains(CompoundTagUtils.TAG_KEY_SIZE, CompoundTagUtils.TAG_INT) ? tag.getInt(CompoundTagUtils.TAG_KEY_SIZE) : inventory.size());
        ListTag itemListTag = tag.getList(CompoundTagUtils.TAG_KEY_ITEM_LIST, CompoundTagUtils.TAG_COMPOUND);
        for (int i = 0; i < itemListTag.size(); i++) {
            CompoundTag itemTag = itemListTag.getCompound(i);
            int slot = itemTag.getInt(CompoundTagUtils.TAG_KEY_SLOT);
            if (slot >= 0 && slot <= inventory.size()) {
                inventory.set(slot, ItemStack.of(itemTag));
            }
        }

        onInventoryLoaded();
    }
}