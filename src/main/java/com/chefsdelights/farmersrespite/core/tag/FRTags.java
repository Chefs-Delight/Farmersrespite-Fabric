package com.chefsdelights.farmersrespite.core.tag;

import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FRTags {

	public FRTags() {
		super();
	}
	
	// Tea Leaves
	public static final TagKey<Item> TEA_LEAVES = modItemTag("tea_leaves");

	private static TagKey<Item> modItemTag(String path) {
		return ItemTags.bind(FarmersRespite.MOD_ID + ":" + path);
	}
}