package com.chefsdelights.farmersrespite.common.loot.function;

import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.Nullable;


@MethodsReturnNonnullByDefault
public class FRCopyMealFunction extends LootItemConditionalFunction {
    public static final ResourceLocation ID = new ResourceLocation(FarmersRespite.MOD_ID, "copy_meal");

    private FRCopyMealFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    public static Builder<?> builder() {
        return simpleBuilder(FRCopyMealFunction::new);
    }

    @Override
    protected ItemStack run(ItemStack stack, LootContext context) {
        BlockEntity tile = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (tile instanceof KettleBlockEntity) {
            CompoundTag tag = ((KettleBlockEntity) tile).writeMeal(new CompoundTag());
            if (!tag.isEmpty()) {
                stack.addTagElement("BlockEntityTag", tag);
            }
        }
        return stack;
    }

    @Override
    @Nullable
    public LootItemFunctionType getType() {
        return null;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<FRCopyMealFunction> {
        @Override
        public FRCopyMealFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
            return new FRCopyMealFunction(conditions);
        }
    }
}