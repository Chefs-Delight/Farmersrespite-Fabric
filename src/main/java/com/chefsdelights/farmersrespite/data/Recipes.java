//package com.chefsdelights.farmersrespite.data;
//
//import com.farmersrespite.core.FarmersRespite;
//import com.farmersrespite.core.registry.FRItems;
//import com.farmersrespite.data.recipe.BrewingRecipes;
//import com.farmersrespite.data.recipe.FRCookingRecipes;
//import com.farmersrespite.data.recipe.FRCuttingRecipes;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.advancements.critereon.InventoryChangeTrigger;
//import net.minecraft.data.DataGenerator;
//import net.minecraft.data.recipes.FinishedRecipe;
//import net.minecraft.data.recipes.RecipeProvider;
//import net.minecraft.data.recipes.ShapedRecipeBuilder;
//import net.minecraft.data.recipes.ShapelessRecipeBuilder;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.Items;
//import vectorwing.farmersdelight.common.registry.ModItems;
//import vectorwing.farmersdelight.common.tag.ForgeTags;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.function.Consumer;
//
//@ParametersAreNonnullByDefault
//@MethodsReturnNonnullByDefault
//public class Recipes extends RecipeProvider
//{
//	public Recipes(DataGenerator generator) {
//		super(generator);
//	}
//
//	@Override
//	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
//		BrewingRecipes.register(consumer);
//		FRCookingRecipes.register(consumer);
//		FRCuttingRecipes.register(consumer);
//		recipesCrafted(consumer);
//
//	}
//
//	private void recipesCrafted(Consumer<FinishedRecipe> consumer) {
//		ShapelessRecipeBuilder.shapeless(FRItems.COFFEE_CAKE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.requires(FRItems.COFFEE_CAKE_SLICE.get())
//		.unlockedBy("has_cake_slice", InventoryChangeTrigger.TriggerInstance.hasItems(FRItems.COFFEE_CAKE_SLICE.get()))
//		.save(consumer, new ResourceLocation(FarmersRespite.MODID, "coffee_cake_from_slices"));
//		ShapelessRecipeBuilder.shapeless(FRItems.BLACK_COD.get())
//		.requires(ForgeTags.COOKED_FISHES_COD)
//		.requires(FRItems.BLACK_TEA_LEAVES.get())
//		.requires(Items.BOWL)
//		.requires(ForgeTags.CROPS_CABBAGE)
//		.requires(ForgeTags.CROPS_RICE)
//		.unlockedBy("has_cod", InventoryChangeTrigger.TriggerInstance.hasItems(Items.COOKED_COD))
//		.save(consumer);
//		ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
//		.requires(FRItems.COFFEE_BERRIES.get())
//		.unlockedBy("has_berries", InventoryChangeTrigger.TriggerInstance.hasItems(FRItems.COFFEE_BERRIES.get()))
//		.save(consumer);
//
//		ShapedRecipeBuilder.shaped(FRItems.KETTLE.get())
//		.pattern("sls")
//		.pattern("bBb")
//		.pattern("bbb")
//		.define('s', Items.STICK)
//		.define('l', Items.LEATHER)
//		.define('b', Items.COPPER_INGOT)
//		.define('B', Items.BUCKET)
//		.unlockedBy("has_brick", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BRICK))
//		.save(consumer);
//		ShapedRecipeBuilder.shaped(FRItems.COFFEE_CAKE.get())
//		.pattern("msm")
//		.pattern("cec")
//		.pattern("www")
//		.define('m', ForgeTags.MILK)
//		.define('s', Items.SUGAR)
//		.define('c', FRItems.COFFEE_BEANS.get())
//		.define('e', Items.EGG)
//		.define('w', Items.WHEAT)
//		.unlockedBy("has_beans", InventoryChangeTrigger.TriggerInstance.hasItems(FRItems.COFFEE_BEANS.get()))
//		.save(consumer);
//		ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
//		.pattern("mhm")
//		.pattern("rrr")
//		.pattern("sps")
//		.define('m', ForgeTags.MILK)
//		.define('h', Items.HONEY_BOTTLE)
//		.define('r', FRItems.ROSE_HIPS.get())
//		.define('s', Items.SUGAR)
//		.define('p', ModItems.PIE_CRUST.get())
//		.unlockedBy("has_pie_crust", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PIE_CRUST.get()))
//		.save(consumer);
//		ShapedRecipeBuilder.shaped(FRItems.GREEN_TEA_COOKIE.get(), 8)
//		.pattern("wgw")
//		.define('w', Items.WHEAT)
//		.define('g', FRItems.GREEN_TEA_LEAVES.get())
//		.unlockedBy("has_leaves", InventoryChangeTrigger.TriggerInstance.hasItems(FRItems.GREEN_TEA_LEAVES.get()))
//		.save(consumer);
//		ShapedRecipeBuilder.shaped(FRItems.NETHER_WART_SOURDOUGH.get())
//		.pattern("nn")
//		.pattern("rb")
//		.define('n', Items.NETHER_WART)
//		.define('r', Items.RED_MUSHROOM)
//		.define('b', Items.BROWN_MUSHROOM)
//		.unlockedBy("has_wart", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHER_WART))
//		.save(consumer);
//		ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
//		.pattern("rr")
//		.pattern("rr")
//		.define('r', FRItems.ROSE_HIP_PIE_SLICE.get())
//		.unlockedBy("has_rose_hip_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(FRItems.ROSE_HIP_PIE_SLICE.get()))
//		.save(consumer, new ResourceLocation(FarmersRespite.MODID, "rose_hip_pie_from_slices"));
//	}
//}