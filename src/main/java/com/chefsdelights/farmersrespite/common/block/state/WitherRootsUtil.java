package com.chefsdelights.farmersrespite.common.block.state;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public class WitherRootsUtil {

    public static Iterable<BlockPos> randomInSquare(RandomSource rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY(), pos.getZ() - size, pos.getX() + size, pos.getY(), pos.getZ() + size);
    }

    public static Iterable<BlockPos> randomInSquareDown(RandomSource rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY() - 1, pos.getZ() - size, pos.getX() + size, pos.getY() - 1, pos.getZ() + size);
    }

    public static Iterable<BlockPos> randomInSquareDownDown(RandomSource rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY() - 2, pos.getZ() - size, pos.getX() + size, pos.getY() - 2, pos.getZ() + size);
    }
}
