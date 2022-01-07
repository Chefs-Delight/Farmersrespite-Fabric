package com.chefsdelights.farmersrespite.blocks;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.setup.FRConfiguration;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CoffeeBushBlock extends PlantBlock implements Fertilizable {
	public static final IntProperty AGE;
	public static final EnumProperty<DoubleBlockHalf> HALF;

	private static final VoxelShape SHAPE_LOWER;
	private static final VoxelShape SHAPE_UPPER;

	public CoffeeBushBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(HALF) == DoubleBlockHalf.UPPER) {
			return SHAPE_UPPER;
		}
		return SHAPE_LOWER;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOf(Blocks.BASALT) || floor.isOf(Blocks.POLISHED_BASALT) || floor.isOf(Blocks.MAGMA_BLOCK);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE, HALF);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return new ItemStack(FRItems.COFFEE_BEANS);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		DoubleBlockHalf doubleBlockHalf = state.get(HALF);
		if ((direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) && !(neighborState.isOf(this) && neighborState.get(HALF) != doubleBlockHalf)) {
			return Blocks.AIR.getDefaultState();
		} else {
			return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
		}
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if ((state.get(HALF) == DoubleBlockHalf.LOWER) && random.nextInt(10) == 0) {
			this.grow(world, random, pos, state);
		}
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!world.isClient && player.isCreative()) {
			this.onBreakInCreative(world, pos, state, player);
		}
		super.onBreak(world, pos, state, player);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos blockPos = ctx.getBlockPos();
		if (blockPos.getY() < 255 && ctx.getWorld().getBlockState(blockPos.up()).canReplace(ctx)) {
			return this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER);
		} else {
			return null;
		}
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
	}

	protected void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		DoubleBlockHalf doubleBlockHalf = state.get(HALF);
		if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
			BlockPos blockPos = pos.down();
			BlockState blockState = world.getBlockState(blockPos);
			if (blockState.getBlock() == state.getBlock() && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
				world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
			}
		}
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos blockPos = pos.down();
		BlockState blockState = world.getBlockState(blockPos);
		return state.get(HALF) == DoubleBlockHalf.LOWER ? this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos) : blockState.isOf(this);
	}

	@Override
	public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		return FRConfiguration.BONE_MEAL_COFFEE.get();
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	public Direction getDirection(Random random) {
		int i = random.nextInt(4);
		if (i == 0) {
			return Direction.NORTH;
		}
		if (i == 1) {
			return Direction.SOUTH;
		}
		if (i == 2) {
			return Direction.EAST;
		}
		return Direction.WEST;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		if (state.get(HALF) == DoubleBlockHalf.LOWER) {
			world.setBlockState(pos, FRBlocks.COFFEE_STEM.getDefaultState().with(CoffeeStemBlock.FACING, this.getDirection(random)));
			world.setBlockState(pos.up(), FRBlocks.COFFEE_BUSH_TOP.getDefaultState());
			world.setBlockState(pos.up(2), FRBlocks.COFFEE_BUSH_TOP.getDefaultState().with(HALF, DoubleBlockHalf.UPPER));
		}
		if (state.get(HALF) == DoubleBlockHalf.UPPER) {
			world.setBlockState(pos.down(), FRBlocks.COFFEE_STEM.getDefaultState().with(CoffeeStemBlock.FACING, this.getDirection(random)));
			world.setBlockState(pos, FRBlocks.COFFEE_BUSH_TOP.getDefaultState());
			world.setBlockState(pos.up(), FRBlocks.COFFEE_BUSH_TOP.getDefaultState().with(HALF, DoubleBlockHalf.UPPER));
		}
	}

	static {
		AGE = Properties.AGE_1;
		HALF = Properties.DOUBLE_BLOCK_HALF;

		SHAPE_LOWER = VoxelShapes.union(Block.createCuboidShape(0.0D, 6.0D, 0.0D, 16.0D, 18.0D, 16.0D), Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D));
		SHAPE_UPPER = VoxelShapes.union(Block.createCuboidShape(0.0D, -10.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(5.0D, -16.0D, 5.0D, 11.0D, -10.0D, 11.0D));
	}
}