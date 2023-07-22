package com.chefsdelights.farmersrespite.common.block.entity.container;

import com.chefsdelights.farmersrespite.common.block.entity.inventory.ItemHandler;
import com.chefsdelights.farmersrespite.common.block.KettleBlock;
import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.chefsdelights.farmersrespite.common.block.entity.inventory.slot.SlotItemHandler;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRContainerTypes;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class KettleContainer extends AbstractContainerMenu {
	public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation(FarmersRespite.MOD_ID, "item/empty_container_slot_bottle");

	public final KettleBlockEntity tileEntity;
	public final ItemHandler inventory;
	private final ContainerData kettleData;
	private final ContainerLevelAccess canInteractWithCallable;

	public KettleContainer(final int windowId, final Inventory playerInventory, final KettleBlockEntity tileEntity, ContainerData kettleDataIn) {
		super(FRContainerTypes.KETTLE, windowId);
		this.tileEntity = tileEntity;
		this.inventory = tileEntity.getInventory();
		this.kettleData = kettleDataIn;
		this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());

		// Ingredient Slots - 2 Rows x 1 Columns
		int startX = 8;
		int startY = 18;
		int inputStartX = 42;
		int inputStartY = 17;
		int borderSlotSize = 18;
		for (int row = 0; row < 2; ++row) {
			for (int column = 0; column < 1; ++column) {
				this.addSlot(new SlotItemHandler(inventory, (row * 1) + column,
						inputStartX + (column * borderSlotSize),
						inputStartY + (row * borderSlotSize)));
			}
		}

		// Meal Display
		this.addSlot(new KettleMealSlot(inventory, 2, 118, 26));

		// Bottle Input
		this.addSlot(new SlotItemHandler(inventory, 3, 86, 55)
		{

			@Override
			@Environment(EnvType.CLIENT)
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_CONTAINER_SLOT_BOTTLE);
			}
		});

		// Bowl Output
		this.addSlot(new KettleResultSlot(playerInventory.player, tileEntity, inventory, 4, 118, 55));

		// Main Player Inventory
		int startPlayerInvY = startY * 4 + 12;
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * borderSlotSize),
						startPlayerInvY + (row * borderSlotSize)));
			}
		}

		// Hotbar
		for (int column = 0; column < 9; ++column) {
			this.addSlot(new Slot(playerInventory, column, startX + (column * borderSlotSize), 142));
		}

		this.addDataSlots(kettleDataIn);
	}

	private static KettleBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof KettleBlockEntity) {
			return (KettleBlockEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public KettleContainer(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(4));
	}

	@Override
	public boolean stillValid(Player playerIn) {
		return stillValid(canInteractWithCallable, playerIn, FRBlocks.KETTLE);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		int indexMealDisplay = 2;
		int indexContainerInput = 3;
		int indexOutput = 4;
		int startPlayerInv = indexOutput + 1;
		int endPlayerInv = startPlayerInv + 36;
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index == indexOutput) {
				if (!this.moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, true)) {
					return ItemStack.EMPTY;
				}
			} else if (index > indexOutput) {
				if (itemstack1.getItem() == Items.GLASS_BOTTLE && !this.moveItemStackTo(itemstack1, indexContainerInput, indexContainerInput + 1, false)) {
					return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(itemstack1, 0, indexMealDisplay, false)) {
					return ItemStack.EMPTY;
				} else if (!this.moveItemStackTo(itemstack1, indexContainerInput, indexOutput, false)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Environment(EnvType.CLIENT)
	public int getBrewProgressionScaled() {
		int i = this.kettleData.get(0);
		int j = this.kettleData.get(1);
		return j != 0 && i != 0 ? i * 40 / j : 0;
	}

	@Environment(EnvType.CLIENT)
	public boolean isHeated() {
		return this.tileEntity.isHeated();
	}

	@Environment(EnvType.CLIENT)
	public int waterLevel() {
		BlockState state = this.tileEntity.getLevel().getBlockState(this.tileEntity.getBlockPos());
     	int i = state.getValue(KettleBlock.WATER_LEVEL);
		if (i == 1) {
		return 1;
		}
		if (i == 2) {
		return 2;
		}
		if (i == 3) {
		return 3;
		}
		return 0;
	}

	@Environment(EnvType.CLIENT)
	public boolean isWater() {
		return this.waterLevel() > 0;
	}
}
