package com.chefsdelights.farmersrespite.integration.rei;

import com.chefsdelights.farmersrespite.client.gui.KettleScreen;
import com.chefsdelights.farmersrespite.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.integration.rei.brewing.BrewingRecipeCategory;
import com.chefsdelights.farmersrespite.integration.rei.brewing.BrewingRecipeDisplay;
import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.registry.FRRecipeSerializers;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.integration.rei.cooking.CookingRecipeDisplay;
import com.nhoryzon.mc.farmersdelight.integration.rei.cutting.CuttingRecipeDisplay;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.brewing.BrewingRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class FarmersRespiteReiPlugin implements REIClientPlugin {
//	@Override
//	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//		registration.addRecipeTransferHandler(KettleContainer.class, BrewingRecipeCategory.UID, 0, 2, 5, 36);
//	}
	//DisplayCategory<KettleRecipe
	public static final CategoryIdentifier<BrewingRecipeDisplay> BREWING = CategoryIdentifier.of(FarmersDelightMod.MOD_ID, "brewing");

	public static Rectangle centeredIntoRecipeBase(Point origin, int width, int height) {
		return centeredInto(new Rectangle(origin.x, origin.y, 150, 66), width, height);
	}

	public static Rectangle centeredInto(Rectangle origin, int width, int height) {
		return new Rectangle(origin.x + (origin.width - width) / 2, origin.y + (origin.height - height) / 2, width, height);
	}

	@Override
	public void registerCategories(CategoryRegistry registry) {
		registry.add(
				new BrewingRecipeCategory());
		registry.addWorkstations(BREWING, EntryStacks.of(FRBlocks.KETTLE));
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {
		registry.registerRecipeFiller(KettleRecipe.class, FRRecipeSerializers.BREWING_RECIPE_SERIALIZER.type(), BrewingRecipeDisplay::new);
	}

	@Override
	public void registerScreens(ScreenRegistry registry) {
		registry.registerContainerClickArea(new Rectangle(62, 25, 40, 17), KettleScreen.class, BREWING);
	}
}