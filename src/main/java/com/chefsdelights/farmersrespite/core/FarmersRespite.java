package com.chefsdelights.farmersrespite.core;

import com.chefsdelights.farmersrespite.core.event.FRCommonSetup;
import com.chefsdelights.farmersrespite.core.event.FRGeneration;
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
import net.minecraftforge.api.ModLoadingContext;
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
				FREffects.class,
				FRBlockEntityTypes.class,
				FRContainerTypes.class,
				FRSounds.class,
				FRBiomeFeatures.class

		);

		FRCommonSetup.init();
		FRGeneration.init();

		//registerVillagerTradeOffer();
		ModLoadingContext.registerConfig(MOD_ID, ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static TranslatableComponent i18n(String key, Object... args) {
		return new TranslatableComponent(MOD_ID + "." + key, args);
	}

	protected void registerVillagerTradeOffer() {
//		if (FarmersDelightMod.CONFIG.isFarmersBuyFDCrops()) {
//			TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
//					factories -> new MerchantOffer(new ItemStack(FRItems., 26)));
//		}
	}
}