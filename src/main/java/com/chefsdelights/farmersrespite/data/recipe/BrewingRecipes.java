//package com.chefsdelights.farmersrespite.data.recipe;
//
//import com.farmersrespite.core.registry.FRItems;
//import com.farmersrespite.core.tag.FRTags;
//import com.farmersrespite.data.builder.KettleRecipeBuilder;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.world.item.Items;
//import vectorwing.farmersdelight.common.registry.ModItems;
//import vectorwing.farmersdelight.common.tag.ForgeTags;
//
//import java.util.function.Consumer;
//
//public class BrewingRecipes
//{
//
//	public static void register(Consumer<FinishedRecipe> consumer) {
//		cookMiscellaneous(consumer);
//	}
//
//	private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
//		KettleRecipeBuilder.kettleRecipe(FRItems.GREEN_TEA.get())
//		.addIngredient(FRItems.GREEN_TEA_LEAVES.get())
//		.addIngredient(FRItems.GREEN_TEA_LEAVES.get())
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.YELLOW_TEA.get())
//		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
//		.addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.BLACK_TEA.get())
//		.addIngredient(FRItems.BLACK_TEA_LEAVES.get())
//		.addIngredient(FRItems.BLACK_TEA_LEAVES.get())
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.ROSE_HIP_TEA.get())
//		.addIngredient(FRItems.ROSE_HIPS.get())
//		.addIngredient(FRItems.ROSE_HIPS.get())
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.DANDELION_TEA.get())
//		.addIngredient(Items.DANDELION)
//		.addIngredient(FRTags.TEA_LEAVES)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.PURULENT_TEA.get())
//		.addIngredient(Items.NETHER_WART)
//		.addIngredient(Items.FERMENTED_SPIDER_EYE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.COFFEE.get())
//		.addIngredient(FRItems.COFFEE_BEANS.get())
//		.addIngredient(FRItems.COFFEE_BEANS.get())
//		.build(consumer);
//
//		KettleRecipeBuilder.kettleRecipe(ModItems.APPLE_CIDER.get())
//				.addIngredient(Items.APPLE)
//				.addIngredient(Items.SUGAR)
//				.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(ModItems.MELON_JUICE.get())
//		.addIngredient(Items.MELON_SLICE)
//		.addIngredient(Items.SUGAR)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(ModItems.HOT_COCOA.get(), 1, 2400, 0.35F, false, ModItems.MILK_BOTTLE.get())
//		.addIngredient(Items.COCOA_BEANS)
//		.addIngredient(Items.SUGAR)
//		.build(consumer);
//
//
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_GREEN_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.GREEN_TEA.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_YELLOW_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.YELLOW_TEA.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_BLACK_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.BLACK_TEA.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_DANDELION_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.DANDELION_TEA.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_COFFEE.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.COFFEE.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.LONG_APPLE_CIDER.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(ModItems.APPLE_CIDER.get())
//		.addIngredient(ForgeTags.MILK)
//		.build(consumer);
//
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_GREEN_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.GREEN_TEA.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_YELLOW_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.YELLOW_TEA.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_BLACK_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.BLACK_TEA.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_PURULENT_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.PURULENT_TEA.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_ROSE_HIP_TEA.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.ROSE_HIP_TEA.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_COFFEE.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(FRItems.COFFEE.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_APPLE_CIDER.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(ModItems.APPLE_CIDER.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//		KettleRecipeBuilder.kettleRecipe(FRItems.STRONG_MELON_JUICE.get(), 1, 2400, 0.35F, false, Items.GLASS_BOTTLE)
//		.addIngredient(ModItems.MELON_JUICE.get())
//		.addIngredient(Items.HONEY_BOTTLE)
//		.build(consumer);
//	}
//}
