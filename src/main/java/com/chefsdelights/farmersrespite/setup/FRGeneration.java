package com.chefsdelights.farmersrespite.setup;

import com.chefsdelights.farmersrespite.registry.FRFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;

public class FRGeneration {
	public static void onBiomeLoad() {
		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP), GenerationStep.Feature.VEGETAL_DECORATION, rk(FRFeatures.Configured.WILD_TEA_BUSH_FEATURE));
		if (matchesKeys(biome, Biomes.BASALT_DELTAS)) {
			generation.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, FRFeatures.Configured.COFFEE_BUSH_FEATURE);
		}
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> rk(ConfiguredFeature carver) {
		return BuiltinRegistries.CONFIGURED_FEATURE.getKey(carver).get();
	}
}