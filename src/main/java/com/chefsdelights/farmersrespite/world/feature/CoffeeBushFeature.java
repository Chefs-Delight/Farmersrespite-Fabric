package com.umpaz.farmersrespite.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.umpaz.farmersrespite.blocks.CoffeeBushBlock;
import com.umpaz.farmersrespite.blocks.CoffeeBushTopBlock;
import com.umpaz.farmersrespite.blocks.CoffeeStemBlock;
import com.umpaz.farmersrespite.registry.FRBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class CoffeeBushFeature extends Feature<NoFeatureConfig> {
	public CoffeeBushFeature(Codec<NoFeatureConfig> config) {
		super(config);
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

	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		int i = 0;
		
		BlockState coffeeBushBottom = FRBlocks.COFFEE_BUSH.get().defaultBlockState().setValue(CoffeeBushBlock.HALF, DoubleBlockHalf.LOWER);
		BlockState coffeeBushTop = FRBlocks.COFFEE_BUSH.get().defaultBlockState().setValue(CoffeeBushBlock.HALF, DoubleBlockHalf.UPPER);
		BlockState coffeeStem = FRBlocks.COFFEE_STEM.get().defaultBlockState();
		BlockState coffeeBushTopBottom = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue(CoffeeBushTopBlock.HALF, DoubleBlockHalf.LOWER);
		BlockState coffeeBushTopTop = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue(CoffeeBushTopBlock.HALF, DoubleBlockHalf.UPPER);

		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				if (Math.abs(x) < 1 || Math.abs(z) < 1) {
					for (int y = -1; y <= 1; ++y) {
						BlockPos blockpos = pos.offset(x, y, z);
						if (rand.nextInt(3) > 0 && worldIn.isEmptyBlock(blockpos) && blockpos.getY() < worldIn.getMaxBuildHeight() && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.BASALT) {
							if (rand.nextInt(5) < 3) {
								worldIn.setBlock(blockpos, coffeeBushBottom, 2);
								worldIn.setBlock(blockpos.above(), coffeeBushTop, 2);
							}
							else {
								worldIn.setBlock(blockpos, coffeeStem.setValue(CoffeeStemBlock.FACING, getDirection(rand)).setValue(CoffeeStemBlock.AGE, rand.nextInt(3)), 2);
								worldIn.setBlock(blockpos.above(), coffeeBushTopBottom, 2);
								worldIn.setBlock(blockpos.above().above(), coffeeBushTopTop, 2);
							}
							++i;
						}
					}
				}
			}
		}

		return i > 0;
	}
}