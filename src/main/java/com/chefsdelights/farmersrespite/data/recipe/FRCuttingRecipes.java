//package com.chefsdelights.farmersrespite.data.recipe;
//
//import com.farmersrespite.core.registry.FRItems;
//import com.farmersrespite.data.builder.FRCuttingBoardRecipeBuilder;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.crafting.Ingredient;
//import vectorwing.farmersdelight.common.tag.ForgeTags;
//
//import java.util.function.Consumer;
//
//public class FRCuttingRecipes {
//
//		public static void register(Consumer<FinishedRecipe> consumer) {
//			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.COFFEE_BERRIES.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.COFFEE_BEANS.get(), 1)
//			.build(consumer);
//			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.COFFEE_CAKE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.COFFEE_CAKE_SLICE.get(), 7)
//			.build(consumer);
//			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(FRItems.ROSE_HIP_PIE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.ROSE_HIP_PIE_SLICE.get(), 4)
//			.build(consumer);
//			FRCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(Items.ROSE_BUSH), Ingredient.of(ForgeTags.TOOLS_KNIVES), FRItems.ROSE_HIPS.get(), 2)
//			.build(consumer);
//		}
//	}
