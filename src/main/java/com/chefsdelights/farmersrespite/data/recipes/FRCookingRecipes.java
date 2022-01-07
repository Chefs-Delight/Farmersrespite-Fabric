package com.chefsdelights.farmersrespite.data.recipes;

import com.chefsdelights.farmersrespite.data.builder.FRCookingPotRecipeBuilder;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.utils.CTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class FRCookingRecipes {
	public static final int FAST_COOKING = 100;
	public static final int NORMAL_COOKING = 200;
	public static final int SLOW_COOKING = 400;

	public static void register(Consumer<RecipeJsonProvider> exporter) {
		cookMiscellaneous(exporter);
	}

	private static void cookMiscellaneous(Consumer<RecipeJsonProvider> exporter) {
		FRCookingPotRecipeBuilder.cookingPotRecipe(FRItems.BLAZING_CHILI, 1, NORMAL_COOKING, 0.35F)
		.addIngredient(Items.BLAZE_POWDER)
		.addIngredient(Items.BLAZE_POWDER)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(FRItems.COFFEE_BEANS)
		.addIngredient(CTags.RAW_BEEF)
		.build(exporter);
		FRCookingPotRecipeBuilder.cookingPotRecipe(FRItems.TEA_CURRY, 1, NORMAL_COOKING, 0.35F)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES)
		.addIngredient(CTags.RAW_CHICKEN)
		.addIngredient(CTags.CROPS_CABBAGE)
		.addIngredient(CTags.CROPS_ONION)
		.addIngredient(CTags.CROPS_RICE)
		.build(exporter);
	}
}