package com.chefsdelights.farmersrespite.entity.block.screen;

import java.util.Objects;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.blocks.KettleBlock;
import com.chefsdelights.farmersrespite.entity.block.KettleBlockEntity;
import com.chefsdelights.farmersrespite.entity.block.container.KettleMealSlot;
import com.chefsdelights.farmersrespite.entity.block.container.KettleResultSlot;
import com.chefsdelights.farmersrespite.registry.FRExtendedScreenTypes;
import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.blocks.KettleBlock;
import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRContainerTypes;
import com.umpaz.farmersrespite.tile.KettleTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class KettleScreenHandler extends ScreenHandler {
	public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = FarmersRespite.id("item/empty_container_slot_bottle");

	public final KettleBlockEntity blockEntity;
	public final ItemStackHandler inventory;
	private final IIntArray kettleData;
	private final IWorldPosCallable canInteractWithCallable;

	//	public KettleScreenHandler(final int windowId, final PlayerInventory playerInventory, final KettleTileEntity blockEntity, IIntArray kettleDataIn) {
	public KettleScreenHandler(int windowId, PlayerInventory playerInventory, KettleBlockEntity blockEntity, PropertyDelegate kettleDataIn) {
		super(FRExtendedScreenTypes.KETTLE, windowId);
		this.blockEntity = blockEntity;
		this.inventory = blockEntity.getInventory();
		this.kettleData = kettleDataIn;
		this.canInteractWithCallable = IWorldPosCallable.create(blockEntity.getLevel(), blockEntity.getBlockPos());

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

		// Drink Display
		this.addSlot(new KettleMealSlot(inventory, 2, 118, 26));

		// Bottle Input
		this.addSlot(new SlotItemHandler(inventory, 3, 86, 55)
		{
			@OnlyIn(Dist.CLIENT)
			public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(PlayerContainer.BLOCK_ATLAS, EMPTY_CONTAINER_SLOT_BOTTLE);
			}
		});

		// Bowl Output
		this.addSlot(new KettleResultSlot(playerInventory.player, blockEntity, inventory, 4, 118, 55));

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

	private static KettleTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
		Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
		Objects.requireNonNull(data, "data cannot be null");
		final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
		if (tileAtPos instanceof KettleTileEntity) {
			return (KettleTileEntity) tileAtPos;
		}
		throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
	}

	public KettleScreenHandler(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
		this(windowId, playerInventory, getTileEntity(playerInventory, data), new IntArray(4));
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn) {
		return stillValid(canInteractWithCallable, playerIn, FRBlocks.KETTLE.get());
	}

	@Override
	public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
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

	@OnlyIn(Dist.CLIENT)
	public int getBrewProgressionScaled() {
		int i = this.kettleData.get(0);
		int j = this.kettleData.get(1);
		return j != 0 && i != 0 ? i * 40 / j : 0;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isHeated() {
		return this.blockEntity.isHeated();
	}
	
	@OnlyIn(Dist.CLIENT)
	public int waterLevel() {
		BlockState state = this.blockEntity.getLevel().getBlockState(this.blockEntity.getBlockPos());
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
		if (i == 4) {
		return 4;
		}
		return 0;
		}
}
