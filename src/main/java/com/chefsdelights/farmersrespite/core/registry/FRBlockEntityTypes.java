package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FRBlockEntityTypes {

	public static final BlockEntityType<KettleBlockEntity> KETTLE = register("kettle", BlockEntityType.Builder.of(KettleBlockEntity::new, FRBlocks.KETTLE).build(null));

	public static <T extends BlockEntityType<?>> T register(String path, T block) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, FarmersRespite.id(path), block);
	}

}