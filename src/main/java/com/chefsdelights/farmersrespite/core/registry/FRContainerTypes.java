package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.block.entity.container.KettleContainer;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class FRContainerTypes {

	public static final MenuType<KettleContainer> KETTLE = registerContainer("kettle", KettleContainer::new);

	public static <T extends AbstractContainerMenu> MenuType<T> registerContainer(String pathName, ExtendedScreenHandlerType.ExtendedFactory<T> screenHandlerFactory) {
		return Registry.register(Registry.MENU, new ResourceLocation(FarmersRespite.MOD_ID, pathName),new ExtendedScreenHandlerType<>(screenHandlerFactory));
	}
}
