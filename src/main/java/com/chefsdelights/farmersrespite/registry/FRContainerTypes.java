package com.chefsdelights.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.tile.container.KettleContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FRContainerTypes {
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, FarmersRespite.MODID);
	
	public static final RegistryObject<ContainerType<KettleContainer>> KETTLE = CONTAINER_TYPES
			.register("kettle", () -> IForgeContainerType.create(KettleContainer::new));
}
