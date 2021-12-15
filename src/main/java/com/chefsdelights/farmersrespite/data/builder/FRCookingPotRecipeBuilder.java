package com.chefsdelights.farmersrespite.data.builder;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.umpaz.farmersrespite.FarmersRespite;

import mezz.jei.api.MethodsReturnNonnullByDefault;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.crafting.CookingPotRecipe;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FRCookingPotRecipeBuilder {
	private final List<Ingredient> ingredients = Lists.newArrayList();
	private final Item result;
	private final int count;
	private final int cookingTime;
	private final float experience;
	private final Item container;

	private FRCookingPotRecipeBuilder(IItemProvider resultIn, int count, int cookingTime, float experience, @Nullable IItemProvider container) {
		this.result = resultIn.asItem();
		this.count = count;
		this.cookingTime = cookingTime;
		this.experience = experience;
		this.container = container != null ? container.asItem() : null;
	}

	public static FRCookingPotRecipeBuilder cookingPotRecipe(IItemProvider mainResult, int count, int cookingTime, float experience) {
		return new FRCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, null);
	}

	public static FRCookingPotRecipeBuilder cookingPotRecipe(IItemProvider mainResult, int count, int cookingTime, float experience, IItemProvider container) {
		return new FRCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, container);
	}

	public FRCookingPotRecipeBuilder addIngredient(ITag<Item> tagIn) {
		return this.addIngredient(Ingredient.of(tagIn));
	}

	public FRCookingPotRecipeBuilder addIngredient(IItemProvider itemIn) {
		return this.addIngredient(itemIn, 1);
	}

	public FRCookingPotRecipeBuilder addIngredient(IItemProvider itemIn, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.addIngredient(Ingredient.of(itemIn));
		}
		return this;
	}

	public FRCookingPotRecipeBuilder addIngredient(Ingredient ingredientIn) {
		return this.addIngredient(ingredientIn, 1);
	}

	public FRCookingPotRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
		for (int i = 0; i < quantity; ++i) {
			this.ingredients.add(ingredientIn);
		}
		return this;
	}

	public void build(Consumer<IFinishedRecipe> consumerIn) {
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.result);
		this.build(consumerIn, FarmersRespite.MODID + ":cooking/" + location.getPath());
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, String save) {
		ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
		if ((new ResourceLocation(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Cooking Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(consumerIn, new ResourceLocation(save));
		}
	}

	public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
		consumerIn.accept(new FRCookingPotRecipeBuilder.Result(id, this.result, this.count, this.ingredients, this.cookingTime, this.experience, this.container));
	}

	public static class Result implements IFinishedRecipe
	{
		private final ResourceLocation id;
		private final List<Ingredient> ingredients;
		private final Item result;
		private final int count;
		private final int cookingTime;
		private final float experience;
		private final Item container;

		public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int cookingTimeIn, float experienceIn, @Nullable Item containerIn) {
			this.id = idIn;
			this.ingredients = ingredientsIn;
			this.result = resultIn;
			this.count = countIn;
			this.cookingTime = cookingTimeIn;
			this.experience = experienceIn;
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
			json.addProperty("cookingtime", this.cookingTime);
		}

		@Override
		public ResourceLocation getId() {
			return this.id;
		}

		@Override
		public IRecipeSerializer<?> getType() {
			return CookingPotRecipe.SERIALIZER;
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