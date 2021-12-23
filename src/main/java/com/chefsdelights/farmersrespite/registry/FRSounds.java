package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class FRSounds {

	public static final SoundEvent BLOCK_KETTLE_WHISTLE = register("block_kettle_whistle", "block.kettle.whistle");

	private static SoundEvent register(String id, String soundID) {
		return Registry.register(Registry.SOUND_EVENT, FarmersRespite.id(id), new SoundEvent(FarmersRespite.id(soundID)));
	}
}
