package com.umpaz.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FRSounds {
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FarmersRespite.MODID);

	public static final RegistryObject<SoundEvent> BLOCK_KETTLE_WHISTLE = SOUNDS.register("block.kettle.whistle",
			() -> new SoundEvent(new ResourceLocation(FarmersRespite.MODID, "block.kettle.whistle")));

}
