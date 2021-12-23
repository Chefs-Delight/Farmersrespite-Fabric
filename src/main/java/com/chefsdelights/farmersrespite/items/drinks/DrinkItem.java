package com.chefsdelights.farmersrespite.items.drinks;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.apache.http.util.TextUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DrinkItem extends ConsumableItem {
    private static final MutableText NO_EFFECTS;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;

    public DrinkItem(Settings settings) {
        super(settings);
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public DrinkItem(Settings settings, boolean hasPotionEffectTooltip, boolean hasCustomTooltip) {
        super(settings);
        this.hasFoodEffectTooltip = hasPotionEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return ItemUsage.consumeHeldItem(worldIn, playerIn, handIn);
    }

    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.hasCustomTooltip) {
            TranslatableText textEmpty = FarmersDelightMod.i18n("tooltip." + this, new Object[0]);
            tooltip.add(textEmpty.formatted(Formatting.BLUE));
        }

        if (this.hasFoodEffectTooltip) {
            addFoodEffectTooltip(stack, tooltip, 1.0F);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void addFoodEffectTooltip(ItemStack itemIn, List<Text> lores, float durationFactor) {
        FoodComponent stackFood = itemIn.getItem().getFoodComponent();
        if (stackFood != null) {
            List<Pair<StatusEffectInstance, Float>> list = stackFood.getStatusEffects();
            List<Pair<EntityAttribute, EntityAttributeModifier>> list1 = Lists.newArrayList();
            Iterator var6;
            Pair pair;
            TranslatableText iformattabletextcomponent;
            StatusEffect effect;
            if (list.isEmpty()) {
                lores.add(NO_EFFECTS);
            } else {
                for(var6 = list.iterator(); var6.hasNext(); lores.add(iformattabletextcomponent.formatted(effect.getCategory().getFormatting()))) {
                    pair = (Pair)var6.next();
                    StatusEffectInstance instance = (StatusEffectInstance)pair.getFirst();
                    iformattabletextcomponent = new TranslatableText(instance.getTranslationKey());
                    effect = instance.getEffectType();
                    Map<EntityAttribute, EntityAttributeModifier> map = effect.getAttributeModifiers();
                    if (!map.isEmpty()) {
                        Iterator var12 = map.entrySet().iterator();

                        while(var12.hasNext()) {
                            Map.Entry<EntityAttribute, EntityAttributeModifier> entry = (Map.Entry)var12.next();
                            EntityAttributeModifier attributemodifier = (EntityAttributeModifier)entry.getValue();
                            EntityAttributeModifier attributemodifier1 = new EntityAttributeModifier(attributemodifier.getName(), effect.adjustModifierAmount(instance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                            list1.add(new Pair(entry.getKey(), attributemodifier1));
                        }
                    }

                    if (instance.getAmplifier() > 0) {
                        iformattabletextcomponent = new TranslatableText("potion.withAmplifier", new Object[]{iformattabletextcomponent, new TranslatableText("potion.potency." + instance.getAmplifier())});
                    }

                    if (instance.getDuration() > 20) {
                        iformattabletextcomponent = new TranslatableText("potion.withDuration", new Object[]{iformattabletextcomponent, StatusEffectUtil.durationToString(instance, durationFactor)});
                    }
                }
            }

            if (!list1.isEmpty()) {
                lores.add(LiteralText.EMPTY);
                lores.add((new TranslatableText("potion.whenDrank")).formatted(Formatting.DARK_PURPLE));
                var6 = list1.iterator();

                while(var6.hasNext()) {
                    pair = (Pair)var6.next();
                    EntityAttributeModifier attributemodifier2 = (EntityAttributeModifier)pair.getSecond();
                    double d0 = attributemodifier2.getValue();
                    double d1;
                    if (attributemodifier2.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
                        d1 = attributemodifier2.getValue();
                    } else {
                        d1 = attributemodifier2.getValue() * 100.0D;
                    }

                    if (d0 > 0.0D) {
                        lores.add((new TranslatableText("attribute.modifier.plus." + attributemodifier2.getOperation().getId(), new Object[]{ItemStack.MODIFIER_FORMAT.format(d1), new TranslatableText(((EntityAttribute)pair.getFirst()).getTranslationKey())})).formatted(Formatting.BLUE));
                    } else if (d0 < 0.0D) {
                        d1 *= -1.0D;
                        lores.add((new TranslatableText("attribute.modifier.take." + attributemodifier2.getOperation().getId(), new Object[]{ItemStack.MODIFIER_FORMAT.format(d1), new TranslatableText(((EntityAttribute)pair.getFirst()).getTranslationKey())})).formatted(Formatting.RED));
                    }
                }
            }

        }
    }
    static {
        NO_EFFECTS = (new TranslatableText("effect.none")).formatted(Formatting.GRAY);
    }
}
