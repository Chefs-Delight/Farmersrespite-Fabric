package com.chefsdelights.farmersrespite.common.item;

import io.github.fabricators_of_create.porting_lib.extensions.MobEffectInstanceExtensions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;

public class PurulentTeaItem extends DrinkableItem {
	private final int effectBoost;

	public PurulentTeaItem(int effectBoost, Properties properties) {
		super(properties, true, true);
		this.effectBoost = effectBoost;
	}

	@Override
	public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
		Iterator<MobEffectInstance> itr = consumer.getActiveEffects().iterator();
		ArrayList<MobEffect> compatibleEffects = new ArrayList<>();
		while (itr.hasNext()) {
			MobEffectInstance effect = itr.next();
			if (effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
				compatibleEffects.add(effect.getEffect());
			}
		}

		if (compatibleEffects.size() > 0) {
			MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(worldIn.random.nextInt(compatibleEffects.size())));
			if (selectedEffect != null) {
				consumer.addEffect(new MobEffectInstance(selectedEffect.getEffect(), selectedEffect.getDuration() + effectBoost, selectedEffect.getAmplifier(), selectedEffect.isAmbient(), selectedEffect.isVisible(), selectedEffect.showIcon()));
			}
		}
	}
}
