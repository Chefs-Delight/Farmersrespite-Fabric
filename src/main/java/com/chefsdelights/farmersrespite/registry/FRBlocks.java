package com.chefsdelights.farmersrespite.registry;

import com.nhoryzon.mc.farmersdelight.block.PieBlock;
import com.umpaz.farmersrespite.blocks.CoffeeBushBlock;
import com.umpaz.farmersrespite.blocks.CoffeeBushTopBlock;
import com.umpaz.farmersrespite.blocks.CoffeeCakeBlock;
import com.umpaz.farmersrespite.blocks.CoffeeDoubleStemBlock;
import com.umpaz.farmersrespite.blocks.CoffeeMiddleStemBlock;
import com.umpaz.farmersrespite.blocks.CoffeeStemBlock;
import com.umpaz.farmersrespite.blocks.KettleBlock;
import com.umpaz.farmersrespite.blocks.SmallTeaBushBlock;
import com.umpaz.farmersrespite.blocks.TeaBushBlock;
import com.umpaz.farmersrespite.blocks.WildTeaBushBlock;

import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.BlockSoundGroup;

public class FRBlocks {

	// Workstations
	public static final Block KETTLE = HELPER.register("kettle", KettleBlock::new);

	//Tea
	public static final Block TEA_BUSH = HELPER.register("tea_bush", () -> new TeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block SMALL_TEA_BUSH = HELPER.register("small_tea_bush", () -> new SmallTeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block WILD_TEA_BUSH = HELPER.register("wild_tea_bush", () -> new WildTeaBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));

	//Coffee
	public static final Block COFFEE_BUSH = HELPER.register("coffee_bush", () -> new CoffeeBushBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM = HELPER.register("coffee_stem", () -> new CoffeeStemBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_BUSH_TOP = HELPER.register("coffee_bush_top", () -> new CoffeeBushTopBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM_DOUBLE = HELPER.register("coffee_stem_double", () -> new CoffeeDoubleStemBlock(Block.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
	public static final Block COFFEE_STEM_MIDDLE = HELPER.register("coffee_stem_middle", () -> new CoffeeMiddleStemBlock(AbstractBlock.Settings.of(Material.PLANT).breakInstantly().sounds(BlockSoundGroup.GRASS).nonOpaque()));

	//Food
	public static final Block COFFEE_CAKE = HELPER.register("coffee_cake", () -> new CoffeeCakeBlock(FRItems.Foods.COFFEE_CAKE, StatusEffectCategory.BENEFICIAL, FRItems.COFFEE_CAKE_SLICE, AbstractBlock.Settings.of(Material.CAKE, MapColor.BROWN).strength(0.5F).sounds(BlockSoundGroup.WOOL)));
	public static final Block ROSE_HIP_PIE = HELPER.register("rose_hip_pie", () -> new PieBlock(AbstractBlock.Settings.copy(Blocks.CAKE), FRItems.ROSE_HIP_PIE_SLICE));

	
}
