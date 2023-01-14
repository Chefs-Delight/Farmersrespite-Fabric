//package com.chefsdelights.farmersrespite.common.effect;
//
//import com.chefsdelights.farmersrespite.core.registry.FREffects;
//import com.farmersrespite.core.FarmersRespite;
//import com.farmersrespite.core.registry.FREffects;
//import com.google.common.collect.Sets;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.stats.Stats;
//import net.minecraft.stats.StatsCounter;
//import net.minecraft.world.effect.MobEffect;
//import net.minecraft.world.effect.MobEffectCategory;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraftforge.event.entity.living.PotionEvent;
//import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.util.Set;
//
//public class CaffeinatedEffect extends MobEffect {
//	public static final Set<MobEffect> CAFFINATED_IMMUNITIES = Sets.newHashSet(MobEffects.DIG_SLOWDOWN);
//
//	public CaffeinatedEffect() {
//		super(MobEffectCategory.BENEFICIAL, 12161815);
//		addAttributeModifier(Attributes.MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
//		addAttributeModifier(Attributes.ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
//	}
//
//	public static class CafeinatedEffect {
//		@SubscribeEvent
//		public static void onCaffinatedDuration(PotionEvent.PotionApplicableEvent event) {
//			MobEffectInstance effect = event.getPotionEffect();
//			LivingEntity entity = event.getEntityLiving();
//			if (entity.getEffect(FREffects.CAFFEINATED) != null && CAFFINATED_IMMUNITIES.contains(effect.getEffect())) {
//				event.setResult(Event.Result.DENY);
//			}
//		}
//
//		@SubscribeEvent
//		public static void onCaffinatedApplied(Event event) {
//			MobEffectInstance addedEffect = event.getPotionEffect();
//			LivingEntity entity = event.getEntityLiving();
//			if (addedEffect.getEffect().equals(FREffects.CAFFEINATED)) {
//				for (MobEffect effect : CAFFINATED_IMMUNITIES) {
//					entity.removeEffect(effect);
//				}
//			}
//		}
//	}
//
//	 @Override
//	    public void applyEffectTick(LivingEntity entity, int amplifier) {
//	      if (this == FREffects.CAFFEINATED) {
//	    	if (entity instanceof ServerPlayer) {
//	    		ServerPlayer playerMP = (ServerPlayer) entity;
//	            StatsCounter statisticsManager = playerMP.getStats();
//	            statisticsManager.increment(playerMP, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
//	        }
//	    }
//	 }
//
//	@Override
//	public boolean isDurationEffectTick(int duration, int amplifier) {
//		return true;
//	}
//}