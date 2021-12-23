package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;

public class FRRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registry.RECIPE_SERIALIZER, FarmersRespite.MOD_ID);
}
