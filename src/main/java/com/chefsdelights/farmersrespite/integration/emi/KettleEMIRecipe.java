package com.chefsdelights.farmersrespite.integration.emi;

import com.chefsdelights.farmersrespite.common.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.google.common.collect.ImmutableList;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class KettleEMIRecipe implements EmiRecipe {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(FarmersRespite.MOD_ID, "textures/gui/rei/kettle_rei.png");
    private final ResourceLocation id;

    private final EmiIngredient containerOutput;
    private final List<EmiIngredient> ingredient;
    private final int brewTime;
    private final boolean needWater;
    private final EmiStack output;

    public KettleEMIRecipe(KettleRecipe recipe) {
        this.id = recipe.getId();
        this.ingredient = recipe.getIngredients().stream().map(EmiIngredient::of).toList();
        this.output = EmiStack.of(recipe.getResultItem(BasicDisplay.registryAccess()));
        this.containerOutput = EmiStack.of(recipe.getOutputContainer());
        this.brewTime = recipe.getBrewTime();
        this.needWater = recipe.getNeedWater();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIPlugin.BREWING_CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        List<EmiIngredient> inputEntryList = new ArrayList<>(ingredient);
        inputEntryList.add(getContainerOutput());

        return ImmutableList.copyOf(inputEntryList);
    }

    public EmiIngredient getContainerOutput() {
        return containerOutput;
    }

    public int getBrewTime() {
        return brewTime;
    }

    public boolean getNeedWater() {
        return needWater;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return containerOutput.getEmiStacks();
    }

    @Override
    public int getDisplayWidth() {
        return 116;
    }

    @Override
    public int getDisplayHeight() {
        return 56;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {

        widgets.addDrawable(0, 0, 116, 56, ((draw, mouseX, mouseY, delta) -> {
            draw.blit(GUI_TEXTURE, 0, 0, 29, 16, 116, 56, 256, 256);
        }));

        List<EmiIngredient> ingredientEntries = this.getInputs();
        if (ingredientEntries != null) {
            if (!ingredientEntries.isEmpty()) {
                widgets.addSlot(ingredientEntries.get(0), 13 - 1, 0).drawBack(false);
            }
            if (ingredientEntries.size() > 1) {
                widgets.addSlot(ingredientEntries.get(1), 13 - 1, 18).drawBack(false);
            }
        }
        widgets.addSlot(this.getContainerOutput(), 57 - 1, 39 - 1).drawBack(false);
        widgets.addSlot(output, 89 - 1, 12 - 1).drawBack(false);
        widgets.addSlot(output, 89 - 1, 39 - 1).drawBack(false);
        widgets.addFillingArrow(42, 10, this.getBrewTime() * 20);

        widgets.addDrawable(13,39, 17, 15, (draw, mouseX, mouseY, delta) -> {
           draw.blit(GUI_TEXTURE,  0,0, 176, 0, 17, 15);
        });

        if (getNeedWater()) {
            widgets.addDrawable(5, 23, 5, 11, (draw, mouseX, mouseY, delta) -> {
               draw.blit(GUI_TEXTURE, 0,0, 176, 15, 5, 11);
            }).tooltipText(FarmersRespite.i18n("rei.brewing.needWater").toFlatList());
        } else {
            widgets.addDrawable(5, 23, 5, 11, (draw, mouseX, mouseY, delta) -> {

            }).tooltipText(FarmersRespite.i18n("rei.brewing.noWater").toFlatList());;
        }
    }
}
