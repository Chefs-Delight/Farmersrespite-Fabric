package com.umpaz.farmersrespite.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class WildTeaBushBlock extends BushBlock {
	   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

	public WildTeaBushBlock(Properties properties) {
		super(properties);
	}
	
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
		 return SHAPE;
	 }

	@Override
	 public boolean isRandomlyTicking(BlockState state) {
		 return false;
	 }
}
