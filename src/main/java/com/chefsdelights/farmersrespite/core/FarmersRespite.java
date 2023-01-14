package com.chefsdelights.farmersrespite.core;

import com.chefsdelights.farmersrespite.core.event.FRCommonSetup;
import com.chefsdelights.farmersrespite.core.registry.*;
import com.google.common.reflect.Reflection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.BusBuilder;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmersRespite implements ModInitializer {
	public static final String MOD_ID = "farmersrespite";
	public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.create(
					new ResourceLocation(MOD_ID, "group"))
			.icon(() -> new ItemStack(FRBlocks.KETTLE))
			.build();

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping()
			.create();
	public static final Gson FD_GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

	@Override
	@SuppressWarnings("UnstableApiUsage")
	public void onInitialize() {
		Reflection.initialize(
				FRItems.class,
				FRBlocks.class,
				FRRecipeSerializers.class,
				FRBlockEntityTypes.class,
				FRContainerTypes.class,
				FRSounds.class
		);

		FRCommonSetup.init();
		ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static TranslatableComponent i18n(String key, Object... args) {
		return new TranslatableComponent(MOD_ID + "." + key, args);
	}
}
