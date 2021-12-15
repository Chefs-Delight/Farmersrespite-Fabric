package com.chefsdelights.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.world.feature.CoffeeBushFeature;
import com.umpaz.farmersrespite.world.feature.WildTeaBushFeature;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FRFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FarmersRespite.MODID);

	public static final RegistryObject<Feature<NoFeatureConfig>> WILD_TEA_BUSH = FEATURES.register("wild_tea_bush", () -> new WildTeaBushFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> COFFEE_BUSH = FEATURES.register("coffee_bush", () -> new CoffeeBushFeature(NoFeatureConfig.CODEC));

	public static final class Configured {
		public static final ConfiguredFeature<?, ?> WILD_TEA_BUSH_FEATURE = register("wild_tea_bush", FRFeatures.WILD_TEA_BUSH.get().configured(IFeatureConfig.NONE)
				.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(18).count(3));
		public static final ConfiguredFeature<?, ?> COFFEE_BUSH_FEATURE = register("coffee_bush", FRFeatures.COFFEE_BUSH.get().configured(IFeatureConfig.NONE)
				.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(50));

		private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
			return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(FarmersRespite.MODID, name), configuredFeature);
		}
	}
}
