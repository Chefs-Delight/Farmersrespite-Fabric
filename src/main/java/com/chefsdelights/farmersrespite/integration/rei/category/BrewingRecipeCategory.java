package com.chefsdelights.farmersrespite.integration.rei.category;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.integration.rei.REIPlugin;
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
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

//@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingRecipeCategory implements DisplayCategory<BrewingRecipeDisplay> {
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(FarmersRespite.MOD_ID, "textures/gui/rei/kettle_rei.png");

	public Renderer getIcon() {
		return EntryStacks.of(FRBlocks.KETTLE);
	}

	public Component getTitle() {
		return FarmersRespite.i18n("rei.brewing");
	}

	public CategoryIdentifier<? extends BrewingRecipeDisplay> getCategoryIdentifier() {
		return REIPlugin.BREWING;
	}

	public List<Widget> setupDisplay(BrewingRecipeDisplay display, Rectangle bounds) {
		Point origin = bounds.getLocation();
		List<Widget> widgets = new ArrayList<>();
		widgets.add(Widgets.createRecipeBase(bounds));
		Rectangle bgBounds = REIPlugin.centeredIntoRecipeBase(origin, 116, 56);
		widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, bgBounds, 29.0F, 16.0F));
		List<EntryIngredient> ingredientEntries = display.getIngredientEntries();
		if (ingredientEntries != null) {
			for(int i = 0; i < ingredientEntries.size(); ++i) {
				Point slotLoc = new Point(bgBounds.x + 1 + i % 3 * 18, bgBounds.y + 1 + i / 3 * 18);
				widgets.add(Widgets.createSlot(slotLoc).entries(ingredientEntries.get(i)).markInput().disableBackground());
			}
		}

		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 57, bgBounds.y + 39)).entries(display.getContainerOutput()).markInput().disableBackground());
		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 89, bgBounds.y + 8)).entries(display.getOutputEntries().get(0)).markOutput().disableBackground());
		widgets.add(Widgets.createSlot(new Point(bgBounds.x + 89, bgBounds.y + 39)).entries(display.getOutputEntries().get(0)).markOutput().disableBackground());
		widgets.add(Widgets.createTexturedWidget(GUI_TEXTURE, new Rectangle(bgBounds.x + 18, bgBounds.y + 39, 17, 15), 176.0F, 0.0F));
		Arrow cookArrow = Widgets.createArrow(new Point(bgBounds.x + 46, bgBounds.y + 10)).animationDurationTicks(display.getBrewTime());
		widgets.add(cookArrow);
		widgets.add(Widgets.createLabel(new Point(cookArrow.getBounds().x + cookArrow.getBounds().width / 2, cookArrow.getBounds().y - 8), Component.literal(display.getBrewTime() + " t")).noShadow().centered().tooltip(Component.literal("Ticks")).color(ChatFormatting.DARK_GRAY.getColor(), ChatFormatting.GRAY.getColor()));
		return widgets;
	}
}
