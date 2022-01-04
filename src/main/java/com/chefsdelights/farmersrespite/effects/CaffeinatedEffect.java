package com.chefsdelights.farmersrespite.effects;

import com.chefsdelights.farmersrespite.registry.FREffects;
import com.google.common.collect.Sets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;

import java.util.Objects;
import java.util.Set;

public class CaffeinatedEffect extends StatusEffect {
	public static final Set<StatusEffect> CAFFINATED_IMMUNITIES = Sets.newHashSet(StatusEffects.SLOWNESS, StatusEffects.MINING_FATIGUE);

	public CaffeinatedEffect() {
		super(StatusEffectCategory.BENEFICIAL, 12161815);
		addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.1F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.5F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);
		Objects.requireNonNull(entity);
		CAFFINATED_IMMUNITIES.forEach(entity::removeStatusEffect);
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (this == FREffects.CAFFEINATED) {
			if (entity instanceof ServerPlayerEntity player) {
				StatHandler statHandler = player.getStatHandler();
				statHandler.increaseStat(player, Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
			}
		}
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
}