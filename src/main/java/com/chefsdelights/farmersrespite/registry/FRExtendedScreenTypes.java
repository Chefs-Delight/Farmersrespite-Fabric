package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.chefsdelights.farmersrespite.entity.block.screen.KettleScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public enum FRExtendedScreenTypes {
	KETTLE("kettle", KettleScreenHandler.class, KettleScreenHandler::new);

	private final String pathName;
	private final Class<? extends ScreenHandler> screenHandlerClass;
	private final ScreenHandlerRegistry.ExtendedClientHandlerFactory<? extends ScreenHandler> screenHandlerFactory;
	private ScreenHandlerType<? extends ScreenHandler> screenHandlerType;

	FRExtendedScreenTypes(String pathName, Class<? extends ScreenHandler> screenHandlerClass, ScreenHandlerRegistry.ExtendedClientHandlerFactory<? extends ScreenHandler> screenHandlerFactory) {
		this.pathName = pathName;
		this.screenHandlerClass = screenHandlerClass;
		this.screenHandlerFactory = screenHandlerFactory;
	}

	public static void registerAll() {
		FRExtendedScreenTypes[] var0 = values();
		int var1 = var0.length;

		for(int var2 = 0; var2 < var1; ++var2) {
			FRExtendedScreenTypes value = var0[var2];
			Registry.register(Registry.SCREEN_HANDLER, FarmersRespite.id(value.pathName), value.get());
		}

	}

	public <T extends ScreenHandler> ScreenHandlerType<T> get() {
		return (ScreenHandlerType<T>) this.get(this.screenHandlerClass);
	}

	private <T extends ScreenHandler> ScreenHandlerType<T> get(Class<T> clazz) {
		if (this.screenHandlerType == null) {
			this.screenHandlerType = new ExtendedScreenHandlerType(this.screenHandlerFactory);
		}

		return (ScreenHandlerType<T>) this.screenHandlerType;
	}
}