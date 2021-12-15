package com.chefsdelights.farmersrespite.setup;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.registry.FRFeatures;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersRespite.MODID)
public class FRGeneration {

	public static boolean matchesKeys(ResourceLocation loc, RegistryKey<?>... keys) {
		for (RegistryKey<?> key : keys)
			if (key.location().equals(loc))
				return true;
		return false;
	}
	
	@SubscribeEvent
	public static void onBiomeLoad(BiomeLoadingEvent event) {
		ResourceLocation biome = event.getName();
		BiomeGenerationSettingsBuilder generation = event.getGeneration();

		if (matchesKeys(biome, Biomes.SWAMP, Biomes.SWAMP_HILLS)) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FRFeatures.Configured.WILD_TEA_BUSH_FEATURE);
		}
		if (matchesKeys(biome, Biomes.BASALT_DELTAS)) {
			generation.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, FRFeatures.Configured.COFFEE_BUSH_FEATURE);

		}
	}
}