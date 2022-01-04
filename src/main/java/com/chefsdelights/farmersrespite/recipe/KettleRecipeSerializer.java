package com.chefsdelights.farmersrespite.recipe;

import com.chefsdelights.farmersrespite.crafting.KettleRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class KettleRecipeSerializer implements RecipeSerializer<KettleRecipe> {
    public KettleRecipeSerializer() {}

    private static DefaultedList<Ingredient> readIngredients(JsonArray ingredientArray) {
        DefaultedList<Ingredient> ingredientList = DefaultedList.of();

        for (int i = 0; i < ingredientArray.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
            if (!ingredient.isEmpty()) {
                ingredientList.add(ingredient);
            }
        }

        return ingredientList;
    }

    @Override
    public KettleRecipe read(Identifier id, JsonObject json) {
        String groupIn = JsonHelper.getString(json, "group", "");
        DefaultedList<Ingredient> inputItemsIn = readIngredients(JsonHelper.getArray(json, "ingredients"));
        if (inputItemsIn.isEmpty()) {
            throw new JsonParseException("No ingredients for brewing recipe");
        } else if (inputItemsIn.size() > KettleRecipe.INPUT_SLOTS) {
            throw new JsonParseException("Too many ingredients for brewing recipe! The max is " + KettleRecipe.INPUT_SLOTS);
        } else {
            JsonObject jsonResult = JsonHelper.getObject(json, "result");
            ItemStack outputIn = new ItemStack(JsonHelper.getItem(jsonResult, "item"), JsonHelper.getInt(jsonResult, "count", 1));
            ItemStack container = ItemStack.EMPTY;
            if (JsonHelper.hasElement(json, "container")) {
                JsonObject jsonContainer = JsonHelper.getObject(json, "container");
                container = new ItemStack(JsonHelper.getItem(jsonContainer, "item"), JsonHelper.getInt(jsonContainer, "count", 1));
            }

            float experienceIn = JsonHelper.getFloat(json, "experience", 0.0F);
            int brewTimeIn = JsonHelper.getInt(json, "brewingtime", 300);
            boolean needWaterIn = JsonHelper.getBoolean(json, "needwater", true);
            return new KettleRecipe(id, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
        }
    }

    @Override
    public KettleRecipe read(Identifier id, PacketByteBuf buf) {
        String groupIn = buf.readString(32767);
        int ingredientSize = buf.readVarInt();
        DefaultedList<Ingredient> ingredientList = DefaultedList.ofSize(ingredientSize, Ingredient.EMPTY);

        for (int j = 0; j < ingredientList.size(); ++j) {
            ingredientList.set(j, Ingredient.fromPacket(buf));
        }

        ItemStack outputIn = buf.readItemStack();
        ItemStack container = buf.readItemStack();
        float experienceIn = buf.readFloat();
        int brewTimeIn = buf.readVarInt();
        boolean needWaterIn = buf.readBoolean();
        return new KettleRecipe(id, groupIn, ingredientList, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
    }

    @Override
    public void write(PacketByteBuf buf, KettleRecipe recipe) {
        buf.writeString(recipe.getGroup());
        buf.writeVarInt(recipe.getIngredients().size());

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buf);
        }

        buf.writeItemStack(recipe.getOutput());
        buf.writeItemStack(recipe.getContainer());
        buf.writeFloat(recipe.getExperience());
        buf.writeVarInt(recipe.getBrewTime());
        buf.writeBoolean(recipe.getNeedWater());
    }
}