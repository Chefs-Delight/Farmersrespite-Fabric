package com.chefsdelights.farmersrespite.data.recipes;

import com.chefsdelights.farmersrespite.data.builder.FRCuttingBoardRecipeBuilder;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.utils.CTags;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.function.Consumer;

public class FRCuttingRecipes {
	public static void register(Consumer<RecipeJsonProvider> exporter) {
		cookMiscellaneous(exporter);
	}

	private static void cookMiscellaneous(Consumer<RecipeJsonProvider> exporter) {
		FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItems(FRItems.COFFEE_BERRIES), Ingredient.fromTag(CTags.TOOLS_KNIVES), FRItems.COFFEE_BEANS, 1)
		.build(exporter);
		FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItems(FRItems.COFFEE_CAKE), Ingredient.fromTag(CTags.TOOLS_KNIVES), FRItems.COFFEE_CAKE_SLICE, 7)
		.build(exporter);
		FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItems(FRItems.ROSE_HIP_PIE), Ingredient.fromTag(CTags.TOOLS_KNIVES), FRItems.ROSE_HIP_PIE_SLICE, 4)
		.build(exporter);
		FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.ofItems(Items.ROSE_BUSH), Ingredient.fromTag(CTags.TOOLS_KNIVES), FRItems.ROSE_HIPS, 2)
		.build(exporter);
	}
}