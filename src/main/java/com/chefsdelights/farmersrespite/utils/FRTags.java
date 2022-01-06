package com.chefsdelights.farmersrespite.utils;

import com.chefsdelights.farmersrespite.FarmersRespite;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FRTags {
	public static final Tag.Identified<Item> TEA_LEAVES = create("tea_leaves", TagFactory.ITEM);

	private static <E> Tag.Identified<E> create(String pathName, TagFactory<E> tagFactory) {
		return tagFactory.create(new Identifier(FarmersRespite.MOD_ID, pathName));
	}
}