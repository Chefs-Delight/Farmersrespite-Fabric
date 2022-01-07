package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.world.feature.CoffeeBushFeature;
import com.chefsdelights.farmersrespite.world.feature.WildTeaBushFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.*;

public class FRFeatures {
	public static final Feature<DefaultFeatureConfig> WILD_TEA_BUSH = register("wild_tea_bush", new WildTeaBushFeature(DefaultFeatureConfig.CODEC));
	public static final Feature<DefaultFeatureConfig> COFFEE_BUSH = register("coffee_bush", new CoffeeBushFeature(DefaultFeatureConfig.CODEC));

	public static final class Configured {
		public static final ConfiguredFeature<?, ?> WILD_TEA_BUSH_FEATURE = register("wild_tea_bush", FRFeatures.WILD_TEA_BUSH.configure(FeatureConfig.DEFAULT)
				.decorate(ConfiguredFeatures.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(18).count(3));
		public static final ConfiguredFeature<?, ?> COFFEE_BUSH_FEATURE = register("coffee_bush", FRFeatures.COFFEE_BUSH.configure(FeatureConfig.DEFAULT)
				.decorate(ConfiguredFeatures.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(50));

		private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
			return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(FarmersRespite.MOD_ID, name), configuredFeature);
		}
	}
}