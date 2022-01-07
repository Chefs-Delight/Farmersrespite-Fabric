package com.chefsdelights.farmersrespite;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.registry.FRContainerTypes;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.client.gui.KettleScreen;
import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRContainerTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vectorwing.farmersdelight.utils.ModAtlases;

@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FarmersRespiteClient {
	public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation(FarmersRespite.MODID, "item/empty_container_slot_bottle");

	@SubscribeEvent
	public static void onStitchEvent(TextureStitchEvent.Pre event) {
		ResourceLocation stitching = event.getMap().location();
		if (stitching.equals(new ResourceLocation("textures/atlas/signs.png"))) {
			event.addSprite(ModAtlases.BLANK_CANVAS_SIGN_MATERIAL.texture());
			for (RenderMaterial material : ModAtlases.DYED_CANVAS_SIGN_MATERIALS.values()) {
				event.addSprite(material.texture());
			}
		}
		if (!stitching.equals(AtlasTexture.LOCATION_BLOCKS)) {
			return;
		}
		event.addSprite(EMPTY_CONTAINER_SLOT_BOTTLE);
	}

	public static void init(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(FRBlocks.KETTLE), RenderType.cutout());
		
		RenderTypeLookup.setRenderLayer(FRBlocks.TEA_BUSH), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.SMALL_TEA_BUSH), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.WILD_TEA_BUSH), RenderType.cutout());
		
		RenderTypeLookup.setRenderLayer(FRBlocks.COFFEE_BUSH), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.COFFEE_STEM), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.COFFEE_BUSH_TOP), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.COFFEE_STEM_DOUBLE), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(FRBlocks.COFFEE_STEM_MIDDLE), RenderType.cutout());

		
		ScreenManager.register((FRContainerTypes.KETTLE), KettleScreen::new);

	}
}
