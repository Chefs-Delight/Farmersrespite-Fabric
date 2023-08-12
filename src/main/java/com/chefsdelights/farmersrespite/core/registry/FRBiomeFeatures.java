package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.levelgen.feature.CoffeeBushFeature;
import com.chefsdelights.farmersrespite.common.levelgen.feature.WildTeaBushFeature;
import com.chefsdelights.farmersrespite.core.FRConfiguration;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.nhoryzon.mc.farmersdelight.registry.BiomeFeaturesRegistry;
import com.nhoryzon.mc.farmersdelight.world.feature.WildCropFeature;
import com.nhoryzon.mc.farmersdelight.world.feature.WildRiceCropFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import org.intellij.lang.annotations.Identifier;

import java.util.List;
import java.util.function.Supplier;


public enum FRBiomeFeatures {
    WILD_COFFEE("wild_coffee_bush", () -> new CoffeeBushFeature(NoneFeatureConfiguration.CODEC)),
    WILD_TEA("wild_tea_bush", () -> new WildTeaBushFeature(SimpleBlockConfiguration.CODEC));

    private final String pathName;
    private final Supplier<Feature<? extends FeatureConfiguration>> featureSupplier;
    private Feature<? extends FeatureConfiguration> feature;

    FRBiomeFeatures(String pathName, Supplier featureSupplier) {
        this.pathName = pathName;
        this.featureSupplier = featureSupplier;
    }

    public static void registerAll() {
        FRBiomeFeatures[] var0 = values();
        for (FRBiomeFeatures value : var0) {
            Registry.register(BuiltInRegistries.FEATURE, new ResourceLocation("farmersrespite", value.pathName), (Feature) value.featureSupplier.get());
        }

    }

    public Feature<? extends FeatureConfiguration> get() {
        if (this.feature == null) {
            this.feature = this.featureSupplier.get();
        }

        return this.feature;
    }

    public enum FRConfiguredFeaturesRegistry {
        PATCH_WILD_TEA_BUSH("patch_wild_tea_bush"),
        PATCH_COFFEE_BUSH("patch_wild_coffee_bush");

        private final ResourceLocation featureIdentifier;
        private ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureRegistryKey;
        private ResourceKey<PlacedFeature> featureRegistryKey;

        FRConfiguredFeaturesRegistry(String featurePathName) {
            this.featureIdentifier = FarmersRespite.id(featurePathName);
        }

        public static void registerAll() {
            for (FRConfiguredFeaturesRegistry value : values()) {
                value.configuredFeatureRegistryKey = ResourceKey.create(Registries.CONFIGURED_FEATURE, value.featureIdentifier);
                value.featureRegistryKey = ResourceKey.create(Registries.PLACED_FEATURE, value.featureIdentifier);
            }
        }

        public ResourceKey<ConfiguredFeature<? extends FeatureConfiguration, ?>> configKey() {
            return configuredFeatureRegistryKey;
        }

        public ResourceKey<PlacedFeature> key() {
            return featureRegistryKey;
        }

        public ResourceLocation identifier() {
            return featureIdentifier;
        }
    }
}