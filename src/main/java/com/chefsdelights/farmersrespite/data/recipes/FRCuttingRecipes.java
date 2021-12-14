package com.umpaz.farmersrespite.data.recipes;

import java.util.function.Consumer;

import com.umpaz.farmersrespite.data.builder.FRCuttingBoardRecipeBuilder;
import com.umpaz.farmersrespite.registry.FRItems;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import vectorwing.farmersdelight.utils.tags.ForgeTags;

public class FRCuttingRecipes {
	
		public static void register(Consumer<IFinishedRecipe> consumer) {
			cookMiscellaneous(consumer);
		}

		private static void cookMiscellaneous(Consumer<IFinishedRecipe> consumer) {
			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.COFFEE_BERRIES.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.COFFEE_BEANS.get(), 1)
			.build(consumer);		
			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.COFFEE_CAKE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.COFFEE_CAKE_SLICE.get(), 7)
			.build(consumer);	
			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.ROSE_HIP_PIE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.ROSE_HIP_PIE_SLICE.get(), 4)
			.build(consumer);	
			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.ROSE_BUSH), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.ROSE_HIPS.get(), 2)
			.build(consumer);	
		}
	}
