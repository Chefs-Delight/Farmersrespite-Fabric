package com.chefsdelights.farmersrespite.utils;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Map;

/**
 * Util for obtaining and formatting ITextComponents for use across the mod.
 */

public class FRTextUtils {
	private static final MutableText NO_EFFECTS = new TranslatableText("effect.none").formatted(Formatting.GRAY);

	/**
	 * Syntactic sugar for custom translation keys. Always prefixed with the mod's ID in lang files (e.g. farmersrespite.your.key.here).
	 */
	public static TranslatableText getTranslation(String key, Object... args) {
		return new TranslatableText(FarmersRespite.MOD_ID + "." + key, args);
	}

	/**
	 * An alternate version of PotionUtils.addPotionTooltip, that obtains the item's food-property potion effects instead.
	 */
	@Environment(EnvType.CLIENT)
	public static void addFoodEffectTooltip(ItemStack itemIn, List<Text> lores, float durationFactor) {
		FoodComponent stackFood = itemIn.getItem().getFoodComponent();
		if (stackFood == null) {
			return;
		}
		List<Pair<StatusEffectInstance, Float>> list = stackFood.getStatusEffects();
		List<Pair<EntityAttribute, EntityAttributeModifier>> list1 = Lists.newArrayList();
		if (list.isEmpty()) {
			lores.add(NO_EFFECTS);
		} else {
			for (Pair<StatusEffectInstance, Float> effectPair : list) {
				StatusEffectInstance instance = effectPair.getFirst();
				MutableText iformattabletextcomponent = new TranslatableText(instance.getTranslationKey());
				StatusEffect effect = instance.getEffectType();
				Map<EntityAttribute, EntityAttributeModifier> map = effect.getAttributeModifiers();
				if (!map.isEmpty()) {
					for(Map.Entry<EntityAttribute, EntityAttributeModifier> entry : map.entrySet()) {
						EntityAttributeModifier attributemodifier = entry.getValue();
						EntityAttributeModifier attributemodifier1 = new EntityAttributeModifier(attributemodifier.getName(), effect.adjustModifierAmount(instance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
						list1.add(new Pair<>(entry.getKey(), attributemodifier1));
					}
				}

				if (instance.getAmplifier() > 0) {
					iformattabletextcomponent = new TranslatableText("potion.withAmplifier", iformattabletextcomponent, new TranslatableText("potion.potency." + instance.getAmplifier()));
				}

				if (instance.getDuration() > 20) {
					iformattabletextcomponent = new TranslatableText("potion.withDuration", iformattabletextcomponent, StatusEffectUtil.durationToString(instance, durationFactor));
				}

				lores.add(iformattabletextcomponent.formatted(effect.getCategory().getFormatting()));
			}
		}

		if (!list1.isEmpty()) {
			lores.add(LiteralText.EMPTY);
			lores.add(new TranslatableText("potion.whenDrank").formatted(Formatting.DARK_PURPLE));

			for(Pair<EntityAttribute, EntityAttributeModifier> pair : list1) {
				EntityAttributeModifier attributemodifier2 = pair.getSecond();
				double d0 = attributemodifier2.getValue();
				double d1;
				if (attributemodifier2.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
					d1 = attributemodifier2.getValue();
				} else {
					d1 = attributemodifier2.getValue() * 100.0D;
				}

				if (d0 > 0.0D) {
					lores.add((new TranslatableText("attribute.modifier.plus." + attributemodifier2.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(d1), new TranslatableText(pair.getFirst().getTranslationKey()))).formatted(Formatting.BLUE));
				} else if (d0 < 0.0D) {
					d1 = d1 * -1.0D;
					lores.add((new TranslatableText("attribute.modifier.take." + attributemodifier2.getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(d1), new TranslatableText(pair.getFirst().getTranslationKey()))).formatted(Formatting.RED));
				}
			}
		}

	}
}
