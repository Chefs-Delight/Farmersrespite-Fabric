package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class FRDamageSources {
    public static final ResourceKey<DamageType> WITHER_ROOTS = ResourceKey.create(Registries.DAMAGE_TYPE, FarmersRespite.id("wither_roots"));
}
