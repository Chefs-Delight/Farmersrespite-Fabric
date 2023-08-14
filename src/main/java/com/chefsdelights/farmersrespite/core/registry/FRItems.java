package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.item.PurulentTeaItem;
import com.chefsdelights.farmersrespite.common.item.RoseHipTeaItem;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.utility.FRFoods;
import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.nhoryzon.mc.farmersdelight.item.DrinkableItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class FRItems {

    public static List<Item> ITEMS = new ArrayList<>();

    // Items
    public static final Item KETTLE = register("kettle", new BlockItem(FRBlocks.KETTLE, new Item.Properties().stacksTo(1)));

    public static final Item WILD_TEA_BUSH = register("wild_tea_bush", new BlockItem(FRBlocks.WILD_TEA_BUSH, new Item.Properties()));

    public static final Item TEA_SEEDS = register("tea_seeds", new BlockItem(FRBlocks.SMALL_TEA_BUSH, new Item.Properties()));
    public static final Item COFFEE_BEANS = register("coffee_beans", new BlockItem(FRBlocks.COFFEE_BUSH, new Item.Properties()));

    public static final Item GREEN_TEA_LEAVES = register("green_tea_leaves", new Item(new Item.Properties()));
    public static final Item YELLOW_TEA_LEAVES = register("yellow_tea_leaves", new Item(new Item.Properties()));
    public static final Item BLACK_TEA_LEAVES = register("black_tea_leaves", new Item(new Item.Properties()));
    public static final Item COFFEE_BERRIES = register("coffee_berries", new Item(new Item.Properties().food(FRFoods.COFFEE_BERRIES)));
    public static final Item ROSE_HIPS = register("rose_hips", new Item(new Item.Properties()));

    public static final Item GREEN_TEA = register("green_tea", new DrinkableItem(new Item.Properties().food(FRFoods.GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item YELLOW_TEA = register("yellow_tea", new DrinkableItem(new Item.Properties().food(FRFoods.YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item BLACK_TEA = register("black_tea", new DrinkableItem(new Item.Properties().food(FRFoods.BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item ROSE_HIP_TEA = register("rose_hip_tea", new RoseHipTeaItem(2, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final Item DANDELION_TEA = register("dandelion_tea", new DrinkableItem(new Item.Properties().food(FRFoods.DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item PURULENT_TEA = register("purulent_tea", new PurulentTeaItem(300, new Item.Properties().food(FRFoods.PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final Item COFFEE = register("coffee", new DrinkableItem(new Item.Properties().food(FRFoods.COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final Item LONG_GREEN_TEA = register("long_green_tea", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item LONG_YELLOW_TEA = register("long_yellow_tea", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item LONG_BLACK_TEA = register("long_black_tea", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item LONG_DANDELION_TEA = register("long_dandelion_tea", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item LONG_COFFEE = register("long_coffee", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item LONG_APPLE_CIDER = register("long_apple_cider", new DrinkableItem(new Item.Properties().food(FRFoods.LONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final Item STRONG_GREEN_TEA = register("strong_green_tea", new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item STRONG_YELLOW_TEA = register("strong_yellow_tea", new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item STRONG_BLACK_TEA = register("strong_black_tea", new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item STRONG_PURULENT_TEA = register("strong_purulent_tea", new PurulentTeaItem(600, new Item.Properties().food(FRFoods.STRONG_PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final Item STRONG_ROSE_HIP_TEA = register("strong_rose_hip_tea", new RoseHipTeaItem(4, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final Item STRONG_COFFEE = register("strong_coffee", new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));
    public static final Item STRONG_MELON_JUICE = register("strong_melon_juice", new RoseHipTeaItem(4, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));
    public static final Item STRONG_APPLE_CIDER = register("strong_apple_cider", new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final Item GREEN_TEA_COOKIE = register("green_tea_cookie", new Item(new Item.Properties().food(FRFoods.GREEN_TEA_COOKIE)));
    public static final Item NETHER_WART_SOURDOUGH = register("nether_wart_sourdough", new Item(new Item.Properties().food(FRFoods.NETHER_WART_SOURDOUGH)));

    public static final Item BLACK_COD = register("black_cod", new ConsumableItem(new Item.Properties().food(FRFoods.BLACK_COD).craftRemainder(Items.BOWL).stacksTo(16), true));
    public static final Item TEA_CURRY = register("tea_curry", new ConsumableItem(new Item.Properties().food(FRFoods.TEA_CURRY).craftRemainder(Items.BOWL).stacksTo(16), true));
    public static final Item BLAZING_CHILI = register("blazing_chili", new ConsumableItem(new Item.Properties().food(FRFoods.BLAZING_CHILLI).craftRemainder(Items.BOWL).stacksTo(16), true));

    public static final Item COFFEE_CAKE = register("coffee_cake", new BlockItem(FRBlocks.COFFEE_CAKE, new Item.Properties().stacksTo(1)));
    public static final Item COFFEE_CAKE_SLICE = register("coffee_cake_slice", new Item(new Item.Properties().food(FRFoods.COFFEE_CAKE_SLICE)));
    public static final Item ROSE_HIP_PIE = register("rose_hip_pie", new BlockItem(FRBlocks.ROSE_HIP_PIE, new Item.Properties()));
    public static final Item ROSE_HIP_PIE_SLICE = register("rose_hip_pie_slice", new Item(new Item.Properties().food(FRFoods.ROSE_HIP_PIE_SLICE)));

    public static <T extends Item> T register(String path, T item) {
        ITEMS.add(item);
        return Registry.register(BuiltInRegistries.ITEM, FarmersRespite.id(path), item);
    }
}
