package com.chefsdelights.farmersrespite.registry;

import com.umpaz.farmersrespite.FarmersRespite;
import com.umpaz.farmersrespite.tile.KettleTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FRTileEntityTypes {
	public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FarmersRespite.MODID);

	public static final RegistryObject<TileEntityType<KettleTileEntity>> KETTLE_TILE = TILES.register("kettle",
			() -> TileEntityType.Builder.of(KettleTileEntity::new, FRBlocks.KETTLE.get()).build(null));
}
