package com.chefsdelights.farmersrespite.common.block.entity.inventory.slot;


import com.chefsdelights.farmersrespite.common.block.entity.inventory.ItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {

    private final ItemHandler itemHandler;
    private final int index;

    public SlotItemHandler(ItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        return itemHandler.canPlaceItem(index, stack);
    }

    @Override
    public int getMaxStackSize() {
        return itemHandler.getMaxCountForSlot(this.index);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);

        ItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getItem(index);

        handler.setItem(index, ItemStack.EMPTY);
        ItemStack remainder = handler.insertItemStack(index, maxAdd, true);
        handler.setItem(index, currentStack);

        return maxInput - remainder.getCount();
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !getItemHandler().extractItemStack(index, 1, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return this.getItemHandler().extractItemStack(index, amount, false);
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }

}
