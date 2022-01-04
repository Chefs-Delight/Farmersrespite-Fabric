package com.chefsdelights.farmersrespite.world.feature;

import com.chefsdelights.farmersrespite.blocks.CoffeeBushBlock;
import com.chefsdelights.farmersrespite.blocks.CoffeeBushTopBlock;
import com.chefsdelights.farmersrespite.blocks.CoffeeStemBlock;
import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class CoffeeBushFeature extends Feature<DefaultFeatureConfig> {
	public CoffeeBushFeature(Codec<DefaultFeatureConfig> config) {
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
		return Direction.WEST;
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess worldIn = context.getWorld();
		Random rand = context.getRandom();
		BlockPos pos = context.getOrigin();
		ChunkGenerator generator = context.getGenerator();
		int i = 0;

		BlockState coffeeBushBottom = FRBlocks.COFFEE_BUSH.getDefaultState().with(CoffeeBushBlock.HALF, DoubleBlockHalf.LOWER);
		BlockState coffeeBushTop = FRBlocks.COFFEE_BUSH.getDefaultState().with(CoffeeBushBlock.HALF, DoubleBlockHalf.UPPER);
		BlockState coffeeStem = FRBlocks.COFFEE_STEM.getDefaultState();
		BlockState coffeeBushTopBottom = FRBlocks.COFFEE_BUSH_TOP.getDefaultState().with(CoffeeBushTopBlock.HALF, DoubleBlockHalf.LOWER);
		BlockState coffeeBushTopTop = FRBlocks.COFFEE_BUSH_TOP.getDefaultState().with(CoffeeBushTopBlock.HALF, DoubleBlockHalf.UPPER);

		for (int x = -1; x <= 1; ++x) {
			for (int z = -1; z <= 1; ++z) {
				if (Math.abs(x) < 1 || Math.abs(z) < 1) {
					for (int y = -1; y <= 1; ++y) {
						BlockPos blockpos = pos.add(x, y, z);
						if (rand.nextInt(3) > 0 && worldIn.isAir(blockpos) && blockpos.getY() < generator.getWorldHeight() && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.BASALT) {
							if (rand.nextInt(5) < 3) {
								worldIn.setBlockState(blockpos, coffeeBushBottom, 2);
								worldIn.setBlockState(blockpos.up(), coffeeBushTop, 2);
							}
							else {
								worldIn.setBlockState(blockpos, coffeeStem.with(CoffeeStemBlock.FACING, getDirection(rand)).with(CoffeeStemBlock.AGE, rand.nextInt(3)), 2);
								worldIn.setBlockState(blockpos.up(), coffeeBushTopBottom, 2);
								worldIn.setBlockState(blockpos.up(2), coffeeBushTopTop, 2);
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