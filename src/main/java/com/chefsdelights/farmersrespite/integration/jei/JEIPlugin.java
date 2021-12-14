package com.umpaz.farmersrespite.integration.jei;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.client.gui.KettleScreen;
import com.umpaz.farmersrespite.crafting.KettleRecipe;
import com.umpaz.farmersrespite.integration.jei.brewing.BrewingRecipeCategory;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.tile.container.KettleContainer;

import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin
{
	private static final ResourceLocation ID = new ResourceLocation(FarmersRespite.MODID, "jei_plugin");
	private static final Minecraft MC = Minecraft.getInstance();

	private static List<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
		return MC.level
				.getRecipeManager()
				.getRecipes()
				.stream()
				.filter(r -> r.getType() == type)
				.collect(Collectors.toList());
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new BrewingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		registration.addRecipes(findRecipesByType(KettleRecipe.TYPE), BrewingRecipeCategory.UID);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(new ItemStack(FRItems.KETTLE.get()), BrewingRecipeCategory.UID);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(KettleScreen.class, 62, 25, 40, 17, BrewingRecipeCategory.UID);
	}

	@Override
	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
		registration.addRecipeTransferHandler(KettleContainer.class, BrewingRecipeCategory.UID, 0, 2, 5, 36);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}
}
