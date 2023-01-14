//package com.chefsdelights.farmersrespite.core.registry;
//
//import com.farmersrespite.common.levelgen.feature.CoffeeBushFeature;
//import com.farmersrespite.common.levelgen.feature.WildTeaBushFeature;
//import com.farmersrespite.core.FRConfiguration;
//import com.farmersrespite.core.FarmersRespite;
//import net.minecraft.core.Registry;
//import net.minecraft.data.BuiltinRegistries;
//import net.minecraft.data.worldgen.placement.PlacementUtils;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
//import net.minecraft.world.level.levelgen.feature.Feature;
//import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
//import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
//import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
//import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
//import net.minecraft.world.level.levelgen.placement.*;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;
//
//
//public class FRBiomeFeatures {
//	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FarmersRespite.MODID);
//
//	public static final RegistryObject<Feature<SimpleBlockConfiguration>> WILD_TEA_BUSH = FEATURES.register("wild_tea_bush", () -> new WildTeaBushFeature(SimpleBlockConfiguration.CODEC));
//	public static final RegistryObject<Feature<NoneFeatureConfiguration>> COFFEE_BUSH = FEATURES.register("coffee_bush", () -> new CoffeeBushFeature(NoneFeatureConfiguration.CODEC));
//
//	public static final class FarmersRespiteConfiguredFeatures {
//
//		public static final ConfiguredFeature<?, ?> PATCH_WILD_TEA_BUSH = register("patch_wild_tea_bush", Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(FRBlocks.WILD_TEA_BUSH.get().defaultBlockState()))));
//		public static final ConfiguredFeature<?, ?> PATCH_COFFEE_BUSH = register("patch_coffee_bush", FRBiomeFeatures.COFFEE_BUSH.get().configured(FeatureConfiguration.NONE));
//
//		private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
//			return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(FarmersRespite.MODID, name), configuredFeature);
//		}
//	}
//
//
//	public static final class FarmersRespitePlacedFeatures {
//		static int teaChance = FRConfiguration.CHANCE_TEA_BUSH.get() - 9;
//		static int coffeeChance = FRConfiguration.CHANCE_COFFEE_BUSH.get() - 9;
//		public static final PlacedFeature PATCH_WILD_TEA_BUSH = register("patch_wild_tea_bush", FarmersRespiteConfiguredFeatures.PATCH_WILD_TEA_BUSH.placed(RarityFilter.onAverageOnceEvery(20 - FRConfiguration.CHANCE_TEA_BUSH.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()));
//		public static final PlacedFeature PATCH_COFFEE_BUSH = register("patch_coffee_bush", FarmersRespiteConfiguredFeatures.PATCH_COFFEE_BUSH.placed(CountPlacement.of(FRConfiguration.CHANCE_COFFEE_BUSH.get()), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
//
//
//		public static PlacedFeature register(String name, PlacedFeature placedFeature) {
//			return Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(FarmersRespite.MODID, name), placedFeature);
//		}
//	}
//}