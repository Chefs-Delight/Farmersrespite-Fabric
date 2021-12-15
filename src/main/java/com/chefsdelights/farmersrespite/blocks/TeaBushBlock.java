package com.chefsdelights.farmersrespite.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.setup.FRConfiguration;

import net.minecraft.block.*;
import net.minecraft.block.BushBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import vectorwing.farmersdelight.setup.Configuration;

	public class TeaBushBlock extends PlantBlock implements Fertilizable {
	   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	   private static final VoxelShape SHAPE_LOWER = VoxelShapes.or(Block.box(0.0D, 11.0D, 0.0D, 16.0D, 24.0D, 16.0D), Block.box(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D));
	   private static final VoxelShape SHAPE_UPPER = VoxelShapes.or(Block.box(0.0D, -5.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(6.0D, -16.0D, 6.0D, 10.0D, -5.0D, 10.0D));

	   public TeaBushBlock(Settings settings) {
	      super(settings);
	      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER));
	   }
	   
	   public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    return SHAPE_UPPER;
		    	}
		    return SHAPE_LOWER;
		   }
	   
	   protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
	      builder.add(AGE, HALF);
	   }
		 
	   public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
		   return new ItemStack(FRItems.TEA_SEEDS.get());
	   }
	   
	   public boolean isRandomlyTicking(BlockState state) {
		      return state.getValue(AGE) < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER;
		   }
	   
	   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
		      DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		      if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
		         return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? state.setValue(AGE, facingState.getValue(AGE)) : Blocks.AIR.defaultBlockState();
		      } else {
		         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
		      }
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
	         World world = pContext.getLevel();
	         boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
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
	   
	   public boolean canSurvive(BlockState state, IWorldReader world, BlockPos pos) {
		      BlockPos blockpos = pos.below();
		      BlockState blockstate = world.getBlockState(blockpos);
		      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(world, blockpos, Direction.UP) : blockstate.is(this);
		   }

	   public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		      int i = state.getValue(AGE);
		      ItemStack heldStack = player.getItemInHand(handIn);
		      Item item = heldStack.getItem();

		      if (item == Items.SHEARS) {
		         int j = world.random.nextInt(2);
		         int k = 2 + world.random.nextInt(2);
		         int l = world.random.nextInt(2);

		         if (i == 0) {
		         popResource(world, pos, new ItemStack(FRItems.GREEN_TEA_LEAVES.get(), 2 + j));
		         }
		         if (i == 1) {
			     popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 2 + j));
		         }
		         if (i == 2) {
		         popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 1 + j));
				 popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 1 + l));
		         }
		         if (i == 3) {
				 popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 2 + j));
		         }
		         if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			     world.setBlockAndUpdate(pos, FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
		         }
		         if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
				 world.setBlockAndUpdate(pos.below(), FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
			     }
		         world.playSound((PlayerEntity)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			     popResource(world, pos, new ItemStack(Items.STICK, k));
			     heldStack.hurtAndBreak(1, player, (p_226874_1_) -> {player.broadcastBreakEvent(handIn);});	
		         return ActionResultType.sidedSuccess(world.isClientSide);
		      } else {
			     return super.use(state, world, pos, player, handIn, result);
		      }
		   }

	@Override
	public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean isClient) {
	    int i = state.getValue(AGE);
	    if (i != 3) {
		return FRConfiguration.BONE_MEAL_TEA.get();
	}
	    return false;
	} 

	@Override
	public boolean isBonemealSuccess(World level, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerWorld level, Random rand, BlockPos pos, BlockState state) {
	    int i = state.getValue(AGE);
  	  	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
		}
	}