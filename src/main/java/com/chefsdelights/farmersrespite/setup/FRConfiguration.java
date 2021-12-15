package com.chefsdelights.farmersrespite.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class FRConfiguration {
	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;

	// COMMON
	public static final String CATEGORY_SETTINGS = "settings";
	public static ForgeConfigSpec.BooleanValue BONE_MEAL_TEA;
	public static ForgeConfigSpec.BooleanValue BONE_MEAL_COFFEE;

	static {
		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

		COMMON_BUILDER.comment("Game settings").push(CATEGORY_SETTINGS);
		BONE_MEAL_TEA = COMMON_BUILDER.comment("Are tea bushes bonemealable?")
				.define("enableBoneMealTeaBush", false);
		BONE_MEAL_COFFEE = COMMON_BUILDER.comment("Are coffee bushes bonemealable?")
				.define("enableBoneMealCoffeeBush", false);

		COMMON_CONFIG = COMMON_BUILDER.build();

	}

	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
	}

	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {
	}

}