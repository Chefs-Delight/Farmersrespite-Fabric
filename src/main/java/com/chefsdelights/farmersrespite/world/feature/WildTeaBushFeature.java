package com.chefsdelights.farmersrespite.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import com.umpaz.farmersrespite.registry.FRBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WildTeaBushFeature extends Feature<DefaultFeatureConfig> {
	public WildTeaBushFeature(Codec<DefaultFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean place(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		BlockState blockstate = FRBlocks.WILD_TEA_BUSH.get().defaultBlockState();

		int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockPos blockpos = new BlockPos(pos.getX(), i, pos.getZ());

		if (worldIn.isEmptyBlock(blockpos) && worldIn.getBlockState(blockpos.below()).getBlock() == Blocks.GRASS_BLOCK) {
			worldIn.setBlock(blockpos, blockstate, 2);

			return true;
		}
		return false;
	}
}