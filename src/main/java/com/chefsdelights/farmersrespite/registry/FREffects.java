package com.chefsdelights.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.effects.CaffeinatedEffect;

import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FREffects {

	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, FarmersRespite.MODID);

	public static final RegistryObject<Effect> CAFFEINATED = EFFECTS.register("caffeinated", CaffeinatedEffect::new);
}