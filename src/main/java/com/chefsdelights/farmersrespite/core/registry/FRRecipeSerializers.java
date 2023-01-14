package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class FRRecipeSerializers {
    public static final RecipeSerializer<KettleRecipe> KETTLE = registerRecipeSerializer("brewing", KettleRecipe.SERIALIZER);
    public static final RecipeType<KettleRecipe> BREWING = type("brewing", KettleRecipe.TYPE);

    public static <B extends Recipe<?>, T extends RecipeSerializer<B>> T registerRecipeSerializer(String pathName, T recipeSerializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, FarmersRespite.id(pathName), recipeSerializer);
    }

    @SuppressWarnings({"unchecked","unused"})
    private static <T extends Recipe<? extends Container>> RecipeType<T> type(String pathName, RecipeType<? extends Recipe<? extends Container>> type) {
        if (type == null) {
            type = RecipeType.register(FarmersRespite.id(pathName).toString());
        }
        return (RecipeType<T>) type;
    }
}