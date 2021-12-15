package com.chefsdelights.farmersrespite.registry;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import com.umpaz.farmersrespite.FarmersRespite;

public class FRRecipeSerializers {
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmersRespite.MODID);
}
