package com.chefsdelights.farmersrespite.mixin;

import com.chefsdelights.farmersrespite.effects.CaffeinatedEffect;
import com.chefsdelights.farmersrespite.registry.FREffects;
import com.nhoryzon.mc.farmersdelight.effect.ComfortEffect;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityCaffeinatedImmunitiesMixin {
    @Shadow
    public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Inject(
            at = @At("HEAD"),
            method = "canHaveStatusEffect",
            cancellable = true
    )
    private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (this.getActiveStatusEffects().containsKey(FREffects.CAFFEINATED) && CaffeinatedEffect.CAFFINATED_IMMUNITIES.contains(effect.getEffectType())) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}