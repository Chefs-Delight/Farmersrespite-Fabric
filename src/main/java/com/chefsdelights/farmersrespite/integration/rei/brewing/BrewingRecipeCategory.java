package com.chefsdelights.farmersrespite.integration.rei.brewing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.crafting.KettleRecipe;
import com.umpaz.farmersrespite.registry.FRItems;

import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingRecipeCategory implements IRecipeCategory<KettleRecipe> {
	public static final ResourceLocation UID = new ResourceLocation(FarmersRespite.MODID, "brewing");
	protected final IDrawable heatIndicator;
	protected final IDrawable waterBar;
	protected final IDrawableAnimated arrow;
	private final String title;
	private final IDrawable background;
	private final IDrawable icon;

	public BrewingRecipeCategory(IGuiHelper helper) {
		title = I18n.get(FarmersRespite.MODID + ".jei.brewing");
		ResourceLocation backgroundImage = new ResourceLocation(FarmersRespite.MODID, "textures/gui/jei/kettle_jei.png");
		background = helper.createDrawable(backgroundImage, 29, 16, 117, 57);
		icon = helper.createDrawableIngredient(new ItemStack(FRItems.KETTLE.get()));
		heatIndicator = helper.createDrawable(backgroundImage, 176, 0, 17, 15);
		arrow = helper.drawableBuilder(backgroundImage, 176, 15, 40, 17)
				.buildAnimated(300, IDrawableAnimated.StartDirection.LEFT, false);
		waterBar = helper.createDrawable(backgroundImage, 176, 32, 5, 9);
	}

	@Override
	public ResourceLocation getUid() {
		return UID;
	}

	@Override
	public Class<? extends KettleRecipe> getRecipeClass() {
		return KettleRecipe.class;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setIngredients(KettleRecipe cookingPotRecipe, IIngredients ingredients) {
		List<Ingredient> inputAndContainer = new ArrayList<>(cookingPotRecipe.getIngredients());
		inputAndContainer.add(Ingredient.of(cookingPotRecipe.getOutputContainer()));

		ingredients.setInputIngredients(inputAndContainer);
		ingredients.setOutput(VanillaTypes.ITEM, cookingPotRecipe.getResultItem());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, KettleRecipe recipe, IIngredients ingredients) {
		final int MEAL_DISPLAY = 2;
		final int CONTAINER_INPUT = 3;
		final int OUTPUT = 4;
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

		int borderSlotSize = 18;
		for (int row = 0; row < 2; ++row) {
			for (int column = 0; column < 1; ++column) {
				int inputIndex = row * 1 + column;
				if (inputIndex < recipeIngredients.size()) {
					itemStacks.init(inputIndex, true, column * borderSlotSize + 12, row * borderSlotSize);
					itemStacks.set(inputIndex, Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
				}
			}
		}

		itemStacks.init(MEAL_DISPLAY, false, 88, 9);
		itemStacks.set(MEAL_DISPLAY, recipe.getResultItem().getStack());

		if (!recipe.getOutputContainer().isEmpty()) {
			itemStacks.init(CONTAINER_INPUT, false, 56, 38);
			itemStacks.set(CONTAINER_INPUT, recipe.getOutputContainer());
		}

		itemStacks.init(OUTPUT, false, 88, 38);
		itemStacks.set(OUTPUT, recipe.getResultItem().getStack());
	}

	@Override
	public void draw(KettleRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
		arrow.draw(matrixStack, 33, 9);
		heatIndicator.draw(matrixStack, 13, 40);
		if (recipe.getNeedWater()) {
			waterBar.draw(matrixStack, 5, 25);

		}
	}
}
