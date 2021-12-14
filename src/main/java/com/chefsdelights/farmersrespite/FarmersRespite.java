package com.umpaz.farmersrespite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.umpaz.farmersrespite.crafting.KettleRecipe;
import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRContainerTypes;
import com.umpaz.farmersrespite.registry.FREffects;
import com.umpaz.farmersrespite.registry.FRFeatures;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.registry.FRRecipeSerializers;
import com.umpaz.farmersrespite.registry.FRSounds;
import com.umpaz.farmersrespite.registry.FRTileEntityTypes;
import com.umpaz.farmersrespite.setup.ClientEventHandler;
import com.umpaz.farmersrespite.setup.CommonEventHandler;
import com.umpaz.farmersrespite.setup.FRConfiguration;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FarmersRespite.MODID)
@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmersRespite
{
	public static final String MODID = "farmersrespite";
	public static final FRItemGroup ITEM_GROUP = new FRItemGroup(FarmersRespite.MODID);

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping()
			.create();

	public FarmersRespite() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(CommonEventHandler::init);
		modEventBus.addListener(ClientEventHandler::init);
		modEventBus.addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);


		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);
		
		FRItems.ITEMS.register(modEventBus);
		FRBlocks.BLOCKS.register(modEventBus);
		FREffects.EFFECTS.register(modEventBus);
		FRFeatures.FEATURES.register(modEventBus);
		FRTileEntityTypes.TILES.register(modEventBus);
		FRSounds.SOUNDS.register(modEventBus);
		FRContainerTypes.CONTAINER_TYPES.register(modEventBus);
		FRRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().register(KettleRecipe.SERIALIZER);
	}
}
