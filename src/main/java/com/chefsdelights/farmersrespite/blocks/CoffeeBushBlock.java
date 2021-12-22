package com.chefsdelights.farmersrespite.blocks;

import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.setup.FRConfiguration;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class CoffeeBushBlock extends PlantBlock implements Fertilizable {
	   public static final IntProperty AGE = BlockStateProperties.AGE_1;
	   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	   private static final VoxelShape SHAPE_LOWER = VoxelShapes.or(Block.box(0.0D, 6.0D, 0.0D, 16.0D, 18.0D, 16.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D));
	   private static final VoxelShape SHAPE_UPPER = VoxelShapes.or(Block.box(0.0D, -10.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(5.0D, -16.0D, 5.0D, 11.0D, -10.0D, 11.0D));


	   public CoffeeBushBlock(Settings settings) {
	      super(settings);
	      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER));
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    return SHAPE_UPPER;
		    	}
		    return SHAPE_LOWER;
		   }
	   
	   @Override
	   public PlantType getPlantType(IBlockReader world, BlockPos pos) {
	        return net.minecraftforge.common.PlantType.NETHER;
	    }
	   
	   @Override
	   protected boolean mayPlaceOn(BlockState pState, IBlockReader pLevel, BlockPos pPos) {
		      return pState.is(Blocks.BASALT) || pState.is(Blocks.POLISHED_BASALT) || pState.is(Blocks.MAGMA_BLOCK);
		   }

	   protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		      builder.add(AGE, HALF);
		   }
			 
		   public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
			   return new ItemStack(FRItems.COFFEE_BEANS.get());
		   }
		   
		   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
			      DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
			      if ((facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) && !(facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf)) {
			      		return Blocks.AIR.defaultBlockState();
			      }
			       else {
			         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
			      }
			   }
		   
		   public boolean isRandomlyTicking(BlockState state) {
				 return true;
			 }	
		    
		   public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
			      if ((state.getValue(HALF) == DoubleBlockHalf.LOWER) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(10) == 0)) {
			    	  performBonemeal(level, random, pos, state);	
			    	  net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
			      }
			   }
		   
		   public void playerWillDestroy(World pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
			      if (!pLevel.isClientSide && pPlayer.isCreative()) {
			         preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
			      }

			      super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
			   }
		   
		   @Nullable
		   public BlockState getStateForPlacement(BlockItemUseContext pContext) {
		      BlockPos blockpos = pContext.getClickedPos();
		      if (blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)) {
		         return this.defaultBlockState().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER);
		      } else {
		         return null;
		      }
		   }

		   public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		      pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
		   }
		   
		   protected static void preventCreativeDropFromBottomPart(World world, BlockPos pos, BlockState state, PlayerEntity player) {
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
		   public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
			      BlockPos blockpos = pos.below();
			      BlockState blockstate = world.getBlockState(blockpos);
			      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? this.mayPlaceOn(world.getBlockState(blockpos), world, blockpos) : blockstate.is(this);
			   }

		@Override
		public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean isClient) {
			return FRConfiguration.BONE_MEAL_COFFEE.get();
		}

		@Override
		public boolean isBonemealSuccess(World level, Random rand, BlockPos pos, BlockState state) {
			return true;
		}
		
		public Direction getDirection(Random rand) {
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
		public void performBonemeal(ServerWorld level, Random rand, BlockPos pos, BlockState state) {
			  if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
	    	  level.setBlockAndUpdate(pos, FRBlocks.COFFEE_STEM.get().defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand)));
	    	  level.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState());
	    	  level.setBlockAndUpdate(pos.above().above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
			}
			  if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
	    	  level.setBlockAndUpdate(pos.below(), FRBlocks.COFFEE_STEM.get().defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand)));
	    	  level.setBlockAndUpdate(pos, FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState());
	    	  level.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
			  }
		}
	}
