package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.tile.KettleBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class FRBlockEntityTypes {
	public static final BlockEntityType<KettleBlockEntity> KETTLE_TILE = register("kettle", () -> BlockEntityType.Builder.create(KettleBlockEntity::new, FRBlocks.KETTLE).build(null));
}
