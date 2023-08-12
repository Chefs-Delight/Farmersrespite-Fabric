package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.core.registry.FREffects;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class CoffeeCakeBlock extends CakeBlock {

    public CoffeeCakeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(handIn);
        Item item = stack.getItem();
        if (stack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0) {
            Block block = Block.byItem(item);
            if (block instanceof CandleBlock) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }

                worldIn.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                worldIn.setBlockAndUpdate(pos, CoffeeCandleCakeBlock.byCandle(block, this));
                worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.awardStat(Stats.ITEM_USED.get(item));
                return InteractionResult.SUCCESS;
            }
        }

        if (worldIn.isClientSide) {
            if (stack.is(TagsRegistry.KNIVES)) {
                return cutSlice(worldIn, pos, state, player);
            }
            if (eatSlice(worldIn, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (stack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        if (stack.is(TagsRegistry.KNIVES)) {
            return cutSlice(worldIn, pos, state, player);
        }

        return eatSlice(worldIn, pos, state, player);
    }

    public InteractionResult eatSlice(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(2, 0.1F);
            if (!level.isClientSide()) {
                player.addEffect(new MobEffectInstance(FREffects.CAFFEINATED, 600, 0));
            }

            int i = state.getValue(BITES);
            level.gameEvent(player, GameEvent.EAT, pos);
            if (i < 6) {
                level.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public InteractionResult cutSlice(Level worldIn, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(BITES);
        if (bites < 6) {
            worldIn.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            worldIn.removeBlock(pos, false);
        }
        Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FRItems.COFFEE_CAKE_SLICE));
        worldIn.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }
}