package com.chefsdelights.farmersrespite.common.levelgen.feature;

import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class WildTeaBushFeature extends Feature<SimpleBlockConfiguration> {
    public WildTeaBushFeature(Codec<SimpleBlockConfiguration> config) {
        super(config);
    }

    @Override
    public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> context) {
        SimpleBlockConfiguration config = context.config();
        WorldGenLevel level = context.level();
        BlockState blockstate = FRBlocks.WILD_TEA_BUSH.defaultBlockState();
        BlockPos pos = context.origin();
        BlockState state = config.toPlace().getState(context.random(), pos);
        if (state.canSurvive(level, pos)) {
            level.setBlock(pos, blockstate, 19);
            return true;
        }
        return false;
    }
}