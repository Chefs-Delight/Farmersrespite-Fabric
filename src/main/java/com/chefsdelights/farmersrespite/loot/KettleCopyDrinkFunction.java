package com.chefsdelights.farmersrespite.loot;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;

public class KettleCopyDrinkFunction extends LootFunction {
	public static final Identifier ID = new Identifier(FarmersRespite.MOD_ID, "copy_drink");

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