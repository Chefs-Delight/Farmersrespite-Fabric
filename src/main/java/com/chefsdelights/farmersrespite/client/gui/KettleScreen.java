package com.chefsdelights.farmersrespite.client.gui;

import com.chefsdelights.farmersrespite.common.block.entity.container.KettleContainer;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.utility.FRTextUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class KettleScreen extends AbstractContainerScreen<KettleContainer> {
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(FarmersRespite.MOD_ID, "textures/gui/kettle.png");
	private static final Rectangle HEAT_ICON = new Rectangle(41, 55, 17, 15);
	private static final Rectangle PROGRESS_ARROW = new Rectangle(62, 25, 0, 17);
	private static final Rectangle WATER_BAR1 = new Rectangle(34, 38, 5, 11);
	private static final Rectangle WATER_BAR2 = new Rectangle(34, 28, 5, 10);
	private static final Rectangle WATER_BAR3 = new Rectangle(34, 17, 5, 12);

	public KettleScreen(KettleContainer screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);
		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.titleLabelX = 35;
	}

	@Override
	public void render(PoseStack ms, final int mouseX, final int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderMealDisplayTooltip(ms, mouseX, mouseY);
		this.renderHeatIndicatorTooltip(ms, mouseX, mouseY);
		this.renderWaterBarIndicatorTooltip(ms, mouseX, mouseY);
	}

	private void renderHeatIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
		if (this.isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
			List<Component> tooltip = new ArrayList<>();
			String key = "container.kettle." + (this.menu.isHeated() ? "heated" : "not_heated");
			tooltip.add(FRTextUtils.getTranslation(key, menu));
			this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
		}
	}

	private void renderWaterBarIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
		if (this.isHovering(34, 17, 5, 32, mouseX, mouseY)) {
			List<Component> tooltip = new ArrayList<>();
			MutableComponent key = null; //= this.menu.isWater()
			if (this.menu.waterLevel() == 0) {
				key = FRTextUtils.getTranslation("container.kettle.no_water");
			}
			if (this.menu.waterLevel() == 1) {
				key = FRTextUtils.getTranslation("container.kettle.has_single_water");
			}
			if (this.menu.waterLevel() > 1) {
				key = FRTextUtils.getTranslation("container.kettle.has_many_water", this.menu.waterLevel());
			}
			tooltip.add(key);
			this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
		}
	}

	protected void renderMealDisplayTooltip(PoseStack ms, int mouseX, int mouseY) {
		if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
			if (this.hoveredSlot.index == 2) {
				List<Component> tooltip = new ArrayList<>();
				ItemStack mealStack = this.hoveredSlot.getItem();
				tooltip.add(((MutableComponent) mealStack.getItem().getDescription()).withStyle(mealStack.getRarity().color));
				ItemStack containerStack = this.menu.tileEntity.getMealContainer();
				String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";
				tooltip.add(FRTextUtils.getTranslation("container.kettle.served_on", container).withStyle(ChatFormatting.GRAY));
				this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
			} else {
				this.renderTooltip(ms, this.hoveredSlot.getItem(), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
		super.renderLabels(ms, mouseX, mouseY);
		this.font.draw(ms, this.playerInventoryTitle, 8.0f, this.imageHeight - 96 + 2, 4210752);
	}

	@Override
	protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
		// Render UI background
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		if (this.minecraft == null)
			return;

		RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
		this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

		// Render heat icon
		if (this.menu.isHeated()) {
			this.blit(ms, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 176, 0, HEAT_ICON.width, HEAT_ICON.height);
		}

		// Render progress arrow
		int l = this.menu.getBrewProgressionScaled();
		this.blit(ms, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 15, l + 1, PROGRESS_ARROW.height);

		// Render water bar
		if (this.menu.waterLevel() == 1) {
    	this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
		}
		if (this.menu.waterLevel() == 2) {
	    this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
	    this.blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 43, WATER_BAR2.width, WATER_BAR2.height);
		}
		if (this.menu.waterLevel() == 3) {
		this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
		this.blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 43, WATER_BAR2.width, WATER_BAR2.height);
		this.blit(ms, this.leftPos + WATER_BAR3.x, this.topPos + WATER_BAR3.y, 176, 32, WATER_BAR3.width, WATER_BAR3.height);
		}
	}
}
