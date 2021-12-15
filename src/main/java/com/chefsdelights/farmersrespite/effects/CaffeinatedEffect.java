package com.chefsdelights.farmersrespite.effects;

import java.util.Set;

import com.google.common.collect.Sets;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.registry.FREffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CaffeinatedEffect extends Effect {
	public static final Set<Effect> CAFFINATED_IMMUNITIES = Sets.newHashSet(Effects.MOVEMENT_SLOWDOWN, Effects.DIG_SLOWDOWN);

	public CaffeinatedEffect() {
		super(EffectType.BENEFICIAL, 12161815);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(Attributes.ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", (double)0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class CafeinatedEffect
	{
		@SubscribeEvent
		public static void onCaffinatedDuration(PotionEvent.PotionApplicableEvent event) {
			EffectInstance effect = event.getPotionEffect();
			LivingEntity entity = event.getEntityLiving();
			if (entity.getEffect(FREffects.CAFFEINATED.get()) != null && CAFFINATED_IMMUNITIES.contains(effect.getEffect())) {
				event.setResult(Event.Result.DENY);
			}
		}

		@SubscribeEvent
		public static void onCaffinatedApplied(PotionEvent.PotionAddedEvent event) {
			EffectInstance addedEffect = event.getPotionEffect();
			LivingEntity entity = event.getEntityLiving();
			if (addedEffect.getEffect().equals(FREffects.CAFFEINATED.get())) {
				for (Effect effect : CAFFINATED_IMMUNITIES) {
					entity.removeEffect(effect);
				}
			}
		}
	}
	
	 @Override
	    public void applyEffectTick(LivingEntity entity, int amplifier) {
	      if (this == FREffects.CAFFEINATED.get()) {
	    	if (entity instanceof ServerPlayerEntity) {
	    		ServerPlayerEntity playerMP = (ServerPlayerEntity) entity;
	            StatisticsManager statisticsManager = playerMP.getStats();
	            statisticsManager.increment(playerMP, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
	        } 
	    }
	 }

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}