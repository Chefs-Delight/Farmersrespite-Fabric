package com.umpaz.farmersrespite.blocks;

import java.util.Random;

import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRItems;
import com.umpaz.farmersrespite.setup.FRConfiguration;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

	public class SmallTeaBushBlock extends BushBlock implements IGrowable {
		   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

		public SmallTeaBushBlock(Properties properties) {
			super(properties);
		}
		
		 public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
			 return SHAPE;
		 }
		 
		 public ItemStack getCloneItemStack(IBlockReader level, BlockPos pos, BlockState state) {
			 return new ItemStack(FRItems.TEA_SEEDS.get());
		 }
		 
		 public boolean isRandomlyTicking(BlockState state) {
			 return true;
		 }		
		 
		 public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
			 if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(10) == 0)) {
				 performBonemeal(level, random, pos, state);
				 net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
			 }
		 }

		@Override
		public boolean isValidBonemealTarget(IBlockReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
			return FRConfiguration.BONE_MEAL_TEA.get();
		}

		@Override
		public boolean isBonemealSuccess(World pLevel, Random pRand, BlockPos pPos, BlockState pState) {
			return true;
		}

		@Override
		public void performBonemeal(ServerWorld pLevel, Random pRand, BlockPos pPos, BlockState pState) {	
			pLevel.setBlockAndUpdate(pPos, FRBlocks.TEA_BUSH.get().defaultBlockState());
			pLevel.setBlockAndUpdate(pPos.above(), FRBlocks.TEA_BUSH.get().defaultBlockState().setValue(TeaBushBlock.HALF, DoubleBlockHalf.UPPER));
		}
}
