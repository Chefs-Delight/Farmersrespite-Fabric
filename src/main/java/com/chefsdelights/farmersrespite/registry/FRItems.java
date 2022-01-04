package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.items.PurulentTeaItem;
import com.chefsdelights.farmersrespite.items.drinks.DrinkItem;
import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;

public class FRItems {
	public static final Item KETTLE = register("kettle", new BlockItem(FRBlocks.KETTLE, new Item.Settings().maxCount(1).group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item WILD_TEA_BUSH = register("wild_tea_bush", new BlockItem(FRBlocks.WILD_TEA_BUSH, new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item TEA_SEEDS = register("tea_seeds", new BlockItem(FRBlocks.SMALL_TEA_BUSH, new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	public static final Item COFFEE_BEANS = register("coffee_beans", new BlockItem(FRBlocks.COFFEE_BUSH, new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item GREEN_TEA_LEAVES = register("green_tea_leaves", new Item(new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	public static final Item YELLOW_TEA_LEAVES = register("yellow_tea_leaves", new Item(new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	public static final Item BLACK_TEA_LEAVES = register("black_tea_leaves", new Item(new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	public static final Item COFFEE_BERRIES = register("coffee_berries", new Item(new Item.Settings().food(Foods.COFFEE_BERRIES).group(FarmersRespite.ITEM_GROUP)));
	public static final Item ROSE_HIPS = register("rose_hips", new Item(new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item GREEN_TEA = register("green_tea", new DrinkItem(new Item.Settings().food(Foods.GREEN_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));
	public static final Item YELLOW_TEA = register("yellow_tea", new DrinkItem(new Item.Settings().food(Foods.YELLOW_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));
	public static final Item BLACK_TEA = register("black_tea", new DrinkItem(new Item.Settings().food(Foods.BLACK_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));
	public static final Item ROSE_HIP_TEA = register("rose_hip_tea", new DrinkItem(new Item.Settings().food(Foods.ROSE_HIP_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));
	public static final Item DANDELION_TEA = register("dandelion_tea", new DrinkItem(new Item.Settings().food(Foods.DANDELION_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));
	public static final Item PURULENT_TEA = register("purulent_tea", new PurulentTeaItem(new Item.Settings().food(Foods.PURULENT_TEA).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP)));
	public static final Item COFFEE = register("coffee", new DrinkItem(new Item.Settings().food(Foods.COFFEE).recipeRemainder(Items.GLASS_BOTTLE).maxCount(16).group(FarmersRespite.ITEM_GROUP), true, false));

	public static final Item GREEN_TEA_COOKIE = register("green_tea_cookie", new Item(new Item.Settings().food(Foods.GREEN_TEA_COOKIE).group(FarmersRespite.ITEM_GROUP)));
	public static final Item NETHER_WART_SOURDOUGH = register("nether_wart_sourdough", new Item(new Item.Settings().food(Foods.NETHER_WART_SOURDOUGH).group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item BLACK_COD = register("black_cod", new ConsumableItem(new Item.Settings().food(Foods.BLACK_COD).recipeRemainder(Items.BOWL).maxCount(16).group(FarmersRespite.ITEM_GROUP)));
	public static final Item TEA_CURRY = register("tea_curry", new ConsumableItem(new Item.Settings().food(Foods.TEA_CURRY).recipeRemainder(Items.BOWL).maxCount(16).group(FarmersRespite.ITEM_GROUP)));
	public static final Item BLAZING_CHILI = register("blazing_chili", new ConsumableItem(new Item.Settings().food(Foods.BLAZING_CHILLI).recipeRemainder(Items.BOWL).maxCount(16).group(FarmersRespite.ITEM_GROUP)));
	
	public static final Item COFFEE_CAKE = register("coffee_cake", new BlockItem(FRBlocks.COFFEE_CAKE, new Item.Settings().maxCount(1).group(FarmersRespite.ITEM_GROUP)));
	public static final Item COFFEE_CAKE_SLICE = register("coffee_cake_slice", new Item(new Item.Settings().food(Foods.COFFEE_CAKE_SLICE).group(FarmersRespite.ITEM_GROUP)));
	public static final Item ROSE_HIP_PIE = register("rose_hip_pie", new BlockItem(FRBlocks.ROSE_HIP_PIE, new Item.Settings().group(FarmersRespite.ITEM_GROUP)));
	public static final Item ROSE_HIP_PIE_SLICE = register("rose_hip_pie_slice", new Item(new Item.Settings().food(Foods.ROSE_HIP_PIE_SLICE).group(FarmersRespite.ITEM_GROUP)));

	static class Foods {
		public static final FoodComponent GREEN_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(StatusEffects.HASTE, 1800, 0), 1.0F).build();
    	public static final FoodComponent YELLOW_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(StatusEffects.RESISTANCE, 1200, 0), 1.0F).build();
    	public static final FoodComponent BLACK_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 3600, 0), 1.0F).statusEffect( new StatusEffectInstance(StatusEffects.POISON, 160, 0), 0.4F).build();
    	public static final FoodComponent COFFEE = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 12000, 1), 1.0F).build();
    	public static final FoodComponent ROSE_HIP_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(StatusEffects.REGENERATION, 200, 0), 1.0F).build();
    	public static final FoodComponent DANDELION_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 3600, 0), 1.0F).build();
    	public static final FoodComponent PURULENT_TEA = (new FoodComponent.Builder()).hunger(0).saturationModifier(0.0f).statusEffect( new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 0), 0.5F).build();
    	public static final FoodComponent ROSE_HIP_PIE_SLICE = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.3f).snack().statusEffect( new StatusEffectInstance(StatusEffects.SPEED, 300, 0), 1.0F).statusEffect( new StatusEffectInstance(StatusEffects.REGENERATION, 300, 0), 1.0F).build();
    	public static final FoodComponent GREEN_TEA_COOKIE = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).statusEffect( new StatusEffectInstance(StatusEffects.HASTE, 100, 0), 1.0F).build();
    	public static final FoodComponent NETHER_WART_SOURDOUGH = (new FoodComponent.Builder()).hunger(4).saturationModifier(0.2f).statusEffect( new StatusEffectInstance(StatusEffects.WEAKNESS, 200, 0), 0.6F).build();
    	public static final FoodComponent BLACK_COD = (new FoodComponent.Builder()).hunger(10).saturationModifier(0.9f).statusEffect( new StatusEffectInstance(EffectsRegistry.NOURISHED.get(), 3600, 0), 1.0F).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 600, 0), 1.0F).build();
    	public static final FoodComponent TEA_CURRY = (new FoodComponent.Builder()).hunger(10).saturationModifier(0.8f).statusEffect( new StatusEffectInstance(StatusEffects.RESISTANCE, 600, 0), 1.0F).build();
    	public static final FoodComponent BLAZING_CHILLI = (new FoodComponent.Builder()).hunger(10).saturationModifier(0.4f).statusEffect( new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1200, 0), 1.0F).build();
		public static final FoodComponent COFFEE_CAKE = (new FoodComponent.Builder()).hunger(1).saturationModifier(0.1F).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 600), 1.0F).build();
    	public static final FoodComponent COFFEE_CAKE_SLICE = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.3f).snack().statusEffect( new StatusEffectInstance(StatusEffects.SPEED, 400, 0), 1.0F).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 600, 0), 1.0F).build();
    	public static final FoodComponent COFFEE_BERRIES = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.4f).statusEffect( new StatusEffectInstance(FREffects.CAFFEINATED, 200, 0), 1.0F).statusEffect( new StatusEffectInstance(StatusEffects.WITHER, 100, 0), 0.8F).build();
	}

	public static <T extends Item> T register(String path, T item) {
		return Registry.register(Registry.ITEM, FarmersRespite.id(path), item);
	}
}