package com.chefsdelights.farmersrespite.registry;

import net.minecraft.entity.effect.StatusEffect;

public class FREffects {

	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, FarmersRespite.MODID);

	public static final StatusEffect CAFFEINATED = EFFECTS.register("caffeinated", CaffeinatedEffect::new);
}