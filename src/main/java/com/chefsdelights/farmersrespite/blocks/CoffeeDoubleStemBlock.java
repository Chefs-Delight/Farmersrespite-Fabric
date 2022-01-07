package com.chefsdelights.farmersrespite.blocks;

import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.setup.FRConfiguration;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CoffeeDoubleStemBlock extends PlantBlock implements Fertilizable {
	public static final VoxelShape SHAPE;

	public static final IntProperty AGE;
	public static final IntProperty AGE1;
	public static final DirectionProperty FACING;

	public CoffeeDoubleStemBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(AGE1, 0).with(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(Blocks.BASALT) || floor.isOf(Blocks.POLISHED_BASALT) || floor.isOf(Blocks.MAGMA_BLOCK);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE, AGE1, FACING);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(FRItems.COFFEE_BEANS);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(AGE) < 2 || (state.get(AGE1) < 2);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		int i = state.get(AGE);
		int j = state.get(AGE1);
		int rand = random.nextInt(2);
		if (random.nextInt(10) == 0) {
			if (rand == 0) {
				if (i < 2) {
					world.setBlockState(pos, state.with(AGE, i + 1));
				}
				else if (j < 2) {
					world.setBlockState(pos, state.with(AGE1, j + 1));
				}
			}
			else if (j < 2) {
				world.setBlockState(pos, state.with(AGE1, j + 1));
			}
			else if (i < 2) {
				world.setBlockState(pos, state.with(AGE, i + 1));
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		int i = state.get(AGE);
		int j = state.get(AGE1);
		boolean flag = (i == 2 || j == 2);
		if (!flag && player.getStackInHand(hand).getItem() == Items.BONE_MEAL) {
			return ActionResult.PASS;
		} else if (flag) {
			if (i == 2) {
				world.setBlockState(pos, state.with(AGE, 0), 2);
			}
			if (j == 2) {
				world.setBlockState(pos, state.with(AGE1, 0), 2);
			}
			dropStack(world, pos, new ItemStack(FRItems.COFFEE_BERRIES, 1));
			world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			return ActionResult.success(world.isClient);
		} else {
			return super.onUse(state, world, pos, player, hand, hit);
		}
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		int i = state.get(AGE);
		int j = state.get(AGE1);
		return FRConfiguration.BONE_MEAL_COFFEE.get() && !(i == 2 && j ==2);
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		int i = state.get(AGE);
		int j = state.get(AGE1);
		int rand = random.nextInt(2);
		if (rand == 0) {
			if (i < 2) {
				world.setBlockState(pos, state.with(AGE, i + 1));
			}
			else if (j < 2) {
				world.setBlockState(pos, state.with(AGE1, j + 1));
			}
		}
		if (rand == 1) {
			if (j < 2) {
				world.setBlockState(pos, state.with(AGE1, j + 1));
			}
			else if (i < 2) {
				world.setBlockState(pos, state.with(AGE, i + 1));
			}
		}
	}

	static {
		SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

		AGE = Properties.AGE_2;
		AGE1 = IntProperty.of("age1", 0, 2);
		FACING = HorizontalFacingBlock.FACING;
	}
}