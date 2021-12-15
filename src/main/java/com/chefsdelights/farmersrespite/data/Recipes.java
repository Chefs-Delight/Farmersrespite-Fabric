package com.chefsdelights.farmersrespite.data;

import java.util.function.Consumer;

import javax.annotation.ParametersAreNonnullByDefault;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.data.recipes.BrewingRecipes;
import com.umpaz.farmersrespite.data.recipes.FRCookingRecipes;
import com.umpaz.farmersrespite.data.recipes.FRCuttingRecipes;
import com.umpaz.farmersrespite.registry.FRItems;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import vectorwing.farmersdelight.registry.ModItems;
import vectorwing.farmersdelight.utils.tags.ForgeTags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Recipes extends RecipeProvider {
	public Recipes(DataGenerator generator) {
		super(generator);
	}

	@Override
	protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
		BrewingRecipes.register(consumer);
		FRCuttingRecipes.register(consumer);
		FRCookingRecipes.register(consumer);
		recipesCrafted(consumer);
	}
	
	private void recipesCrafted(Consumer<IFinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(FRItems.COFFEE_CAKE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.requires(FRItems.COFFEE_CAKE_SLICE.get())
		.unlockedBy("has_cake_slice", InventoryChangeTrigger.Instance.hasItems(FRItems.COFFEE_CAKE_SLICE.get()))
		.save(consumer, new ResourceLocation(FarmersRespite.MODID, "coffee_cake_from_slices"));
		ShapelessRecipeBuilder.shapeless(FRItems.BLACK_COD.get())
		.requires(ForgeTags.COOKED_FISHES_COD)
		.requires(FRItems.BLACK_TEA_LEAVES.get())
		.requires(Items.BOWL)
		.requires(ForgeTags.CROPS_CABBAGE)
		.requires(ForgeTags.CROPS_RICE)
		.unlockedBy("has_cod", InventoryChangeTrigger.Instance.hasItems(Items.COOKED_COD))
		.save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
		.requires(FRItems.COFFEE_BERRIES.get())
		.unlockedBy("has_berries", InventoryChangeTrigger.Instance.hasItems(FRItems.COFFEE_BERRIES.get()))
		.save(consumer);
		
		ShapedRecipeBuilder.shaped(FRItems.KETTLE.get())
		.pattern("bSb")
		.pattern("iWi")
		.pattern("iii")
		.define('b', Items.STICK)
		.define('i', Items.BRICK)
		.define('S', Items.LEATHER)
		.define('W', Items.BUCKET)
		.unlockedBy("has_brick", InventoryChangeTrigger.Instance.hasItems(Items.BRICK))
		.save(consumer);
		ShapedRecipeBuilder.shaped(FRItems.COFFEE_CAKE.get())
		.pattern("bSb")
		.pattern("xWx")
		.pattern("iii")
		.define('b', ForgeTags.MILK)
		.define('x', FRItems.COFFEE_BEANS.get())
		.define('i', Items.WHEAT)
		.define('S', Items.SUGAR)
		.define('W', Items.EGG)
		.unlockedBy("has_beans", InventoryChangeTrigger.Instance.hasItems(FRItems.COFFEE_BEANS.get()))
		.save(consumer);
		ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
		.pattern("bSb")
		.pattern("xxx")
		.pattern("iWi")
		.define('b', ForgeTags.MILK)
		.define('x', FRItems.ROSE_HIPS.get())
		.define('i', Items.SUGAR)
		.define('S', Items.HONEY_BOTTLE)
		.define('W', ModItems.PIE_CRUST.get())
		.unlockedBy("has_pie_crust", InventoryChangeTrigger.Instance.hasItems(ModItems.PIE_CRUST.get()))
		.save(consumer);
		ShapedRecipeBuilder.shaped(FRItems.GREEN_TEA_COOKIE.get(), 8)
		.pattern("bSb")
		.define('b', Items.WHEAT)
		.define('S', FRItems.GREEN_TEA_LEAVES.get())
		.unlockedBy("has_leaves", InventoryChangeTrigger.Instance.hasItems(FRItems.GREEN_TEA_LEAVES.get()))
		.save(consumer);
		ShapedRecipeBuilder.shaped(FRItems.NETHER_WART_SOURDOUGH.get())
		.pattern("##")
		.pattern("bS")
		.define('#', Items.NETHER_WART)
		.define('b', Items.RED_MUSHROOM)
		.define('S', Items.BROWN_MUSHROOM)
		.unlockedBy("has_wart", InventoryChangeTrigger.Instance.hasItems(Items.NETHER_WART))
		.save(consumer);
		ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
		.pattern("##")
		.pattern("##")
		.define('#', FRItems.ROSE_HIP_PIE_SLICE.get())
		.unlockedBy("has_rose_hip_pie_slice", InventoryChangeTrigger.Instance.hasItems(FRItems.ROSE_HIP_PIE_SLICE.get()))
		.save(consumer, new ResourceLocation(FarmersRespite.MODID, "rose_hip_pie_from_slices"));
	}
}
