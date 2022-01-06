package com.chefsdelights.farmersrespite.data.recipes;

import com.chefsdelights.farmersrespite.data.builder.KettleRecipeBuilder;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.utils.FRTags;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class BrewingRecipes {
	public static final int FAST_COOKING = 200;
	public static final int NORMAL_COOKING = 300;
	public static final int SLOW_COOKING = 400;

	public static void register(Consumer<IFinishedRecipe> consumer) {
		cookMiscellaneous(consumer);
	}

	private static void cookMiscellaneous(Consumer<IFinishedRecipe> consumer) {
		KettleRecipeBuilder.kettleRecipe(FRItems.GREEN_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.GREEN_TEA_LEAVES)
		.addIngredient(FRItems.GREEN_TEA_LEAVES)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.YELLOW_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.BLACK_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.BLACK_TEA_LEAVES)
		.addIngredient(FRItems.BLACK_TEA_LEAVES)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.ROSE_HIP_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.ROSE_HIPS)
		.addIngredient(FRItems.ROSE_HIPS)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.DANDELION_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(Items.DANDELION)
		.addIngredient(FRTags.TEA_LEAVES)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.PURULENT_TEA, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(Items.FERMENTED_SPIDER_EYE)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.COFFEE, 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.COFFEE_BEANS)
		.addIngredient(FRItems.COFFEE_BEANS)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(ItemsRegistry.APPLE_CIDER, 1, NORMAL_COOKING, 0.35F, true)
				.addIngredient(Items.APPLE)
				.addIngredient(Items.SUGAR)
				.build(consumer);
	}
}
