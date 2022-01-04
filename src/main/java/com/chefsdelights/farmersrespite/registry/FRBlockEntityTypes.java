package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.entity.block.KettleBlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class FRBlockEntityTypes {
	public static final BlockEntityType<KettleBlockEntity> KETTLE_TILE = register("kettle", BlockEntityType.Builder.create(new KettleBlockEntity(), FRBlocks.KETTLE).build(null));
}
