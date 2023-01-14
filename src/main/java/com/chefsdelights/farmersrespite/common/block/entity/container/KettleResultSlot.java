package com.chefsdelights.farmersrespite.common.block.entity.container;

import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.ItemHandler;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.slot.SlotItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

//@ParametersAreNonnullByDefault
public class KettleResultSlot extends SlotItemHandler {
	public final KettleBlockEntity tileEntity;
	private final Player player;
	private int removeCount;

	public KettleResultSlot(Player player, KettleBlockEntity tile, ItemHandler inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.tileEntity = tile;
		this.player = player;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return false;
	}

	@Override
	@NotNull
	public ItemStack remove(int amount) {
		if (this.hasItem()) {
			this.removeCount += Math.min(amount, this.getItem().getCount());
		}

		return super.remove(amount);
	}

	@Override
	public void onTake(Player thePlayer, ItemStack stack) {
		this.checkTakeAchievements(stack);
		super.onTake(thePlayer, stack);
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
