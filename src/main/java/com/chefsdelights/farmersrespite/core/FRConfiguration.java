package com.chefsdelights.farmersrespite.core;

import net.minecraftforge.common.ForgeConfigSpec;

public class FRConfiguration {
	public static ForgeConfigSpec COMMON_CONFIG;

	// COMMON
	public static final String CATEGORY_SETTINGS = "settings";
	public static ForgeConfigSpec.BooleanValue BONE_MEAL_TEA;
	public static ForgeConfigSpec.BooleanValue BONE_MEAL_COFFEE;
	public static ForgeConfigSpec.IntValue CHANCE_TEA_BUSH;
	public static ForgeConfigSpec.IntValue CHANCE_COFFEE_BUSH;

	static {
		ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

		COMMON_BUILDER.comment("Game settings").push(CATEGORY_SETTINGS);
		BONE_MEAL_TEA = COMMON_BUILDER.comment("Are tea bushes bonemealable?")
				.define("enableBoneMealTeaBush", false);
		BONE_MEAL_COFFEE = COMMON_BUILDER.comment("Are coffee bushes bonemealable?")
				.define("enableBoneMealCoffeeBush", false);

		CHANCE_TEA_BUSH = COMMON_BUILDER.comment("Chance of generating wild tea bushes. Bigger value = more frequent.")
				.defineInRange("teaBushChance", 10, 0, 20);
		CHANCE_COFFEE_BUSH = COMMON_BUILDER.comment("Chance of generating coffee bushes. Bigger value = more frequent.")
				.defineInRange("coffeeBushChance", 10, 0, 20);

		COMMON_CONFIG = COMMON_BUILDER.build();

	}
}
