package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.common.block.state.WitherRootsUtil;
import com.chefsdelights.farmersrespite.core.FRConfiguration;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CoffeeBushTopBlock extends BushBlock implements BonemealableBlock {
	   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	   private static final VoxelShape SHAPE_LOWER = Shapes.or(Block.box(0.0D, 6.0D, 0.0D, 16.0D, 18.0D, 16.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D));
	   private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(0.0D, -10.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(5.0D, -16.0D, 5.0D, 11.0D, -10.0D, 11.0D));


	   public CoffeeBushTopBlock(Properties properties) {
	      super(properties);
	      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
	   }

	   @Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    return SHAPE_UPPER;
		    	}
		    return SHAPE_LOWER;
		   }

	   @Override
	   public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		      return pState.is(FRBlocks.COFFEE_STEM) || pState.is(FRBlocks.COFFEE_STEM_MIDDLE) || pState.is(FRBlocks.COFFEE_BUSH_TOP);
		   }

	   @Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		      builder.add(HALF);
		   }

		   @Override
		public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
			   return new ItemStack(FRItems.COFFEE_BEANS);
		   }

		   @Override
		public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
			      DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
			      if ((facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) && !(facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf)) {
			      		return Blocks.AIR.defaultBlockState();
			      }
			       else {
			         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
			      }
			   }

		   @Override
		public boolean isRandomlyTicking(BlockState state) {
				 return true;
			 }

		   @Override
		public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
				for (BlockPos neighborPos : WitherRootsUtil.randomInSquareDown(random, pos, 2)) {
				    BlockState neighborState = level.getBlockState(neighborPos);
					BlockState witherRootsState = random.nextInt(2) == 0 ? FRBlocks.WITHER_ROOTS.defaultBlockState() : FRBlocks.WITHER_ROOTS_PLANT.defaultBlockState();
				      if ((state.getValue(HALF) == DoubleBlockHalf.LOWER) && (level.isEmptyBlock(pos.above().above())) && random.nextInt(2) == 0) {
							if (neighborState.getBlock() instanceof CropBlock) {
								level.setBlockAndUpdate(neighborPos, witherRootsState);
								performBonemeal(level, random, pos, state);
				    	  		}
							else if (level.dimensionType().ultraWarm()) {
								performBonemeal(level, random, pos, state);
							}
					      }
				   }
			   }

		   @Override
		public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player player) {
			      if (!pLevel.isClientSide && player.isCreative()) {
			         preventCreativeDropFromBottomPart(pLevel, pPos, pState, player);
			      }
			      BlockPos blockpos = pPos.below();
			      BlockState blockstate = pLevel.getBlockState(blockpos);
			      BlockPos blockpos1 = pPos.below().below();
			      BlockState blockstate1 = pLevel.getBlockState(blockpos1);
			      if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM) && pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
			    	  pLevel.destroyBlock(pPos.below(), true, player);
				  }
			      if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM) && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    	  pLevel.destroyBlock(pPos.below().below(), true, player);
				  }
			      if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE) && pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
			    	  pLevel.destroyBlock(pPos.below(), true, player);
			    	  pLevel.destroyBlock(pPos.below().below(), true, player);
				  }
			      if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE) && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    	  pLevel.destroyBlock(pPos.below().below(), true, player);
			    	  pLevel.destroyBlock(pPos.below().below().below(), true, player);
				  }
			      super.playerWillDestroy(pLevel, pPos, pState, player);
			   }

		   @Override
		   @Nullable
		   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
		      BlockPos blockpos = pContext.getClickedPos();
		      if (blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)) {
		         return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
		      } else {
		         return null;
		      }
		   }

		   @Override
		public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		      pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
		   }

		   protected static void preventCreativeDropFromBottomPart(Level world, BlockPos pos, BlockState state, Player player) {
			      DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
			      if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			         BlockPos blockpos = pos.below();
			         BlockState blockstate = world.getBlockState(blockpos);
			         if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
			            world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
			            world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			         }
			      }

			   }

		   @Override
		   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
			      BlockPos blockpos = pos.below();
			      BlockState blockstate = world.getBlockState(blockpos);
			      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? this.mayPlaceOn(world.getBlockState(blockpos), world, blockpos) : blockstate.is(this);
			   }

		@Override
		public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
			  if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			      BlockPos blockpos = pos.below();
			      BlockState blockstate = level.getBlockState(blockpos);
		      return FRConfiguration.BONE_MEAL_COFFEE.get() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM);
		      }
			  if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			      BlockPos blockpos = pos.below().below();
			      BlockState blockstate = level.getBlockState(blockpos);
			  return FRConfiguration.BONE_MEAL_COFFEE.get() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM);
			  }
			  return false;
		}

		@Override
		public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
			return FRConfiguration.BONE_MEAL_COFFEE.get();
		}

		public Direction getDirection(RandomSource rand) {
			int i = rand.nextInt(4);
			if (i == 0) {
				return Direction.NORTH;
			}
			if (i == 1) {
				return Direction.SOUTH;
			}
			if (i == 2) {
				return Direction.EAST;
			}
			if (i == 3) {
				return Direction.WEST;
			}
			return null;
		}

		@Override
		public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
				BlockState belowState = world.getBlockState(pos.below());
				BlockState belowBelowState = world.getBlockState(pos.below().below());
			  if (state.getValue(HALF) == DoubleBlockHalf.LOWER && (world.isEmptyBlock(pos.above().above())) && belowState.getBlock() instanceof CoffeeStemBlock) {
			  Direction direction = world.getBlockState(pos.below()).getValue(CoffeeStemBlock.FACING);
			  int berry = world.getBlockState(pos.below()).getValue(CoffeeStemBlock.AGE);
			  world.setBlockAndUpdate(pos.below(), FRBlocks.COFFEE_STEM_DOUBLE.defaultBlockState().setValue(CoffeeDoubleStemBlock.FACING, direction).setValue(CoffeeDoubleStemBlock.AGE, berry));
			  world.setBlockAndUpdate(pos, FRBlocks.COFFEE_STEM_MIDDLE.defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand)));
			  world.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.defaultBlockState());
			  world.setBlockAndUpdate(pos.above().above(), FRBlocks.COFFEE_BUSH_TOP.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));

			}
			  if (state.getValue(HALF) == DoubleBlockHalf.UPPER && (world.isEmptyBlock(pos.above())) && belowBelowState.getBlock() instanceof CoffeeStemBlock) {
			      Direction direction = world.getBlockState(pos.below().below()).getValue(CoffeeStemBlock.FACING);
				  int berry = world.getBlockState(pos.below().below()).getValue(CoffeeStemBlock.AGE);
				  world.setBlockAndUpdate(pos.below().below(), FRBlocks.COFFEE_STEM_DOUBLE.defaultBlockState().setValue(CoffeeDoubleStemBlock.FACING, direction).setValue(CoffeeDoubleStemBlock.AGE, berry));
				  world.setBlockAndUpdate(pos.below(), FRBlocks.COFFEE_STEM_MIDDLE.defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand)));
				  world.setBlockAndUpdate(pos, FRBlocks.COFFEE_BUSH_TOP.defaultBlockState());
		    	  world.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
		    }
		}
	}
