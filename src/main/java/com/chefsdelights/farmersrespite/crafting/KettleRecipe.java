package com.chefsdelights.farmersrespite.crafting;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.umpaz.farmersrespite.FarmersRespite;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class KettleRecipe implements Recipe<Inventory> {
	public static final int INPUT_SLOTS = 2;

	private final Identifier id;
	private final String group;
	private final DefaultedList<Ingredient> inputItems;
	private final ItemStack output;
	private final ItemStack container;
	private final float experience;
	private final int brewTime;
	private final boolean needWater;

	public KettleRecipe(Identifier id, String group, DefaultedList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int brewTime, boolean needWater) {
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
	public Identifier getId() {
		return this.id;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return this.inputItems;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	public ItemStack getContainer() {
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


}