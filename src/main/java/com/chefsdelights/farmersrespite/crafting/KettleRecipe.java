package com.umpaz.farmersrespite.crafting;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.umpaz.farmersrespite.FarmersRespite;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KettleRecipe implements IRecipe<RecipeWrapper>
{
	public static IRecipeType<KettleRecipe> TYPE = IRecipeType.register(FarmersRespite.MODID + ":brewing");
	public static final Serializer SERIALIZER = new Serializer();
	public static final int INPUT_SLOTS = 2;

	private final ResourceLocation id;
	private final String group;
	private final NonNullList<Ingredient> inputItems;
	private final ItemStack output;
	private final ItemStack container;
	private final float experience;
	private final int brewTime;
	private final boolean needWater;

	public KettleRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int brewTime, boolean needWater) {
		this.id = id;
		this.group = group;
		this.inputItems = inputItems;
		this.output = output;

		if (!container.isEmpty()) {
			this.container = container;
		} else if (!output.getContainerItem().isEmpty()) {
			this.container = output.getContainerItem();
		} else {
			this.container = ItemStack.EMPTY;
		}

		this.experience = experience;
		this.brewTime = brewTime;
		this.needWater = needWater;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputItems;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	public ItemStack getOutputContainer() {
		return this.container;
	}

	@Override
	public ItemStack assemble(RecipeWrapper inv) {
		return this.output.copy();
	}

	public float getExperience() {
		return this.experience;
	}

	public int getBrewTime() {
		return this.brewTime;
	}
	
	public boolean getNeedWater() {
		return this.needWater;
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;

		for (int j = 0; j < INPUT_SLOTS; ++j) {
			ItemStack itemstack = inv.getItem(j);
			if (!itemstack.isEmpty()) {
				++i;
				inputs.add(itemstack);
			}
		}
		return i == this.inputItems.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.inputItems) != null;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= this.inputItems.size();
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return KettleRecipe.SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return KettleRecipe.TYPE;
	}

	private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<KettleRecipe>
	{
		Serializer() {
			this.setRegistryName(new ResourceLocation(FarmersRespite.MODID, "brewing"));
		}

		@Override
		public KettleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final String groupIn = JSONUtils.getAsString(json, "group", "");
			final NonNullList<Ingredient> inputItemsIn = readIngredients(JSONUtils.getAsJsonArray(json, "ingredients"));
			if (inputItemsIn.isEmpty()) {
				throw new JsonParseException("No ingredients for brewing recipe");
			} else if (inputItemsIn.size() > KettleRecipe.INPUT_SLOTS) {
				throw new JsonParseException("Too many ingredients for brewing recipe! The max is " + KettleRecipe.INPUT_SLOTS);
			} else {
				final ItemStack outputIn = CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "result"), true);
				ItemStack container = JSONUtils.isValidNode(json, "container") ? CraftingHelper.getItemStack(JSONUtils.getAsJsonObject(json, "container"), true) : ItemStack.EMPTY;
				final float experienceIn = JSONUtils.getAsFloat(json, "experience", 0.0F);
				final int brewTimeIn = JSONUtils.getAsInt(json, "brewingtime", 300);
				final boolean needWaterIn = JSONUtils.getAsBoolean(json, "needwater", true);
				return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for (int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}

			return nonnulllist;
		}

		@Nullable
		@Override
		public KettleRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
			String groupIn = buffer.readUtf(32767);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

			for (int j = 0; j < inputItemsIn.size(); ++j) {
				inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
			}

			ItemStack outputIn = buffer.readItem();
			ItemStack container = buffer.readItem();
			float experienceIn = buffer.readFloat();
			int brewTimeIn = buffer.readVarInt();
			boolean needWaterIn = buffer.readBoolean();
			return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
		}

		@Override
		public void toNetwork(PacketBuffer buffer, KettleRecipe recipe) {
			buffer.writeUtf(recipe.group);
			buffer.writeVarInt(recipe.inputItems.size());

			for (Ingredient ingredient : recipe.inputItems) {
				ingredient.toNetwork(buffer);
			}

			buffer.writeItem(recipe.output);
			buffer.writeItem(recipe.container);
			buffer.writeFloat(recipe.experience);
			buffer.writeVarInt(recipe.brewTime);
			buffer.writeBoolean(recipe.needWater);
		}
	}
}
