package com.chefsdelights.farmersrespite.core.mixin;


import com.chefsdelights.farmersrespite.common.effect.CaffeinatedEffect;
import com.chefsdelights.farmersrespite.core.registry.FREffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityCaffeinatedImmunitiesMixin {

    @Shadow
    public abstract Map<MobEffect, MobEffectInstance> getActiveEffectsMap();

    @Inject(
            at = @At("HEAD"),
            method = "canBeAffected",
            cancellable = true
    )
    private void canHaveStatusEffect(MobEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this.getActiveEffectsMap().containsKey(FREffects.CAFFEINATED) && CaffeinatedEffect.CAFFINATED_IMMUNITIES.contains(effect.getEffect())) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
