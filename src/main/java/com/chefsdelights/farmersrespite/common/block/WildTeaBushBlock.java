package com.chefsdelights.farmersrespite.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildTeaBushBlock extends BushBlock {
	   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

	public WildTeaBushBlock(Properties properties) {
		super(properties);
	}

	   @Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		 return SHAPE;
	 }

	@Override
	 public boolean isRandomlyTicking(BlockState state) {
		 return false;
	 }
}
