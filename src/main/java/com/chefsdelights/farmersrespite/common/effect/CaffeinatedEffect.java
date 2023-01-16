package com.chefsdelights.farmersrespite.common.effect;

import com.chefsdelights.farmersrespite.core.registry.FREffects;
import com.google.common.collect.Sets;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.Objects;
import java.util.Set;

public class CaffeinatedEffect extends MobEffect {
    public static final Set<MobEffect> CAFFINATED_IMMUNITIES = Sets.newHashSet(MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN);

    public CaffeinatedEffect() {
        super(MobEffectCategory.BENEFICIAL, 12161815);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        super.addAttributeModifiers(entity, attributes, amplifier);
        Objects.requireNonNull(entity);
        CAFFINATED_IMMUNITIES.forEach(entity::removeEffect);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (this == FREffects.CAFFEINATED) {
            if (entity instanceof ServerPlayer player) {
                StatsCounter statHandler = player.getStats();
                statHandler.increment(player, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}