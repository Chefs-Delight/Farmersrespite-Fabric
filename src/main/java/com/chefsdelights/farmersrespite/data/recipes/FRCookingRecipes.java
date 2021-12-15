package com.chefsdelights.farmersrespite.data.recipes;

import java.util.function.Consumer;

import com.umpaz.farmersrespite.data.builder.FRCookingPotRecipeBuilder;
import com.umpaz.farmersrespite.registry.FRItems;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import vectorwing.farmersdelight.utils.tags.ForgeTags;

public class FRCookingRecipes {
	public static final int FAST_COOKING = 100;
	public static final int NORMAL_COOKING = 200;
	public static final int SLOW_COOKING = 400;

	public static void register(Consumer<IFinishedRecipe> consumer) {
		cookMiscellaneous(consumer);
	}

	private static void cookMiscellaneous(Consumer<IFinishedRecipe> consumer) {
		FRCookingPotRecipeBuilder.cookingPotRecipe(FRItems.BLAZING_CHILI.get(), 1, NORMAL_COOKING, 0.35F)
		.addIngredient(Items.BLAZE_POWDER)
		.addIngredient(Items.BLAZE_POWDER)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(FRItems.COFFEE_BEANS.get())
		.addIngredient(ForgeTags.RAW_BEEF)
		.build(consumer);
		FRCookingPotRecipeBuilder.cookingPotRecipe(FRItems.TEA_CURRY.get(), 1, NORMAL_COOKING, 0.35F)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
		.addIngredient(ForgeTags.RAW_CHICKEN)
		.addIngredient(ForgeTags.CROPS_CABBAGE)
		.addIngredient(ForgeTags.CROPS_ONION)
		.addIngredient(ForgeTags.CROPS_RICE)
		.build(consumer);
	}
}