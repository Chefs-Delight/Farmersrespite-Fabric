package com.umpaz.farmersrespite.loot;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.tile.KettleTileEntity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import vectorwing.farmersdelight.loot.functions.CopyMealFunction;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KettleCopyDrinkFunction extends LootFunction
{
	public static final ResourceLocation ID = new ResourceLocation(FarmersRespite.MODID, "copy_drink");

	private KettleCopyDrinkFunction(ILootCondition[] conditions) {
		super(conditions);
	}

	public static LootFunction.Builder<?> builder() {
		return simpleBuilder(KettleCopyDrinkFunction::new);
	}

	@Override
	protected ItemStack run(ItemStack stack, LootContext context) {
		TileEntity tile = context.getParamOrNull(LootParameters.BLOCK_ENTITY);
		if (tile instanceof KettleTileEntity) {
			CompoundNBT tag = ((KettleTileEntity) tile).writeMeal(new CompoundNBT());
			if (!tag.isEmpty()) {
				stack.addTagElement("BlockEntityTag", tag);
			}
		}
		return stack;
	}

	@Override
	@Nullable
	public LootFunctionType getType() {
		return null;
	}

	public static class Serializer extends LootFunction.Serializer<KettleCopyDrinkFunction>
	{
		@Override
		public KettleCopyDrinkFunction deserialize(JsonObject json, JsonDeserializationContext context, ILootCondition[] conditions) {
			return new KettleCopyDrinkFunction(conditions);
		}
	}
}