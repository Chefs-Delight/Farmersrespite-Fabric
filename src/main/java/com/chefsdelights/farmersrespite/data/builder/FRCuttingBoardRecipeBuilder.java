package com.chefsdelights.farmersrespite.data.builder;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nhoryzon.mc.farmersdelight.registry.RecipeTypesRegistry;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FRCuttingBoardRecipeBuilder {
	private final List<ChanceResult> results = new ArrayList<>(4);
	private final Ingredient ingredient;
	private final Ingredient tool;
	private String soundEventID;

	private FRCuttingBoardRecipeBuilder(Ingredient ingredient, Ingredient tool, ItemConvertible mainResult, int count, float chance) {
		this.results.add(new ChanceResult(new ItemStack(mainResult.asItem(), count), chance));
		this.ingredient = ingredient;
		this.tool = tool;
	}

	/**
	 * Creates a new builder for a cutting recipe.
	 */
	public static FRCuttingBoardRecipeBuilder cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemConvertible mainResult, int count) {
		return new FRCuttingBoardRecipeBuilder(ingredient, tool, mainResult, count, 1);
	}

	/**
	 * Creates a new builder for a cutting recipe, providing a chance for the main output to drop.
	 */
	public static FRCuttingBoardRecipeBuilder cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemConvertible mainResult, int count, int chance) {
		return new FRCuttingBoardRecipeBuilder(ingredient, tool, mainResult, count, chance);
	}

	/**
	 * Creates a new builder for a cutting recipe, returning 1 unit of the result.
	 */
	public static FRCuttingBoardRecipeBuilder cuttingRecipe(Ingredient ingredient, Ingredient tool, ItemConvertible mainResult) {
		return new FRCuttingBoardRecipeBuilder(ingredient, tool, mainResult, 1, 1);
	}

	public FRCuttingBoardRecipeBuilder addResult(ItemConvertible result) {
		return this.addResult(result, 1);
	}

	public FRCuttingBoardRecipeBuilder addResult(ItemConvertible result, int count) {
		this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), 1));
		return this;
	}

	public FRCuttingBoardRecipeBuilder addResultWithChance(ItemConvertible result, float chance) {
		return this.addResultWithChance(result, chance, 1);
	}

	public FRCuttingBoardRecipeBuilder addResultWithChance(ItemConvertible result, float chance, int count) {
		this.results.add(new ChanceResult(new ItemStack(result.asItem(), count), chance));
		return this;
	}

	public FRCuttingBoardRecipeBuilder addSound(String soundEventID) {
		this.soundEventID = soundEventID;
		return this;
	}

	public void build(Consumer<RecipeJsonProvider> exporter) {
		Identifier location = Registry.ITEM.getId(this.ingredient.getMatchingStacks()[0].getItem());
		this.build(exporter, FarmersRespite.MOD_ID + ":cutting/" + location.getPath());
	}

	public void build(Consumer<RecipeJsonProvider> exporter, String save) {
		Identifier resourcelocation = Registry.ITEM.getId(this.ingredient.getMatchingStacks()[0].getItem());
		if ((new Identifier(save)).equals(resourcelocation)) {
			throw new IllegalStateException("Cutting Recipe " + save + " should remove its 'save' argument");
		} else {
			this.build(exporter, new Identifier(save));
		}
	}

	public void build(Consumer<RecipeJsonProvider> exporter, Identifier id) {
		exporter.accept(new FRCuttingBoardRecipeBuilder.Result(id, this.ingredient, this.tool, this.results, this.soundEventID == null ? "" : this.soundEventID));
	}

	public static class Result implements RecipeJsonProvider {
		private final Identifier id;
		private final Ingredient ingredient;
		private final Ingredient tool;
		private final List<ChanceResult> results;
		private final String soundEventID;

		public Result(Identifier idIn, Ingredient ingredientIn,  Ingredient toolIn, List<ChanceResult> resultsIn, String soundEventIDIn) {
			this.id = idIn;
			this.ingredient = ingredientIn;
			this.tool = toolIn;
			this.results = resultsIn;
			this.soundEventID = soundEventIDIn;
		}

		@Override
		public void serialize(JsonObject json) {
			JsonArray arrayIngredients = new JsonArray();
			arrayIngredients.add(this.ingredient.toJson());
			json.add("ingredients", arrayIngredients);

			json.add("tool", this.tool.toJson());

			JsonArray arrayResults = new JsonArray();
			for (ChanceResult result : this.results) {
				JsonObject jsonobject = new JsonObject();
				jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(result.getStack().getItem()).toString());
				if (result.getStack().getCount() > 1) {
					jsonobject.addProperty("count", result.getStack().getCount());
				}
				if (result.getChance() < 1) {
					jsonobject.addProperty("chance", result.getChance());
				}
				arrayResults.add(jsonobject);
			}
			json.add("result", arrayResults);
			if (!this.soundEventID.isEmpty()) {
				json.addProperty("sound", this.soundEventID);
			}
		}

		@Override
		public Identifier getRecipeId() {
			return this.id;
		}

		@Override
		public RecipeSerializer<?> getSerializer() {
			return RecipeTypesRegistry.CUTTING_RECIPE_SERIALIZER.serializer();
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