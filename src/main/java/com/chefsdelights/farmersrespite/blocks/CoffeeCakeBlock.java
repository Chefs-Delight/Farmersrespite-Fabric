package com.umpaz.farmersrespite.blocks;

import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import vectorwing.farmersdelight.utils.tags.ModTags;

public class CoffeeCakeBlock extends CakeBlock {
	private final EffectType effectType;
	private final Food food;
	public final Supplier<Item> cakeSlice;

	public CoffeeCakeBlock(Food food, EffectType effectType, Supplier<Item> cakeSlice, Properties properties) {
		super(properties);
		this.effectType = effectType;
		this.food = food;
		this.cakeSlice = cakeSlice;
	}

	public ItemStack getCakeSliceItem() {
		return new ItemStack(this.cakeSlice.get());
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		ItemStack heldStack = player.getItemInHand(handIn);
		if (worldIn.isClientSide) {
			if (ModTags.KNIVES.contains(heldStack.getItem())) {
				return cutSlice(worldIn, pos, state);
			}

			if (this.eatSlice(worldIn, pos, state, player) == ActionResultType.SUCCESS) {
				return ActionResultType.SUCCESS;
			}

			if (heldStack.isEmpty()) {
				return ActionResultType.CONSUME;
			}
		}

		if (ModTags.KNIVES.contains(heldStack.getItem())) {
			return cutSlice(worldIn, pos, state);
		}
		return this.eatSlice(worldIn, pos, state, player);
	}


	private ActionResultType eatSlice(IWorld world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.canEat(false)) {
			return ActionResultType.PASS;
		} else {
			player.awardStat(Stats.EAT_CAKE_SLICE);
			player.getFoodData().eat(food.getNutrition(), food.getSaturationModifier());
			int i = state.getValue(BITES);

			for (Pair<EffectInstance, Float> pair : food.getEffects()) {
				if (!world.isClientSide() && pair.getFirst() != null && world.getRandom().nextFloat() < pair.getSecond()) {
					player.addEffect(new EffectInstance(pair.getFirst()));
				}
			}

			if (i < 6) {
				world.setBlock(pos, state.setValue(BITES, i + 1), 3);
			} else {
				world.removeBlock(pos, false);
			}

			return ActionResultType.SUCCESS;
		}
	}
	
	protected ActionResultType cutSlice(World worldIn, BlockPos pos, BlockState state) {
		int bites = state.getValue(BITES);
		if (bites < 6) {
			worldIn.setBlock(pos, state.setValue(BITES, bites + 1), 3);
		} else {
			worldIn.removeBlock(pos, false);
		}
		InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), this.getCakeSliceItem());
		worldIn.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundCategory.PLAYERS, 0.8F, 0.8F);
		return ActionResultType.SUCCESS;
	}

	public EffectType getEffectType() {
		return this.effectType;
	}
}