//package com.chefsdelights.farmersrespite.integration.jei;
//
//import com.farmersrespite.client.gui.KettleScreen;
//import com.farmersrespite.common.block.entity.container.KettleContainer;
//import com.farmersrespite.common.crafting.KettleRecipe;
//import com.farmersrespite.core.FarmersRespite;
//import com.farmersrespite.core.registry.FRItems;
//import com.farmersrespite.integration.jei.category.BrewingRecipeCategory;
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.registration.*;
//import net.minecraft.MethodsReturnNonnullByDefault;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Recipe;
//import net.minecraft.world.item.crafting.RecipeType;
//
//import javax.annotation.ParametersAreNonnullByDefault;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@JeiPlugin
//@ParametersAreNonnullByDefault
//@MethodsReturnNonnullByDefault
//@SuppressWarnings("unused")
//public class JEIPlugin implements IModPlugin
//{
//	private static final ResourceLocation ID = new ResourceLocation(FarmersRespite.MODID, "jei_plugin");
//	private static final Minecraft MC = Minecraft.getInstance();
//
//	private static List<Recipe<?>> findRecipesByType(RecipeType<?> type) {
//		return MC.level
//				.getRecipeManager()
//				.getRecipes()
//				.stream()
//				.filter(r -> r.getType() == type)
//				.collect(Collectors.toList());
//	}
//
//	@Override
//	public void registerCategories(IRecipeCategoryRegistration registry) {
//		registry.addRecipeCategories(new BrewingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
//	}
//
//	@Override
//	public void registerRecipes(IRecipeRegistration registration) {
//		registration.addRecipes(findRecipesByType(KettleRecipe.TYPE), BrewingRecipeCategory.UID);
//	}
//
//	@Override
//	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
//		registration.addRecipeCatalyst(new ItemStack(FRItems.KETTLE.get()), BrewingRecipeCategory.UID);
//	}
//
//	@Override
//	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
//		registration.addRecipeClickArea(KettleScreen.class, 62, 25, 40, 17, BrewingRecipeCategory.UID);
//	}
//
//	@Override
//	public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
//		registration.addRecipeTransferHandler(KettleContainer.class, BrewingRecipeCategory.UID, 0, 2, 5, 36);
//	}
//
//	@Override
//	public ResourceLocation getPluginUid() {
//		return ID;
//	}
//}
