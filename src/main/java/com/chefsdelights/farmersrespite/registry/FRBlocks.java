package com.umpaz.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
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

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.blocks.PieBlock;

public class FRBlocks
{
	//Blocks
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmersRespite.MODID);

	// Workstations
	public static final RegistryObject<Block> KETTLE = BLOCKS.register("kettle", KettleBlock::new);

	//Tea
	public static final RegistryObject<Block> TEA_BUSH = BLOCKS.register("tea_bush", () -> new TeaBushBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> SMALL_TEA_BUSH = BLOCKS.register("small_tea_bush", () -> new SmallTeaBushBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> WILD_TEA_BUSH = BLOCKS.register("wild_tea_bush", () -> new WildTeaBushBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));

	//Coffee
	public static final RegistryObject<Block> COFFEE_BUSH = BLOCKS.register("coffee_bush", () -> new CoffeeBushBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> COFFEE_STEM = BLOCKS.register("coffee_stem", () -> new CoffeeStemBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> COFFEE_BUSH_TOP = BLOCKS.register("coffee_bush_top", () -> new CoffeeBushTopBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> COFFEE_STEM_DOUBLE = BLOCKS.register("coffee_stem_double", () -> new CoffeeDoubleStemBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
	public static final RegistryObject<Block> COFFEE_STEM_MIDDLE = BLOCKS.register("coffee_stem_middle", () -> new CoffeeMiddleStemBlock(Block.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));

	//Food
	public static final RegistryObject<Block> COFFEE_CAKE = BLOCKS.register("coffee_cake", () -> new CoffeeCakeBlock(FRItems.Foods.COFFEE_CAKE, EffectType.BENEFICIAL, FRItems.COFFEE_CAKE_SLICE, AbstractBlock.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL)));
	public static final RegistryObject<Block> ROSE_HIP_PIE = BLOCKS.register("rose_hip_pie", () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), FRItems.ROSE_HIP_PIE_SLICE));

	
}
