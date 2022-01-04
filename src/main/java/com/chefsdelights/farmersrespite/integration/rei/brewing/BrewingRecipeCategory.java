package com.chefsdelights.farmersrespite.integration.rei.brewing;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.integration.rei.FarmersRespiteREIPlugin;
import com.chefsdelights.farmersrespite.registry.FRItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BrewingRecipeCategory implements DisplayCategory<BrewingRecipeDisplay> {
	public static final Identifier GUI_TEXTURE = new Identifier(FarmersRespite.MOD_ID, "textures/gui/jei/kettle_jei.png");

	public BrewingRecipeCategory() {
		// arrow = helper.drawableBuilder(backgroundImage, 176, 15, 24, 17)
		//TODO change this to that |
		//                         V
//		arrow = helper.drawableBuilder(backgroundImage, 176, 15, 40, 17)
//				.buildAnimated(300, IDrawableAnimated.StartDirection.LEFT, false);

		//TODO add this
//		waterBar = helper.createDrawable(backgroundImage, 176, 32, 5, 9);
	}

	@Override
	public Renderer getIcon() {
		return EntryStacks.of(FRItems.KETTLE);
	}

	@Override
	public Text getTitle() {
		return FarmersRespite.i18n("jei.brewing");
	}

	@Override
	public CategoryIdentifier<? extends BrewingRecipeDisplay> getCategoryIdentifier() {
		return FarmersRespiteREIPlugin.BREWING;
	}

	//TODO
	@Override
	public List<Widget> setupDisplay(BrewingRecipeDisplay display, Rectangle bounds) {
		Point origin = bounds.getLocation();
		List<Widget> widgets = new ArrayList();
		widgets.add(Widgets.createRecipeBase(bounds));
		Rectangle bgBounds = FarmersRespiteREIPlugin.centeredIntoRecipeBase(origin, 116, 56);
		widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, bgBounds, 29.0F, 16.0F));
		List<EntryIngredient> ingredientEntries = display.getIngredientEntries();
		if (ingredientEntries != null) {
			for(int i = 0; i < ingredientEntries.size(); ++i) {
				Point slotLoc = new Point(bgBounds.x + 1 + i % 3 * 18, bgBounds.y + 1 + i / 3 * 18);
				widgets.add(Widgets.createSlot(slotLoc).entries(ingredientEntries.get(i)).markInput().disableBackground());
			}
		}

		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 63, bgBounds.y + 39)).entries(display.getContainerOutput()).markInput().disableBackground());
		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 95, bgBounds.y + 12)).entries(display.getOutputEntries().get(0)).markOutput().disableBackground());
		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 95, bgBounds.y + 39)).entries(display.getOutputEntries().get(0)).markOutput().disableBackground());
		widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, new Rectangle(bgBounds.x + 18, bgBounds.y + 39, 17, 15), 176.0F, 0.0F));
		Arrow cookArrow = Widgets.createArrow(new Point(bgBounds.x + 34, bgBounds.y + 10)).animationDurationTicks(display.getCookTime());
		widgets.add(cookArrow);
		widgets.add(Widgets.createLabel(new Point(cookArrow.getBounds().x + cookArrow.getBounds().width / 2, cookArrow.getBounds().y - 8), new LiteralText(display.getCookTime() + " t")).noShadow().centered().tooltipLine("Ticks").color(Formatting.DARK_GRAY.getColorValue(), Formatting.GRAY.getColorValue()));
		return widgets;
	}


//
//	@Override
//	public void setIngredients(KettleRecipe cookingPotRecipe, IIngredients ingredients) {
//		List<Ingredient> inputAndContainer = new ArrayList<>(cookingPotRecipe.getIngredients());
//		inputAndContainer.add(Ingredient.of(cookingPotRecipe.getOutputContainer()));
//
//		ingredients.setInputIngredients(inputAndContainer);
//		ingredients.setOutput(VanillaTypes.ITEM, cookingPotRecipe.getResultItem());
//	}
//
//	@Override
//	public void setRecipe(IRecipeLayout recipeLayout, KettleRecipe recipe, IIngredients ingredients) {
//		final int MEAL_DISPLAY = 2;
//		final int CONTAINER_INPUT = 3;
//		final int OUTPUT = 4;
//		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
//		DefaultedList<Ingredient> recipeIngredients = recipe.getIngredients();
//
//		int borderSlotSize = 18;
//		for (int row = 0; row < 2; ++row) {
//			for (int column = 0; column < 1; ++column) {
//				int inputIndex = row * 1 + column;
//				if (inputIndex < recipeIngredients.size()) {
//					itemStacks.init(inputIndex, true, column * borderSlotSize + 12, row * borderSlotSize);
//					itemStacks.set(inputIndex, Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
//				}
//			}
//		}
//
//		itemStacks.init(MEAL_DISPLAY, false, 88, 9);
//		itemStacks.set(MEAL_DISPLAY, recipe.getResultItem().getStack());
//
//		if (!recipe.getContainer().isEmpty()) {
//			itemStacks.init(CONTAINER_INPUT, false, 56, 38);
//			itemStacks.set(CONTAINER_INPUT, recipe.getOutputContainer());
//		}
//
//		itemStacks.init(OUTPUT, false, 88, 38);
//		itemStacks.set(OUTPUT, recipe.getResultItem().getStack());
//	}
//
//	@Override
//	public void draw(KettleRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
//		heatIndicator.draw(matrixStack, 13, 40);
//		if (recipe.getNeedWater()) {
//			waterBar.draw(matrixStack, 5, 25);
//
//		}
//	}
}