package com.umpaz.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.items.PurulentTeaItem;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.items.ConsumableItem;
import vectorwing.farmersdelight.items.drinks.DrinkItem;
import vectorwing.farmersdelight.registry.ModEffects;

public class FRItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersRespite.MODID);

	public static final RegistryObject<Item> KETTLE = ITEMS.register("kettle",
			() -> new BlockItem(FRBlocks.KETTLE.get(), new Item.Properties().stacksTo(1).tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> WILD_TEA_BUSH = ITEMS.register("wild_tea_bush",
			() -> new BlockItem(FRBlocks.WILD_TEA_BUSH.get(), new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds",
			() -> new BlockItem(FRBlocks.SMALL_TEA_BUSH.get(), new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans",
			() -> new BlockItem(FRBlocks.COFFEE_BUSH.get(), new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> GREEN_TEA_LEAVES = ITEMS.register("green_tea_leaves",
			() -> new Item(new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> YELLOW_TEA_LEAVES = ITEMS.register("yellow_tea_leaves",
			() -> new Item(new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> BLACK_TEA_LEAVES = ITEMS.register("black_tea_leaves",
			() -> new Item(new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> COFFEE_BERRIES = ITEMS.register("coffee_berries",
			() -> new Item(new Item.Properties().food(Foods.COFFEE_BERRIES).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> ROSE_HIPS = ITEMS.register("rose_hips",
			() -> new Item(new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> GREEN_TEA = ITEMS.register("green_tea",
			() -> new DrinkItem(new Item.Properties().food(Foods.GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));
	public static final RegistryObject<Item> YELLOW_TEA = ITEMS.register("yellow_tea",
			() -> new DrinkItem(new Item.Properties().food(Foods.YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));
	public static final RegistryObject<Item> BLACK_TEA = ITEMS.register("black_tea",
			() -> new DrinkItem(new Item.Properties().food(Foods.BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));
	public static final RegistryObject<Item> ROSE_HIP_TEA = ITEMS.register("rose_hip_tea",
			() -> new DrinkItem(new Item.Properties().food(Foods.ROSE_HIP_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));
	public static final RegistryObject<Item> DANDELION_TEA = ITEMS.register("dandelion_tea",
			() -> new DrinkItem(new Item.Properties().food(Foods.DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));
	public static final RegistryObject<Item> PURULENT_TEA = ITEMS.register("purulent_tea",
			() -> new PurulentTeaItem(new Item.Properties().food(Foods.PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee",
			() -> new DrinkItem(new Item.Properties().food(Foods.COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.ITEM_GROUP), true, false));

	public static final RegistryObject<Item> GREEN_TEA_COOKIE = ITEMS.register("green_tea_cookie",
			() -> new Item(new Item.Properties().food(Foods.GREEN_TEA_COOKIE).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> NETHER_WART_SOURDOUGH = ITEMS.register("nether_wart_sourdough",
			() -> new Item(new Item.Properties().food(Foods.NETHER_WART_SOURDOUGH).tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> BLACK_COD = ITEMS.register("black_cod",
			() -> new ConsumableItem(new Item.Properties().food(Foods.BLACK_COD).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> TEA_CURRY = ITEMS.register("tea_curry",
			() -> new ConsumableItem(new Item.Properties().food(Foods.TEA_CURRY).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> BLAZING_CHILI = ITEMS.register("blazing_chili",
			() -> new ConsumableItem(new Item.Properties().food(Foods.BLAZING_CHILLI).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.ITEM_GROUP)));
	
	public static final RegistryObject<Item> COFFEE_CAKE = ITEMS.register("coffee_cake",
			() -> new BlockItem(FRBlocks.COFFEE_CAKE.get(), new Item.Properties().stacksTo(1).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> COFFEE_CAKE_SLICE = ITEMS.register("coffee_cake_slice",
			() -> new Item(new Item.Properties().food(Foods.COFFEE_CAKE_SLICE).tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> ROSE_HIP_PIE = ITEMS.register("rose_hip_pie",
			() -> new BlockItem(FRBlocks.ROSE_HIP_PIE.get(), new Item.Properties().tab(FarmersRespite.ITEM_GROUP)));
	public static final RegistryObject<Item> ROSE_HIP_PIE_SLICE = ITEMS.register("rose_hip_pie_slice",
			() -> new Item(new Item.Properties().food(Foods.ROSE_HIP_PIE_SLICE).tab(FarmersRespite.ITEM_GROUP)));
	
	static class Foods {
		public static final Food GREEN_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(Effects.DIG_SPEED, 1800, 0), 1.0F).build();
    	public static final Food YELLOW_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0), 1.0F).build();
    	public static final Food BLACK_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 3600, 0), 1.0F).effect(() -> new EffectInstance(Effects.POISON, 160, 0), 0.4F).build();
    	public static final Food COFFEE = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 12000, 1), 1.0F).build();
    	public static final Food ROSE_HIP_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(Effects.REGENERATION, 200, 0), 1.0F).build();    	
    	public static final Food DANDELION_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();
    	public static final Food PURULENT_TEA = (new Food.Builder()).nutrition(0).saturationMod(0.0f).effect(() -> new EffectInstance(Effects.WEAKNESS, 600, 0), 0.5F).build();
    	public static final Food ROSE_HIP_PIE_SLICE = (new Food.Builder()).nutrition(3).saturationMod(0.3f).fast().effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 300, 0), 1.0F).effect(() -> new EffectInstance(Effects.REGENERATION, 300, 0), 1.0F).build();  
    	public static final Food GREEN_TEA_COOKIE = (new Food.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new EffectInstance(Effects.DIG_SPEED, 100, 0), 1.0F).build();
    	public static final Food NETHER_WART_SOURDOUGH = (new Food.Builder()).nutrition(4).saturationMod(0.2f).effect(() -> new EffectInstance(Effects.WEAKNESS, 200, 0), 0.6F).build();  
    	public static final Food BLACK_COD = (new Food.Builder()).nutrition(10).saturationMod(0.9f).effect(() -> new EffectInstance(ModEffects.NOURISHED.get(), 3600, 0), 1.0F).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 600, 0), 1.0F).build();
    	public static final Food TEA_CURRY = (new Food.Builder()).nutrition(10).saturationMod(0.8f).effect(() -> new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 0), 1.0F).build();
    	public static final Food BLAZING_CHILLI = (new Food.Builder()).nutrition(10).saturationMod(0.4f).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 1200, 0), 1.0F).build();
		public static final Food COFFEE_CAKE = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 600), 1.0F).build();
    	public static final Food COFFEE_CAKE_SLICE = (new Food.Builder()).nutrition(3).saturationMod(0.3f).fast().effect(() -> new EffectInstance(Effects.MOVEMENT_SPEED, 400, 0), 1.0F).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 600, 0), 1.0F).build();  
    	public static final Food COFFEE_BERRIES = (new Food.Builder()).nutrition(2).saturationMod(0.4f).effect(() -> new EffectInstance(FREffects.CAFFEINATED.get(), 200, 0), 1.0F).effect(() -> new EffectInstance(Effects.WITHER, 100, 0), 0.8F).build();  

	}
}
