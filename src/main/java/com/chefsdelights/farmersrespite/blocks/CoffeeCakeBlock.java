package com.chefsdelights.farmersrespite.blocks;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

//TODO: Replace this with a mixin in KnivesEventListener
//public class CoffeeCakeBlock extends CakeBlock {
//	private final FoodComponent foodComponent;
//	public final Item cakeSlice;
//
//	public CoffeeCakeBlock(FoodComponent foodComponent, Item cakeSlice, Settings settings) {
//		super(settings);
//		this.foodComponent = foodComponent;
//		this.cakeSlice = cakeSlice;
//	}
//
//	@Override
//	public ActionResult use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
//		ItemStack heldStack = player.getStackInHand(handIn);
//		if (worldIn.isClient) {
//			if (this.eatSlice(worldIn, pos, state, player) == ActionResult.SUCCESS) {
//				return ActionResult.SUCCESS;
//			}
//
//			if (heldStack.isEmpty()) {
//				return ActionResult.CONSUME;
//			}
//		}
//
//		return this.eatSlice(worldIn, pos, state, player);
//	}
//
//
//	private ActionResult eatSlice(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
//		if (!player.canConsume(false)) {
//			return ActionResult.PASS;
//		} else {
//			player.increaseStat(Stats.EAT_CAKE_SLICE, 1);
//			player.getHungerManager().add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
//			int i = state.get(BITES);
//
//			for (Pair<StatusEffectInstance, Float> pair : foodComponent.getStatusEffects()) {
//				if (!world.isClient() && pair.getFirst() != null && world.getRandom().nextFloat() < pair.getSecond()) {
//					player.addStatusEffect(new StatusEffectInstance(pair.getFirst()));
//				}
//			}
//
//			if (i < 6) {
//				world.setBlockState(pos, state.with(BITES, i + 1), 3);
//			} else {
//				world.removeBlock(pos, false);
//			}
//
//			return ActionResult.SUCCESS;
//		}
//	}
//}