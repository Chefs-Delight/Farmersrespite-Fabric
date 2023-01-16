package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.levelgen.feature.CoffeeBushFeature;
import com.chefsdelights.farmersrespite.common.levelgen.feature.WildTeaBushFeature;
import com.chefsdelights.farmersrespite.core.FRConfiguration;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.function.Supplier;


public class FRBiomeFeatures {
	public static final Supplier<Feature<SimpleBlockConfiguration>> WILD_TEA_BUSH = ()-> Registry.register(Registry.FEATURE,"wild_tea_bush", new WildTeaBushFeature(SimpleBlockConfiguration.CODEC));
	public static final Supplier<Feature<NoneFeatureConfiguration>> COFFEE_BUSH = ()-> Registry.register(Registry.FEATURE,"coffee_bush", new CoffeeBushFeature(NoneFeatureConfiguration.CODEC));

	public static final class FarmersRespiteConfiguredFeatures {

		public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> PATCH_WILD_TEA_BUSH = register(new ResourceLocation(FarmersRespite.MOD_ID, "patch_wild_tea_bush"), Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(FRBlocks.WILD_TEA_BUSH.defaultBlockState())));
		public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_COFFEE_BUSH = register(new ResourceLocation(FarmersRespite.MOD_ID, "patch_coffee_bush"), FRBiomeFeatures.COFFEE_BUSH.get(), (FeatureConfiguration.NONE));

		static Holder<PlacedFeature> registerPlacement(ResourceLocation id, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
			return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
		}
		private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, F feature, FC featureConfig) {
			return register(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, featureConfig));
		}
		private static <V extends T, T> Holder<V> register(Registry<T> registry, ResourceLocation id, V value) {
			return (Holder<V>) BuiltinRegistries.<T>register(registry, id, value);
		}
	}


	public static final class FarmersRespitePlacedFeatures {
		static int teaChance = FRConfiguration.CHANCE_TEA_BUSH.get() - 9;
		static int coffeeChance = FRConfiguration.CHANCE_COFFEE_BUSH.get() - 9;
		public static final Holder<PlacedFeature> PATCH_WILD_TEA_BUSH = FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation(FarmersRespite.MOD_ID, "patch_wild_tea_bush"), FarmersRespiteConfiguredFeatures.PATCH_WILD_TEA_BUSH, RarityFilter.onAverageOnceEvery(20 - FRConfiguration.CHANCE_TEA_BUSH.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		public static final Holder<PlacedFeature> PATCH_COFFEE_BUSH = FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation(FarmersRespite.MOD_ID, "patch_coffee_bush"), FarmersRespiteConfiguredFeatures.PATCH_COFFEE_BUSH, CountPlacement.of(FRConfiguration.CHANCE_COFFEE_BUSH.get()), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
	}
}