package com.chefsdelights.farmersrespite.client.gui;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.entity.block.container.KettleContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.nhoryzon.mc.farmersdelight.entity.block.screen.CookingPotScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class KettleScreen extends HandledScreen<CookingPotScreenHandler> { //HandledScreen<KettleScreenHandler>
	private static final Identifier BACKGROUND_TEXTURE = new Identifier(FarmersRespite.MOD_ID, "textures/gui/kettle.png");
	private static final Rectangle HEAT_ICON = new Rectangle(41, 55, 17, 15);
	private static final Rectangle PROGRESS_ARROW = new Rectangle(62, 25, 0, 17);
	private static final Rectangle WATER_BAR1 = new Rectangle(34, 41, 5, 9);
	private static final Rectangle WATER_BAR2 = new Rectangle(34, 33, 5, 8);
	private static final Rectangle WATER_BAR3 = new Rectangle(34, 25, 5, 8);
	private static final Rectangle WATER_BAR4 = new Rectangle(34, 17, 5, 8);
	
	public KettleScreen(KettleContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.leftPos = 0;
		this.topPos = 0;
		this.imageWidth = 176;
		this.imageHeight = 166;
		this.titleLabelX = 35;
	}

	@Override
	public void render(MatrixStack ms, final int mouseX, final int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderDrinkDisplayTooltip(ms, mouseX, mouseY);
		this.renderHeatIndicatorTooltip(ms, mouseX, mouseY);
	}

	private void renderHeatIndicatorTooltip(MatrixStack ms, int mouseX, int mouseY) {
		if (this.isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
			List<ITextComponent> tooltip = new ArrayList<>();
			String key = "container.kettle." + (this.menu.isHeated() ? "heated" : "not_heated");
			tooltip.add(FRTextUtils.getTranslation(key, menu));
			this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
		}
	}

	protected void renderDrinkDisplayTooltip(MatrixStack ms, int mouseX, int mouseY) {
		if (this.minecraft != null && this.minecraft.player != null && this.minecraft.player.inventory.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
			if (this.hoveredSlot.index == 2) {
				List<ITextComponent> tooltip = new ArrayList<>();

				ItemStack drinkStack = this.hoveredSlot.getItem();
				tooltip.add(((IFormattableTextComponent) drinkStack.getItem().getDescription()).withStyle(drinkStack.getRarity().color));

				ItemStack containerStack = this.menu.tileEntity.getContainer();
				String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";

				tooltip.add(FRTextUtils.getTranslation("container.kettle.served_on", container).withStyle(TextFormatting.GRAY));

				this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
			} else {
				this.renderTooltip(ms, this.hoveredSlot.getItem(), mouseX, mouseY);
			}
		}
	}

	@Override
	protected void renderLabels(MatrixStack ms, int mouseX, int mouseY) {
		super.renderLabels(ms, mouseX, mouseY);
		this.font.draw(ms, this.inventory.getDisplayName().getString(), 8.0f, (float) (this.imageHeight - 96 + 2), 4210752);
	}

	@Override
	protected void renderBg(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
		// Render UI background
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		if (this.minecraft == null)
			return;

		this.minecraft.getTextureManager().bind(BACKGROUND_TEXTURE);
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
		    	this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 56, WATER_BAR1.width, WATER_BAR1.height);
				}
				if (this.menu.waterLevel() == 2) {
			    this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 56, WATER_BAR1.width, WATER_BAR1.height);
			    this.blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 48, WATER_BAR2.width, WATER_BAR2.height);
				}
				if (this.menu.waterLevel() == 3) {
				this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 56, WATER_BAR1.width, WATER_BAR1.height);
				this.blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 48, WATER_BAR2.width, WATER_BAR2.height);
				this.blit(ms, this.leftPos + WATER_BAR3.x, this.topPos + WATER_BAR3.y, 176, 40, WATER_BAR3.width, WATER_BAR3.height);
				}
				if (this.menu.waterLevel() == 4) {
				this.blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 56, WATER_BAR1.width, WATER_BAR1.height);
				this.blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 48, WATER_BAR2.width, WATER_BAR2.height);
				this.blit(ms, this.leftPos + WATER_BAR3.x, this.topPos + WATER_BAR3.y, 176, 40, WATER_BAR3.width, WATER_BAR3.height);
				this.blit(ms, this.leftPos + WATER_BAR4.x, this.topPos + WATER_BAR4.y, 176, 32, WATER_BAR4.width, WATER_BAR4.height);
			}
	}
}
