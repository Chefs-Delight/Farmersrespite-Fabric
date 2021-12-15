package com.chefsdelights.farmersrespite.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import com.umpaz.farmersrespite.FarmersRespite;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		if (event.includeServer()) {
			generator.addProvider(new Recipes(generator));
		}
	}
}
