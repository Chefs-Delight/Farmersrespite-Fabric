package com.chefsdelights.farmersrespite.utils;

import com.umpaz.farmersrespite.FarmersRespite;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class FRTags {

	public static final ITag.INamedTag<Item> TEA_LEAVES = modItemTag("tea_leaves");

	private static ITag.INamedTag<Item> modItemTag(String path) {
		return ItemTags.bind(FarmersRespite.MODID + ":" + path);
	}

}
