package com.chefsdelights.farmersrespite.items;

import java.util.ArrayList;
import java.util.Iterator;

import com.chefsdelights.farmersrespite.items.drinks.DrinkItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class PurulentTeaItem extends DrinkItem {
	public PurulentTeaItem(Item.Settings settings) {
		super(settings, true, true);
	}
	
	@Override
	public void affectConsumer(ItemStack stack, World worldIn, LivingEntity consumer) {
		Iterator<StatusEffectInstance> itr = consumer.getActiveEffects().iterator();
		ArrayList<StatusEffect> compatibleEffects = new ArrayList<>();

		while (itr.hasNext()) {
			StatusEffectInstance effect = itr.next();
			if (effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
				compatibleEffects.add(effect.getEffectType());
			}
		}

		if (compatibleEffects.size() > 0) {
			StatusEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(worldIn.random.nextInt(compatibleEffects.size())));
			if (selectedEffect != null && !net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(consumer, selectedEffect))) {
				consumer.addEffect(new StatusEffectInstance(selectedEffect.getEffectType(), selectedEffect.getDuration() + 400, selectedEffect.getAmplifier(), selectedEffect.isAmbient(), selectedEffect.isVisible(), selectedEffect.showIcon()));
			}
		}
	}
}