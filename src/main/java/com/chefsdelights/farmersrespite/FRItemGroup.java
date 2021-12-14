package com.umpaz.farmersrespite;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import com.umpaz.farmersrespite.registry.FRBlocks;
import com.umpaz.farmersrespite.registry.FRItems;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class FRItemGroup extends ItemGroup
{
	public FRItemGroup(String label) {
		super(label);
	}

	@Nonnull
	@Override
	public ItemStack makeIcon() {
		return new ItemStack(FRBlocks.KETTLE.get());
	}
}
