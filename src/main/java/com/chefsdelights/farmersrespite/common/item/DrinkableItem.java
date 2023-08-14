package com.chefsdelights.farmersrespite.common.item;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class DrinkableItem extends ConsumableItem {
    public DrinkableItem(Item.Properties settings) {
        super(settings);
    }

    public DrinkableItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }

    public DrinkableItem(Properties properties, boolean hasPotionEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasPotionEffectTooltip, hasCustomTooltip);
    }

    public void affectConsumer(ItemStack stack, Level world, LivingEntity user) {
        Collection<MobEffect> activeStatusEffectList = user.getActiveEffectsMap().keySet();
        if (!activeStatusEffectList.isEmpty()) {
            activeStatusEffectList.stream().skip(world.getRandom().nextInt(activeStatusEffectList.size())).findFirst().ifPresent(user::removeEffect);
        }

    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(user.getItemInHand(hand));
    }

    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        MutableComponent empty = FarmersDelightMod.i18n("tooltip.milk_bottle");
        tooltip.add(empty.withStyle(ChatFormatting.BLUE));
    }
}
