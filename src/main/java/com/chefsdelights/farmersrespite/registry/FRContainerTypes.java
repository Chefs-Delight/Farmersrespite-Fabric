package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.entity.block.container.KettleContainer;

public class FRContainerTypes {
	public static final ContainerType<KettleContainer> KETTLE = register("kettle", IForgeContainerType.create(KettleContainer::new));
}