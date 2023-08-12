package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

public class FRSounds {
    public static final SoundEvent BLOCK_KETTLE_WHISTLE = register("block_kettle_whistle", "block.kettle.whistle");

    private static SoundEvent register(String id, String soundID) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, FarmersRespite.id(id), SoundEvent.createVariableRangeEvent(FarmersRespite.id(soundID)));
    }
}