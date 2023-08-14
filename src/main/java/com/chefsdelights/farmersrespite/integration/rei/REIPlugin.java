package com.chefsdelights.farmersrespite.integration.rei;

import com.chefsdelights.farmersrespite.client.gui.KettleScreen;
import com.chefsdelights.farmersrespite.common.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.integration.rei.category.BrewingRecipeCategory;
import com.chefsdelights.farmersrespite.integration.rei.category.BrewingRecipeDisplay;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.MethodsReturnNonnullByDefault;


@Environment(EnvType.CLIENT)
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class REIPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<BrewingRecipeDisplay> BREWING = CategoryIdentifier.of(FarmersRespite.MOD_ID, "brewing");

    public static Rectangle centeredIntoRecipeBase(Point origin, int width, int height) {
        return centeredInto(new Rectangle(origin.x, origin.y, 150, 66), width, height);
    }

    public static Rectangle centeredInto(Rectangle origin, int width, int height) {
        return new Rectangle(origin.x + (origin.width - width) / 2, origin.y + (origin.height - height) / 2, width, height);
    }

    public void registerCategories(CategoryRegistry registry) {
        registry.add(new BrewingRecipeCategory());
        registry.addWorkstations(BREWING, EntryStacks.of(FRBlocks.KETTLE));
    }

    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(KettleRecipe.class, KettleRecipe.TYPE, BrewingRecipeDisplay::new);
    }

    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(new Rectangle(62, 25, 40, 17), KettleScreen.class, BREWING);
    }
}