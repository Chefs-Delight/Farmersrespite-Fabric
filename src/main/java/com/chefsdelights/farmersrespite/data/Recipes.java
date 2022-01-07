package com.chefsdelights.farmersrespite.data;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.data.recipes.BrewingRecipes;
import com.chefsdelights.farmersrespite.data.recipes.FRCookingRecipes;
import com.chefsdelights.farmersrespite.data.recipes.FRCuttingRecipes;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.utils.CTags;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class Recipes extends FabricRecipesProvider {
	public Recipes(FabricDataGenerator generator) {
		super(generator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		BrewingRecipes.register(exporter);
		FRCuttingRecipes.register(exporter);
		FRCookingRecipes.register(exporter);
		recipesCrafted(exporter);
	}
	
	private void recipesCrafted(Consumer<RecipeJsonProvider> exporter) {
		ShapelessRecipeJsonFactory.create(FRItems.COFFEE_CAKE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
				.input(FRItems.COFFEE_CAKE_SLICE)
		.criterion("has_cake_slice", InventoryChangedCriterion.Conditions.items(FRItems.COFFEE_CAKE_SLICE))
		.offerTo(exporter, new Identifier(FarmersRespite.MOD_ID, "coffee_cake_from_slices"));
		ShapelessRecipeJsonFactory.create(FRItems.BLACK_COD)
		.input(CTags.COOKED_FISHES_COD)
		.input(FRItems.BLACK_TEA_LEAVES)
		.input(Items.BOWL)
		.input(CTags.CROPS_CABBAGE)
		.input(CTags.CROPS_RICE)
		.criterion("has_cod", InventoryChangedCriterion.Conditions.items(Items.COOKED_COD))
		.offerTo(exporter);
		ShapelessRecipeJsonFactory.create(Items.RED_DYE)
		.input(FRItems.COFFEE_BERRIES)
		.criterion("has_berries", InventoryChangedCriterion.Conditions.items(FRItems.COFFEE_BERRIES))
		.offerTo(exporter);
		
		ShapedRecipeJsonFactory.create(FRItems.KETTLE)
		.pattern("bSb")
		.pattern("iWi")
		.pattern("iii")
		.input('b', Items.STICK)
		.input('i', Items.BRICK)
		.input('S', Items.LEATHER)
		.input('W', Items.BUCKET)
		.criterion("has_brick", InventoryChangedCriterion.Conditions.items(Items.BRICK))
		.offerTo(exporter);
		ShapedRecipeJsonFactory.create(FRItems.COFFEE_CAKE)
		.pattern("bSb")
		.pattern("xWx")
		.pattern("iii")
		.input('b', CTags.MILK)
		.input('x', FRItems.COFFEE_BEANS)
		.input('i', Items.WHEAT)
		.input('S', Items.SUGAR)
		.input('W', Items.EGG)
		.criterion("has_beans", InventoryChangedCriterion.Conditions.items(FRItems.COFFEE_BEANS))
		.offerTo(exporter);
		ShapedRecipeJsonFactory.create(FRItems.ROSE_HIP_PIE)
		.pattern("bSb")
		.pattern("xxx")
		.pattern("iWi")
		.input('b', CTags.MILK)
		.input('x', FRItems.ROSE_HIPS)
		.input('i', Items.SUGAR)
		.input('S', Items.HONEY_BOTTLE)
		.input('W', ItemsRegistry.PIE_CRUST.get())
		.criterion("has_pie_crust", InventoryChangedCriterion.Conditions.items(ItemsRegistry.PIE_CRUST.get()))
		.offerTo(exporter);
		ShapedRecipeJsonFactory.create(FRItems.GREEN_TEA_COOKIE, 8)
		.pattern("bSb")
		.input('b', Items.WHEAT)
		.input('S', FRItems.GREEN_TEA_LEAVES)
		.criterion("has_leaves", InventoryChangedCriterion.Conditions.items(FRItems.GREEN_TEA_LEAVES))
		.offerTo(exporter);
		ShapedRecipeJsonFactory.create(FRItems.NETHER_WART_SOURDOUGH)
		.pattern("##")
		.pattern("bS")
		.input('#', Items.NETHER_WART)
		.input('b', Items.RED_MUSHROOM)
		.input('S', Items.BROWN_MUSHROOM)
		.criterion("has_wart", InventoryChangedCriterion.Conditions.items(Items.NETHER_WART))
		.offerTo(exporter);
		ShapedRecipeJsonFactory.create(FRItems.ROSE_HIP_PIE)
		.pattern("##")
		.pattern("##")
		.input('#', FRItems.ROSE_HIP_PIE_SLICE)
		.criterion("has_rose_hip_pie_slice", InventoryChangedCriterion.Conditions.items(FRItems.ROSE_HIP_PIE_SLICE))
		.offerTo(exporter, new Identifier(FarmersRespite.MOD_ID, "rose_hip_pie_from_slices"));
	}
}