package com.chefsdelights.farmersrespite.registry;

import com.chefsdelights.farmersrespite.FarmersRespite;
import com.nhoryzon.mc.farmersdelight.loot.function.CopyMealFunctionSerializer;
import com.nhoryzon.mc.farmersdelight.loot.function.SmokerCookFunctionSerializer;
import java.util.function.Supplier;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.loot.function.ConditionalLootFunction.Serializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum FRLootFunctions {
    COPY_DRINK("copy_drink", SmokerCookFunctionSerializer::new);

    private final String pathName;
    private final Supplier<Serializer<? extends LootFunction>> lootFunctionSerializerSupplier;
    private Serializer<? extends LootFunction> serializer;
    private LootFunctionType type;

    FRLootFunctions(String pathName, Supplier<Serializer<? extends LootFunction>> lootFunctionSerializerSupplier) {
        this.pathName = pathName;
        this.lootFunctionSerializerSupplier = lootFunctionSerializerSupplier;
    }

    public static void registerAll() {
        FRLootFunctions[] var0 = values();
        int var1 = var0.length;

        for(int var2 = 0; var2 < var1; ++var2) {
            FRLootFunctions value = var0[var2];
            Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(FarmersRespite.MOD_ID, value.pathName), value.type());
        }
    }

    public LootFunctionType type() {
        if (this.type == null) {
            this.type = new LootFunctionType(this.serializer());
        }

        return this.type;
    }

    public Serializer<? extends LootFunction> serializer() {
        if (this.serializer == null) {
            this.serializer = this.lootFunctionSerializerSupplier.get();
        }

        return this.serializer;
    }
}