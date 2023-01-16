package com.chefsdelights.farmersrespite.core.event;

import com.chefsdelights.farmersrespite.core.registry.FRBiomeFeatures;
import com.nhoryzon.mc.farmersdelight.registry.ConfiguredFeaturesRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FRGeneration {

	public static void init() {
		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.SWAMP), GenerationStep.Decoration.VEGETAL_DECORATION,
				resourceKey(FRBiomeFeatures.FarmersRespitePlacedFeatures.PATCH_WILD_TEA_BUSH.value()));
		BiomeModifications.addFeature(context -> context.getBiomeKey().equals(Biomes.BASALT_DELTAS), GenerationStep.Decoration.VEGETAL_DECORATION,
				resourceKey(FRBiomeFeatures.FarmersRespitePlacedFeatures.PATCH_COFFEE_BUSH.value()));
	}

	public static ResourceKey<PlacedFeature> resourceKey(PlacedFeature carver) {
		return BuiltinRegistries.PLACED_FEATURE.getResourceKey(carver).get();
	}
}