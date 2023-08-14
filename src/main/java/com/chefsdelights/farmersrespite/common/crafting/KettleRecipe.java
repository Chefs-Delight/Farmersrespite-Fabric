package com.chefsdelights.farmersrespite.common.crafting;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import com.nhoryzon.mc.farmersdelight.util.RecipeMatcher;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class KettleRecipe implements Recipe<Container> {
    public static RecipeType<KettleRecipe> TYPE = RecipeType.register(FarmersRespite.MOD_ID + ":brewing");
    public static final RecipeSerializer<KettleRecipe> SERIALIZER = new Serializer();
    public static final int INPUT_SLOTS = 2;

    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    public final ItemStack container;
    private final float experience;
    public final int brewTime;
    private final boolean needWater;

    public KettleRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int brewTime, boolean needWater) {
        this.id = id;
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (output.getItem().getCraftingRemainingItem() != null) {
            this.container = new ItemStack(output.getItem().getCraftingRemainingItem());
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

    public ItemStack getOutputContainer() {
        return this.container;
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
    public boolean matches(Container inv, Level worldIn) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.inputItems.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return KettleRecipe.SERIALIZER;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return KettleRecipe.TYPE;
    }

    private static class Serializer implements RecipeSerializer<KettleRecipe> {
        Serializer() {
        }

        @Override
        public KettleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String groupIn = GsonHelper.getAsString(json, "group", "");
            final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for brewing recipe");
            } else if (inputItemsIn.size() > KettleRecipe.INPUT_SLOTS) {
                throw new JsonParseException("Too many ingredients for brewing recipe! The max is " + KettleRecipe.INPUT_SLOTS);
            } else {
                JsonObject jsonResult = GsonHelper.getAsJsonObject(json, "result");
                ItemStack outputIn = new ItemStack(GsonHelper.getAsItem(jsonResult, "item"), GsonHelper.getAsInt(jsonResult, "count", 1));

                ItemStack container = ItemStack.EMPTY;
                if (GsonHelper.isValidNode(json, "container")) {
                    JsonObject jsonContainer = GsonHelper.getAsJsonObject(json, "container");
                    container = new ItemStack(GsonHelper.getAsItem(jsonContainer, "item"), GsonHelper.getAsInt(jsonContainer, "count", 1));
                }

                final float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
                final int brewTimeIn = GsonHelper.getAsInt(json, "brewingtime", 2400);
                final boolean needWaterIn = GsonHelper.getAsBoolean(json, "needWater", true);
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
        public KettleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
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
            Boolean needWaterIn = buffer.readBoolean();
            return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, KettleRecipe recipe) {
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
