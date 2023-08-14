package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.block.*;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;

import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public class FRBlocks {

    //Blocks

    // Workstations
    public static final Block KETTLE = register("kettle", new KettleBlock());

    //Tea
    public static final Block TEA_BUSH = register("tea_bush", new TeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block SMALL_TEA_BUSH = register("small_tea_bush", new SmallTeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block WILD_TEA_BUSH = register("wild_tea_bush", new WildTeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));

    //Coffee
    public static final Block COFFEE_BUSH = register("coffee_bush", new CoffeeBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block COFFEE_STEM = register("coffee_stem", new CoffeeStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block COFFEE_BUSH_TOP = register("coffee_bush_top", new CoffeeBushTopBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block COFFEE_STEM_DOUBLE = register("coffee_stem_double", new CoffeeDoubleStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block COFFEE_STEM_MIDDLE = register("coffee_stem_middle", new CoffeeMiddleStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final Block WITHER_ROOTS = register("wither_roots", new WitherRootsBlock(BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS)));
    public static final Block WITHER_ROOTS_PLANT = register("wither_roots_plant", new WitherRootsBlock(BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS)));

    //Food
    public static final Block COFFEE_CAKE = register("coffee_cake", new CoffeeCakeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL)));
    public static final Block ROSE_HIP_PIE = register("rose_hip_pie", new PieBlock(FRItems.ROSE_HIP_PIE_SLICE));
    public static final Block CANDLE_COFFEE_CAKE = register("candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block WHITE_CANDLE_COFFEE_CAKE = register("white_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.WHITE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block ORANGE_CANDLE_COFFEE_CAKE = register("orange_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block MAGENTA_CANDLE_COFFEE_CAKE = register("magenta_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block LIGHT_BLUE_CANDLE_COFFEE_CAKE = register("light_blue_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block YELLOW_CANDLE_COFFEE_CAKE = register("yellow_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block LIME_CANDLE_COFFEE_CAKE = register("lime_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.LIME_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block PINK_CANDLE_COFFEE_CAKE = register("pink_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.PINK_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block GRAY_CANDLE_COFFEE_CAKE = register("gray_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.GRAY_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block LIGHT_GRAY_CANDLE_COFFEE_CAKE = register("light_gray_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block CYAN_CANDLE_COFFEE_CAKE = register("cyan_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.CYAN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block PURPLE_CANDLE_COFFEE_CAKE = register("purple_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block BLUE_CANDLE_COFFEE_CAKE = register("blue_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.BLUE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block BROWN_CANDLE_COFFEE_CAKE = register("brown_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.BROWN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block GREEN_CANDLE_COFFEE_CAKE = register("green_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.GREEN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block RED_CANDLE_COFFEE_CAKE = register("red_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.RED_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final Block BLACK_CANDLE_COFFEE_CAKE = register("black_candle_coffee_cake", new CoffeeCandleCakeBlock(Blocks.BLACK_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));

    //Decoration
    public static final Block POTTED_TEA_BUSH = register("potted_tea_bush", new FlowerPotBlock(FRBlocks.SMALL_TEA_BUSH, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Block POTTED_WILD_TEA_BUSH = register("potted_wild_tea_bush", new FlowerPotBlock(FRBlocks.WILD_TEA_BUSH, BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final Block POTTED_COFFEE_BUSH = register("potted_coffee_bush", new FlowerPotBlock(FRBlocks.COFFEE_BUSH, BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    public static <T extends Block> T register(String path, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, FarmersRespite.id(path), block);
    }

    private static ToIntFunction<BlockState> litBlockEmission(int level) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? level : 0;
    }
}
