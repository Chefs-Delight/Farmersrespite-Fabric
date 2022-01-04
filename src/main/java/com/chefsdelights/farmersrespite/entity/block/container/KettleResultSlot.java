package com.chefsdelights.farmersrespite.entity.block.container;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import com.umpaz.farmersrespite.tile.KettleTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@ParametersAreNonnullByDefault
public class KettleResultSlot extends SlotItemHandler {
	public final KettleTileEntity tileEntity;
	private final PlayerEntity player;
	private int removeCount;

	public KettleResultSlot(PlayerEntity player, KettleTileEntity tile, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.tileEntity = tile;
		this.player = player;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	@Nonnull
	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.removeCount += Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}

	@Override
	public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
		this.checkTakeAchievements(stack);
		super.onTake(thePlayer, stack);
		return stack;
	}

	@Override
	protected void onQuickCraft(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.checkTakeAchievements(stack);
	}

	@Override
	protected void checkTakeAchievements(ItemStack stack) {
		stack.onCraftedBy(this.player.level, this.player, this.removeCount);

		if (!this.player.level.isClientSide) {
			tileEntity.clearUsedRecipes(this.player);
		}

		this.removeCount = 0;
	}
}
