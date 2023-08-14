package com.chefsdelights.farmersrespite.common.block.entity.container;

import com.chefsdelights.farmersrespite.common.block.entity.inventory.ItemHandler;
import com.chefsdelights.farmersrespite.common.block.entity.inventory.slot.SlotItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


public class KettleMealSlot extends SlotItemHandler {
    public KettleMealSlot(ItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return false;
    }
}
