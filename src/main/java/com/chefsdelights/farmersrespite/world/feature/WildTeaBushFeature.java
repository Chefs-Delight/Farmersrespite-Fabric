package com.chefsdelights.farmersrespite.world.feature;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WildTeaBushFeature extends Feature<DefaultFeatureConfig> {
	public WildTeaBushFeature(Codec<DefaultFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		StructureWorldAccess worldIn = context.getWorld();
		BlockPos pos = context.getOrigin();
		BlockState blockstate = FRBlocks.WILD_TEA_BUSH.getDefaultState();

		int i = worldIn.getHeight();
		BlockPos blockpos = new BlockPos(pos.getX(), i, pos.getZ());

		if (worldIn.isAir(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
			worldIn.setBlockState(blockpos, blockstate, 2);

			return true;
		}
		return false;
	}
}