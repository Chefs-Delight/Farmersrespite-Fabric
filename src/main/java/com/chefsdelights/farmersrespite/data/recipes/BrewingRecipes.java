package com.umpaz.farmersrespite.data.recipes;

import java.util.function.Consumer;

import com.umpaz.farmersrespite.data.builder.KettleRecipeBuilder;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.utils.FRTags;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import vectorwing.farmersdelight.registry.ModItems;

public class BrewingRecipes
{
	public static final int FAST_COOKING = 200;
	public static final int NORMAL_COOKING = 300;
	public static final int SLOW_COOKING = 400;

	public static void register(Consumer<IFinishedRecipe> consumer) {
		cookMiscellaneous(consumer);
	}

	private static void cookMiscellaneous(Consumer<IFinishedRecipe> consumer) {
		KettleRecipeBuilder.kettleRecipe(FRItems.GREEN_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.GREEN_TEA_LEAVES.get())
		.addIngredient(FRItems.GREEN_TEA_LEAVES.get())
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.YELLOW_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.BLACK_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.BLACK_TEA_LEAVES.get())
		.addIngredient(FRItems.BLACK_TEA_LEAVES.get())
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.ROSE_HIP_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.ROSE_HIPS.get())
		.addIngredient(FRItems.ROSE_HIPS.get())
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.DANDELION_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(Items.DANDELION)
		.addIngredient(FRTags.TEA_LEAVES)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.PURULENT_TEA.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(Items.NETHER_WART)
		.addIngredient(Items.FERMENTED_SPIDER_EYE)
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(FRItems.COFFEE.get(), 1, NORMAL_COOKING, 0.35F, true)
		.addIngredient(FRItems.COFFEE_BEANS.get())
		.addIngredient(FRItems.COFFEE_BEANS.get())
		.build(consumer);
		KettleRecipeBuilder.kettleRecipe(ModItems.APPLE_CIDER.get(), 1, NORMAL_COOKING, 0.35F, true)
				.addIngredient(Items.APPLE)
				.addIngredient(Items.SUGAR)
				.build(consumer);
	}
}
