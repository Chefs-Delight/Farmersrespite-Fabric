//package com.chefsdelights.farmersrespite.core.event;
//
//import com.farmersrespite.core.FRConfiguration;
//import com.farmersrespite.core.FarmersRespite;
//import com.farmersrespite.core.registry.FRBiomeFeatures.FarmersRespitePlacedFeatures;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.levelgen.GenerationStep;
//import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
//import net.minecraftforge.event.world.BiomeLoadingEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = FarmersRespite.MODID)
//public class FRGeneration {
//
//	public static boolean matchesKeys(ResourceLocation loc, ResourceKey<?>... keys) {
//		for (ResourceKey<?> key : keys)
//			if (key.location().equals(loc))
//				return true;
//		return false;
//	}
//
//	@SubscribeEvent
//	public static void onBiomeLoad(BiomeLoadingEvent event) {
//		ResourceLocation biome = event.getName();
//		Biome.BiomeCategory category = event.getCategory();
//		if (biome == null) return;
//		BiomeGenerationSettingsBuilder generation = event.getGeneration();
//
//		if (category.equals(Biome.BiomeCategory.SWAMP) && FRConfiguration.CHANCE_TEA_BUSH.get() > 0)
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FarmersRespitePlacedFeatures.PATCH_WILD_TEA_BUSH);
//
//		if (event.getCategory().equals(Biome.BiomeCategory.NETHER) && FRConfiguration.CHANCE_COFFEE_BUSH.get() > 0) {
//			generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FarmersRespitePlacedFeatures.PATCH_COFFEE_BUSH);
//		}
//	}
//}