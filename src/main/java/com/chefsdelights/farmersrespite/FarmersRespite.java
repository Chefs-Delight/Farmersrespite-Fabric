package com.chefsdelights.farmersrespite;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.setup.ClientEventHandler;
import com.chefsdelights.farmersrespite.setup.CommonEventHandler;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FarmersRespite {
	public static final String MOD_ID = "farmersrespite";
	public static final ItemGroup GROUP = FabricItemGroupBuilder.create(
					new Identifier(MOD_ID, "group"))
			.icon(() -> new ItemStack(FRBlocks.KETTLE))
			.build();

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

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().register(KettleRecipe.SERIALIZER);
	}
}
