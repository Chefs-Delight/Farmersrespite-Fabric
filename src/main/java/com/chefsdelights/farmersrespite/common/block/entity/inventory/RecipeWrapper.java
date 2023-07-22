package com.chefsdelights.farmersrespite.common.block.entity.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RecipeWrapper implements Container {
    protected final ItemHandler inventory;

    public RecipeWrapper(ItemHandler inventory) {
        this.inventory = inventory;
    }

    public int getContainerSize() {
        return this.inventory.getContainerSize();
    }

    public ItemStack getItem(int slot) {
        return this.inventory.getItem(slot);
    }

    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack itemStack = this.getItem(slot);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.setItem(slot, ItemStack.EMPTY);
            return itemStack;
        }
    }

    public ItemStack removeItem(int slot, int count) {
        ItemStack itemStack = this.inventory.getItem(slot);
        return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack.split(count);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public boolean isEmpty() {
        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            if (!this.inventory.getItem(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        return this.inventory.canPlaceItem(slot, itemStack);
    }

    public void clearContent() {
        for(int i = 0; i < this.inventory.getContainerSize(); ++i) {
            this.inventory.setItem(i, ItemStack.EMPTY);
        }

    }

    public int getMaxStackSize() {
        return 0;
    }

    public boolean stillValid(Player player) {
        return false;
    }

    public void setChanged() {
    }

    public void startOpen(Player player) {
    }

    public void stopOpen(Player player) {
    }
}
