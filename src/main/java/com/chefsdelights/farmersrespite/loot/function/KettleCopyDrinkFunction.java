package com.chefsdelights.farmersrespite.loot.function;

import com.chefsdelights.farmersrespite.entity.block.KettleBlockEntity;
import com.chefsdelights.farmersrespite.registry.FRLootFunctions;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.NbtCompound;

public class KettleCopyDrinkFunction extends ConditionalLootFunction {
	public static Builder<?> builder() {
		return builder(KettleCopyDrinkFunction::new);
	}

	public KettleCopyDrinkFunction(LootCondition[] conditions) {
		super(conditions);
	}

	@Override
	protected ItemStack process(ItemStack stack, LootContext context) {
		BlockEntity blockEntity = context.get(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity instanceof KettleBlockEntity) {
			NbtCompound tag = ((KettleBlockEntity) blockEntity).writeMeal(new NbtCompound());
			if (!tag.isEmpty()) {
				stack.setSubNbt("BlockEntityTag", tag);
			}
		}
		return stack;
	}

	@Override
	public LootFunctionType getType() {
		return FRLootFunctions.COPY_DRINK.type();
	}
}