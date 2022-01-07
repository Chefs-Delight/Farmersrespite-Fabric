package com.chefsdelights.farmersrespite.items;

import java.util.*;

import com.chefsdelights.farmersrespite.items.drinks.DrinkItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PurulentTeaItem extends DrinkItem {
	public PurulentTeaItem(Item.Settings settings) {
		super(settings, true, true);
	}
	
	@Override
	public void affectConsumer(ItemStack stack, World world, LivingEntity user) {
		Collection<StatusEffect> activeStatusEffectList = user.getActiveStatusEffects().keySet();

		if (!activeStatusEffectList.isEmpty()) {
			Optional<StatusEffect> firstStatusEffect = activeStatusEffectList.stream().skip(world.getRandom().nextInt(activeStatusEffectList.size())).findFirst();
			firstStatusEffect.ifPresent(l -> user.addStatusEffect(new StatusEffectInstance(user.getStatusEffect(l).getEffectType(), user.getStatusEffect(l).getDuration() + 400, user.getStatusEffect(l).getAmplifier(), user.getStatusEffect(l).isAmbient(), user.getStatusEffect(l).shouldShowParticles(), user.getStatusEffect(l).shouldShowIcon())));
		}
	}
}