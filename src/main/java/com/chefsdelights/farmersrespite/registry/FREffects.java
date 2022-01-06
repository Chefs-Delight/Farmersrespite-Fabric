package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.effects.CaffeinatedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.registry.Registry;

public class FREffects {
	public static final StatusEffect CAFFEINATED = register("caffeinated", new CaffeinatedEffect());

	public static <T extends StatusEffect> T register(String path, T effect) {
		return Registry.register(Registry.STATUS_EFFECT, FarmersRespite.id(path), effect);
	}
}