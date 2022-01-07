package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.blocks.*;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.registry.Registry;

public class FRBlocks {
	// Workstations
	public static final Block KETTLE = register("kettle", new KettleBlock());

	//Tea
	public static final Block TEA_BUSH = register("tea_bush", new TeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block SMALL_TEA_BUSH = register("small_tea_bush", new SmallTeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block WILD_TEA_BUSH = register("wild_tea_bush", new WildTeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));

	//Coffee
	public static final Block COFFEE_BUSH = register("coffee_bush", new CoffeeBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM = register("coffee_stem", new CoffeeStemBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_BUSH_TOP = register("coffee_bush_top", new CoffeeBushTopBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM_DOUBLE = register("coffee_stem_double", new CoffeeDoubleStemBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM_MIDDLE = register("coffee_stem_middle", new CoffeeMiddleStemBlock(AbstractBlock.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));

	//Food
	public static final Block COFFEE_CAKE = register("coffee_cake", new CoffeeCakeBlock(AbstractBlock.Settings.of(Material.CAKE, MapColor.BROWN).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
	public static final Block ROSE_HIP_PIE = register("rose_hip_pie", new PieBlock(FRItems.ROSE_HIP_PIE_SLICE));

	public static <T extends Block> T register(String path, T block, ItemGroup group) {
		Registry.register(Registry.BLOCK, FarmersRespite.id(path), block);
		Registry.register(Registry.ITEM, FarmersRespite.id(path), new BlockItem(block, new FabricItemSettings().group(group)));

		return block;
	}

	public static <T extends Block> T register(String path, T block) {
		return Registry.register(Registry.BLOCK, FarmersRespite.id(path), block);
	}
}