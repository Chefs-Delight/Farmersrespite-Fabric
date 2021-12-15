package com.chefsdelights.farmersrespite.blocks;

import java.util.Random;

import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.setup.FRConfiguration;

import net.minecraft.block.*;
import net.minecraft.block.BushBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

public class CoffeeDoubleStemBlock extends PlantBlock implements Fertilizable {
	   public static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

	   public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	   public static final IntegerProperty AGE1 = IntegerProperty.create("age1", 0, 2);
	   public static final DirectionProperty FACING = HorizontalBlock.FACING;


	   public CoffeeDoubleStemBlock(Settings settings) {
	      super(settings);
	      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(AGE1, Integer.valueOf(0)).setValue(FACING, Direction.NORTH));
	   }

	   public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
			return SHAPE;
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
		      builder.add(AGE, AGE1, FACING);
		   }
			 
		   public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
			   return new ItemStack(FRItems.COFFEE_BEANS.get());
		   }
		   
		   public boolean isRandomlyTicking(BlockState state) {
			      return (state.getValue(AGE) < 2 || (state.getValue(AGE1) < 2));
			   }

		   public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
			      int i = state.getValue(AGE);
			      int j = state.getValue(AGE1);
			      int rand = random.nextInt(2);
			      if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(10) == 0)) {
				      	if (rand == 0) {
				      		if (i < 2) {
				    	 	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
				      		}
				      		else if (j < 2) {
				    	 	level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
				      		}
					    }
				      	else if (rand == 1 && j < 2) {
				    	 	level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
					    }
				      	else if (rand == 1 && i < 2) {
				    	 	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
				      	}
			    	  net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
			      }
			   }
		   
		@SuppressWarnings("deprecation")
		public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
			      int i = state.getValue(AGE);
			      int j = state.getValue(AGE1);
			      boolean flag = (i == 2 || j == 2);
			      if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
			         return ActionResultType.PASS;
			      } else if (flag) {
			    	  if (i == 2) {
			    		  world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
			    	  }
			    	  if (j == 2) {
			    		  world.setBlock(pos, state.setValue(AGE1, Integer.valueOf(0)), 2);
			    	  }
			         popResource(world, pos, new ItemStack(FRItems.COFFEE_BERRIES.get(), 1));
			         world.playSound((PlayerEntity)null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
			         return ActionResultType.sidedSuccess(world.isClientSide);
			      } else {
			         return super.use(state, world, pos, player, handIn, result);
			      }
			   }

		@Override
		public boolean isValidBonemealTarget(IBlockReader level, BlockPos pos, BlockState state, boolean isClient) {
		      int i = state.getValue(AGE);
		      int j = state.getValue(AGE1);
			return FRConfiguration.BONE_MEAL_COFFEE.get() && !(i == 2 && j ==2);
		}

		@Override
		public boolean isBonemealSuccess(World level, Random rand, BlockPos pos, BlockState state) {
			return true;
		}

		@Override
		public void performBonemeal(ServerWorld level, Random rand, BlockPos pos, BlockState state) {
		      	int i = state.getValue(AGE);
		      	int j = state.getValue(AGE1);
		      	int random = rand.nextInt(2);
		      	if (random == 0) {
		      		if (i < 2) {
	    	 	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
		      		}
		      	else if (j < 2){
		    	 	level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
		      	}
		     }
		      	if (random == 1) {
		      		if (j < 2) {
	    	 	level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
		      		}
		      	else if (i < 2) {
		    	 	level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
		      	}
		     }
		}
	}
