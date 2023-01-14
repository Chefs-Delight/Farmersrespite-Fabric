package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.core.FRConfiguration;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

	public class TeaBushBlock extends BushBlock implements BonemealableBlock {
	   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	   private static final VoxelShape SHAPE_LOWER = Shapes.or(Block.box(0.0D, 11.0D, 0.0D, 16.0D, 24.0D, 16.0D), Block.box(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D));
	   private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(0.0D, -5.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(6.0D, -16.0D, 6.0D, 10.0D, -5.0D, 10.0D));

	   public TeaBushBlock(Properties properties) {
	      super(properties);
	      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER));
	   }

	   @Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
			    return SHAPE_UPPER;
		    	}
		    return SHAPE_LOWER;
		   }

	   @Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	      builder.add(AGE, HALF);
	   }

	   @Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		   return new ItemStack(FRItems.TEA_SEEDS);
	   }

	   @Override
	public boolean isRandomlyTicking(BlockState state) {
		      return state.getValue(AGE) < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER;
		   }

	   @Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
		      DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		      if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
		         return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? state.setValue(AGE, facingState.getValue(AGE)) : Blocks.AIR.defaultBlockState();
		      } else {
		         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
		      }
		   }

	   @Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
		      if ((state.getValue(HALF) == DoubleBlockHalf.LOWER) && random.nextInt(15) == 0) {
		    	  performBonemeal(level, random, pos, state);
		      }
		   }

	   @Override
	public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
		      if (!pLevel.isClientSide && pPlayer.isCreative()) {
		         preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
		      }

		      super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
		   }

	   @Override
	   @Nullable
	   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
	      BlockPos blockpos = pContext.getClickedPos();
	      if (blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)) {
	         return this.defaultBlockState().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER);
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
		      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(world, blockpos, Direction.UP) : blockstate.is(this);
		   }

		@Override
		public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
		      int i = state.getValue(AGE);
		      ItemStack heldStack = player.getItemInHand(handIn);
		      Item item = heldStack.getItem();

		      if (item == Items.SHEARS) {
		         int j = world.random.nextInt(2);
		         int k = 2 + world.random.nextInt(2);
		         int l = world.random.nextInt(2);

		         if (i == 0) {
		         popResource(world, pos, new ItemStack(FRItems.GREEN_TEA_LEAVES, 2 + j));
		         }
		         if (i == 1) {
			     popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES, 2 + j));
		         }
		         if (i == 2) {
		         popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES, 1 + j));
				 popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES, 1 + l));
		         }
		         if (i == 3) {
				 popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES, 2 + j));
		         }
		         if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			     world.setBlockAndUpdate(pos, FRBlocks.SMALL_TEA_BUSH.defaultBlockState());
		         }
		         if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
				 world.setBlockAndUpdate(pos.below(), FRBlocks.SMALL_TEA_BUSH.defaultBlockState());
			     }
		         world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			     popResource(world, pos, new ItemStack(Items.STICK, k));
			     heldStack.hurtAndBreak(1, player, (p_226874_1_) -> {player.broadcastBreakEvent(handIn);});
		         return InteractionResult.sidedSuccess(world.isClientSide);
		      } else {
			     return super.use(state, world, pos, player, handIn, result);
		      }
		   }

	@Override
	public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
	    int i = state.getValue(AGE);
	    if (i != 3) {
		return FRConfiguration.BONE_MEAL_TEA.get();
	}
	    return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
	    int i = state.getValue(AGE);
  	  	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
		}
	}