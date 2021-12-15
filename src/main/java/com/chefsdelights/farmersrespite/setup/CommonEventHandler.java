package com.chefsdelights.farmersrespite.setup;

import java.util.List;
import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.loot.KettleCopyDrinkFunction;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vectorwing.farmersdelight.setup.Configuration;

@Mod.EventBusSubscriber(modid = FarmersRespite.MODID)
@ParametersAreNonnullByDefault
public class CommonEventHandler {
	public static void init(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			registerCompostables();
		});
		
		LootFunctionManager.register(KettleCopyDrinkFunction.ID.toString(), new KettleCopyDrinkFunction.Serializer());

	}

	public static void registerCompostables() {
		// 30% chance
		//ComposterBlock.COMPOSTABLES.put(ModItems.TREE_BARK.get(), 0.3F);
	}

	@SubscribeEvent
	public static void onVillagerTrades(VillagerTradesEvent event) {
		if (!Configuration.FARMERS_BUY_FD_CROPS.get()) return;

		Int2ObjectMap<List<VillagerTrades.ITrade>> trades = event.getTrades();
		VillagerProfession profession = event.getType();
		if (profession.getRegistryName() == null) return;
		if (profession.getRegistryName().getPath().equals("farmer")) {
			//trades.get(1).add(new EmeraldForItemsTrade(ModItems.ONION.get(), 26, 16, 2));
		}
	}

	static class EmeraldForItemsTrade implements VillagerTrades.ITrade
	{
		private final Item tradeItem;
		private final int count;
		private final int maxUses;
		private final int xpValue;
		private final float priceMultiplier;

		public EmeraldForItemsTrade(IItemProvider tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
			this.tradeItem = tradeItemIn.asItem();
			this.count = countIn;
			this.maxUses = maxUsesIn;
			this.xpValue = xpValueIn;
			this.priceMultiplier = 0.05F;
		}

		public MerchantOffer getOffer(Entity trader, Random rand) {
			ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
			return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, this.priceMultiplier);
		}
	}
}
