package com.chefsdelights.farmersrespite.data.builder;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.crafting.KettleRecipe;

import mezz.jei.api.MethodsReturnNonnullByDefault;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KettleRecipeBuilder {
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Item result;
	private final int count;
	private final int brewingTime;
	private final float experience;
	private final Item container;
	private final boolean needWater;

	private KettleRecipeBuilder(IItemProvider resultIn, int count, int brewingTime, float experience, boolean needWater, @Nullable IItemProvider container) {
		this.result = resultIn.asItem();
		this.count = count;
		this.brewingTime = brewingTime;
		this.experience = experience;
		this.container = container != null ? container.asItem() : null;
		this.needWater = needWater;
	}

	public static KettleRecipeBuilder kettleRecipe(IItemProvider mainResult, int count, int brewingTime, float experience, boolean needWater) {
		return new KettleRecipeBuilder(mainResult, count, brewingTime, experience, needWater, null);
	}

	public static KettleRecipeBuilder kettleRecipe(IItemProvider mainResult, int count, int brewingTime, float experience, boolean needWater, IItemProvider container) {
		return new KettleRecipeBuilder(mainResult, count, brewingTime, experience, needWater, container);
	}

	public KettleRecipeBuilder addIngredient(ITag<Item> tagIn) {
		return this.addIngredient(Ingredient.of(tagIn));
	}

	public KettleRecipeBuilder addIngredient(IItemProvider itemIn) {
		return this.addIngredient(itemIn, 1);
	}

	public KettleRecipeBuilder addIngredient(IItemProvider itemIn, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.addIngredient(Ingredient.of(itemIn));
		}
		return this;
	}

	public KettleRecipeBuilder addIngredient(Ingredient ingredientIn) {
		return this.addIngredient(ingredientIn, 1);
	}

	public KettleRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.ingredients.add(ingredientIn);
		}
		return this;
	}

	public void build(Consumer<IFinishedRecipe> consumerIn) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.result);
		this.build(consumerIn, FarmersRespite.MODID + ":brewing/" + location.getPath());
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Brewing Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(save));
		}
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new KettleRecipeBuilder.Result(id, this.result, this.count, this.ingredients, this.brewingTime, this.experience, this.needWater, this.container));
	}

	public static class Result implements IFinishedRecipe
	{
		private final ResourceLocation id;
		private final List<Ingredient> ingredients;
		private final Item result;
		private final int count;
		private final int brewingTime;
		private final float experience;
		private final boolean needWater;
		private final Item container;

		public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int brewingTimeIn, float experienceIn, boolean needWaterIn, @Nullable Item containerIn) {
			this.id = idIn;
			this.ingredients = ingredientsIn;
			this.result = resultIn;
			this.count = countIn;
			this.brewingTime = brewingTimeIn;
			this.experience = experienceIn;
			this.needWater = needWaterIn;
			this.container = containerIn;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			JsonArray arrayIngredients = new JsonArray();

			for (Ingredient ingredient : this.ingredients) {
				arrayIngredients.add(ingredient.toJson());
			}
			json.add("ingredients", arrayIngredients);

			JsonObject objectResult = new JsonObject();
			objectResult.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
			if (this.count > 1) {
				objectResult.addProperty("count", this.count);
			}
			json.add("result", objectResult);

			if (this.container != null) {
				JsonObject objectContainer = new JsonObject();
				objectContainer.addProperty("item", ForgeRegistries.ITEMS.getKey(this.container).toString());
				json.add("container", objectContainer);
			}
			if (this.experience > 0) {
				json.addProperty("experience", this.experience);
			}
			json.addProperty("brewingtime", this.brewingTime);
			json.addProperty("needWater", this.needWater);
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return KettleRecipe.SERIALIZER;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
