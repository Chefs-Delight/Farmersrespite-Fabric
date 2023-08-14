package com.chefsdelights.farmersrespite.integration.emi;

import com.chefsdelights.farmersrespite.common.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import com.chefsdelights.farmersrespite.core.registry.FRRecipeSerializers;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

public class EMIPlugin implements EmiPlugin {
    private static final EmiStack ICON = EmiStack.of(FRItems.KETTLE);
    public static final EmiRecipeCategory BREWING_CATEGORY = new EmiRecipeCategory(
            new ResourceLocation(FarmersRespite.MOD_ID, "brewing"), ICON
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BREWING_CATEGORY);
        RecipeManager manager = registry.getRecipeManager();
        for (KettleRecipe recipe : manager.getAllRecipesFor(FRRecipeSerializers.BREWING)) {
            registry.addRecipe(new KettleEMIRecipe(recipe));
        }
    }
}
