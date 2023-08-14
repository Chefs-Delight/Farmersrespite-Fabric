package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.effect.CaffeinatedEffect;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class FREffects {
    public static final MobEffect CAFFEINATED = register("caffeinated", new CaffeinatedEffect());

    public static <T extends MobEffect> T register(String path, T effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, FarmersRespite.id(path), effect);
    }
}