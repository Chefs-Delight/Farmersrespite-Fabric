package com.chefsdelights.farmersrespite.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.function.ConditionalLootFunction.Serializer;

public class KettleCopyDrinkFunctionSerializer extends Serializer<KettleCopyDrinkFunction> {
    public KettleCopyDrinkFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
        return new KettleCopyDrinkFunction(conditions);
    }
}