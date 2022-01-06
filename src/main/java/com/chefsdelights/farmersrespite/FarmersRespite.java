package com.chefsdelights.farmersrespite;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.setup.FRConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarmersRespite implements ModInitializer {
	public static final String MOD_ID = "farmersrespite";

	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "group"))
			.icon(() -> new ItemStack(FRBlocks.KETTLE))
			.build();

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping()
			.create();

	@Override
	public void onInitialize() {
		ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static TranslatableText i18n(String key, Object... args) {
		return new TranslatableText(MOD_ID + "." + key, args);
	}
}