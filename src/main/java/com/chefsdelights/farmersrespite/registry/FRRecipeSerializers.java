package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.recipe.KettleRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public enum FRRecipeSerializers {
	BREWING_RECIPE_SERIALIZER("brewing", KettleRecipe.class, KettleRecipeSerializer::new);

	private final String pathName;
	private final Class<? extends Recipe<? extends Inventory>> recipeClass;
	private final Supplier<RecipeSerializer<? extends Recipe<? extends Inventory>>> recipeSerializerSupplier;
	private RecipeSerializer<? extends Recipe<? extends Inventory>> serializer;
	private RecipeType<? extends Recipe<? extends Inventory>> type;

	FRRecipeSerializers(String pathName, Class<? extends Recipe<? extends Inventory>> recipeClass, Supplier<RecipeSerializer<? extends Recipe<? extends Inventory>>> recipeSerializerSupplier) {
		this.pathName = pathName;
		this.recipeClass = recipeClass;
		this.recipeSerializerSupplier = recipeSerializerSupplier;
	}

	public static void registerAll() {
		FRRecipeSerializers[] var0 = values();
		int var1 = var0.length;

		for(int var2 = 0; var2 < var1; ++var2) {
			FRRecipeSerializers value = var0[var2];
			Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(FarmersRespite.MOD_ID, value.pathName), value.serializer());
			value.type = RecipeType.register((new Identifier(FarmersRespite.MOD_ID, value.pathName)).toString());
		}

	}

	public RecipeSerializer<? extends Recipe<? extends Inventory>> serializer() {
		if (this.serializer == null) {
			this.serializer = this.recipeSerializerSupplier.get();
		}

		return this.serializer;
	}

	public <T extends Recipe<? extends Inventory>> RecipeType<T> type() {
		return (RecipeType<T>) this.type(this.recipeClass);
	}

	private <T extends Recipe<? extends Inventory>> RecipeType<T> type(Class<T> clazz) {
		if (this.type == null) {
			this.type = RecipeType.register((new Identifier(FarmersRespite.MOD_ID, this.pathName)).toString());
		}

		return (RecipeType<T>) this.type;
	}
}
