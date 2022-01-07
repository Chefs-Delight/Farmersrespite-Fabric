package com.chefsdelights.farmersrespite.data.builder;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.registry.FRRecipeSerializers;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class KettleRecipeBuilder {
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Item result;
	private final int count;
	private final int brewingTime;
	private final float experience;
	private final Item container;
	private final boolean needWater;

	private KettleRecipeBuilder(ItemConvertible resultIn, int count, int brewingTime, float experience, boolean needWater, @Nullable ItemConvertible container) {
		this.result = resultIn.asItem();
		this.count = count;
		this.brewingTime = brewingTime;
		this.experience = experience;
		this.container = container != null ? container.asItem() : null;
		this.needWater = needWater;
	}

	public static KettleRecipeBuilder kettleRecipe(ItemConvertible mainResult, int count, int brewingTime, float experience, boolean needWater) {
		return new KettleRecipeBuilder(mainResult, count, brewingTime, experience, needWater, null);
	}

	public static KettleRecipeBuilder kettleRecipe(ItemConvertible mainResult, int count, int brewingTime, float experience, boolean needWater, ItemConvertible container) {
		return new KettleRecipeBuilder(mainResult, count, brewingTime, experience, needWater, container);
	}

	public KettleRecipeBuilder addIngredient(Tag<Item> tagIn) {
		return this.addIngredient(Ingredient.fromTag(tagIn));
	}

	public KettleRecipeBuilder addIngredient(ItemConvertible itemIn) {
		return this.addIngredient(itemIn, 1);
	}

	public KettleRecipeBuilder addIngredient(ItemConvertible itemIn, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.addIngredient(Ingredient.ofItems(itemIn));
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

	public void build(Consumer<RecipeJsonProvider> exporter) {
		Identifier location = Registry.ITEM.getId(this.result);
		this.build(exporter, FarmersRespite.MOD_ID + ":brewing/" + location.getPath());
	}

	public void build(Consumer<RecipeJsonProvider> exporter, String save) {
		Identifier resourcelocation = Registry.ITEM.getId(this.result);
		if ((new Identifier(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Brewing Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(exporter, new Identifier(save));
		}
	}

	public void build(Consumer<RecipeJsonProvider> exporter, Identifier id) {
		exporter.accept(new KettleRecipeBuilder.Result(id, this.result, this.count, this.ingredients, this.brewingTime, this.experience, this.needWater, this.container));
	}

	public static class Result implements RecipeJsonProvider {
		private final Identifier id;
		private final List<Ingredient> ingredients;
		private final Item result;
		private final int count;
		private final int brewingTime;
		private final float experience;
		private final boolean needWater;
		private final Item container;

		public Result(Identifier idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int brewingTimeIn, float experienceIn, boolean needWaterIn, @Nullable Item containerIn) {
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
		public void serialize(JsonObject json) {
			JsonArray arrayIngredients = new JsonArray();

			for (Ingredient ingredient : this.ingredients) {
				arrayIngredients.add(ingredient.toJson());
			}
			json.add("ingredients", arrayIngredients);

			JsonObject objectResult = new JsonObject();
			objectResult.addProperty("item", Registry.ITEM.getId(this.result).toString());
			if (this.count > 1) {
				objectResult.addProperty("count", this.count);
			}
			json.add("result", objectResult);

			if (this.container != null) {
				JsonObject objectContainer = new JsonObject();
				objectContainer.addProperty("item", Registry.ITEM.getId(this.container).toString());
				json.add("container", objectContainer);
			}
			if (this.experience > 0) {
				json.addProperty("experience", this.experience);
			}
			json.addProperty("brewingtime", this.brewingTime);
			json.addProperty("needWater", this.needWater);
		}

		@Override
		public Identifier getRecipeId() {
			return this.id;
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return FRRecipeSerializers.BREWING_RECIPE_SERIALIZER.serializer();
		}

		@Nullable
		@Override
		public JsonObject toAdvancementJson() {
			return null;
		}

		@Nullable
		@Override
		public Identifier getAdvancementId() {
			return null;
		}
	}
}