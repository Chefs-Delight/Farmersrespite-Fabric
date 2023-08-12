package com.chefsdelights.farmersrespite.common.item;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ConsumableItem extends Item {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;

    private static final MutableComponent NO_EFFECTS = (Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY);

    public ConsumableItem(Item.Properties settings) {
        super(settings);
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public ConsumableItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
    }

    public ConsumableItem(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide()) {
            this.affectConsumer(stack, world, user);
        }

        ItemStack container = new ItemStack(stack.getItem().getCraftingRemainingItem());
        Player player;
        if (stack.isEdible()) {
            super.finishUsingItem(stack, world, user);
        } else if (user instanceof Player) {
            player = (Player) user;
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (stack.isEmpty()) {
            return container;
        } else {
            if (user instanceof Player) {
                player = (Player) user;
                if (!player.getAbilities().instabuild && !player.getInventory().add(container)) {
                    player.drop(container, false);
                }
            }

            return stack;
        }
    }

    public void affectConsumer(ItemStack stack, Level world, LivingEntity user) {
    }

    @Override
    @Environment(value = EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);
        if (hasCustomTooltip) {
            tooltip.add(FarmersRespite.i18n("tooltip." + this).withStyle(ChatFormatting.BLUE));
        }
        if (hasFoodEffectTooltip) {
            addFoodEffectTooltip(stack, tooltip, 1.f);
        }
    }

    @Environment(value = EnvType.CLIENT)
    public static void addFoodEffectTooltip(ItemStack itemIn, List<Component> lores, float durationFactor) {
        FoodProperties foodStats = itemIn.getItem().getFoodProperties();
        if (foodStats == null) {
            return;
        }
        List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
        List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
        if (effectList.isEmpty()) {
            lores.add(NO_EFFECTS);
        } else {
            for (Pair<MobEffectInstance, Float> effectPair : effectList) {
                MobEffectInstance instance = effectPair.getFirst();
                MutableComponent iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
                MobEffect effect = instance.getEffect();
                Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                if (!attributeMap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                        AttributeModifier rawModifier = entry.getValue();
                        AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue
                                (instance.getAmplifier(), rawModifier), rawModifier.getOperation());
                        attributeList.add(new Pair<>(entry.getKey(), modifier));
                    }
                }

                if (instance.getAmplifier() > 0) {
                    iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier()));
                }

                if (instance.getDuration() > 20) {
                    iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration
                            (instance, durationFactor));
                }

                lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        if (!attributeList.isEmpty()) {
            lores.add(Component.nullToEmpty(""));
            lores.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : attributeList) {
                AttributeModifier modifier = pair.getSecond();
                double amount = modifier.getAmount();
                double formattedAmount;
                if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    formattedAmount = modifier.getAmount();
                } else {
                    formattedAmount = modifier.getAmount() * 100.d;
                }

                if (amount > 0.0D) {
                    lores.add((Component.translatable("attribute.modifier.plus." + modifier.getOperation().name(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
                } else if (amount < 0.0D) {
                    formattedAmount = formattedAmount * -1.d;
                    lores.add((Component.translatable("attribute.modifier.take." + modifier.getOperation().name(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
                }
            }
        }

    }
}
