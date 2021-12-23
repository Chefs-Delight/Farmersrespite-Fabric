package com.chefsdelights.farmersrespite.tile;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.umpaz.farmersrespite.blocks.KettleBlock;
import com.umpaz.farmersrespite.crafting.KettleRecipe;
import com.umpaz.farmersrespite.registry.FRTileEntityTypes;
import com.umpaz.farmersrespite.tile.container.KettleContainer;
import com.umpaz.farmersrespite.tile.inventory.KettleItemHandler;
import com.umpaz.farmersrespite.utils.FRTextUtils;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.mixin.accessors.RecipeManagerAccessor;
import vectorwing.farmersdelight.tile.FDSyncedTileEntity;
import vectorwing.farmersdelight.tile.IHeatableTileEntity;
import vectorwing.farmersdelight.utils.ItemUtils;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KettleBlockEntity extends FDSyncedTileEntity implements INamedContainerProvider, ITickableTileEntity, IHeatableTileEntity, INameable {
	public static final int MEAL_DISPLAY_SLOT = 2;
	public static final int CONTAINER_SLOT = 3;
	public static final int OUTPUT_SLOT = 4;
	public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;

	private final ItemStackHandler inventory;
	private final LazyOptional<IItemHandler> inputHandler;
	private final LazyOptional<IItemHandler> outputHandler;

	private int brewTime;
	private int brewTimeTotal;
	private boolean needWater;
	private ItemStack drinkContainerStack;
	private ITextComponent customName;

	protected final IIntArray kettleData;
	private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

	private ResourceLocation lastRecipeID;
	private boolean checkNewRecipe;

	public KettleBlockEntity() {
		super(FRTileEntityTypes.KETTLE_TILE.get());
		this.inventory = createHandler();
		this.inputHandler = LazyOptional.of(() -> new KettleItemHandler(inventory, Direction.UP));
		this.outputHandler = LazyOptional.of(() -> new KettleItemHandler(inventory, Direction.DOWN));
		this.drinkContainerStack = ItemStack.EMPTY;
		this.kettleData = createIntArray();
		this.experienceTracker = new Object2IntOpenHashMap<>();
	}
	
	public boolean isLid() {
        if (this.level == null) {
            return false;
        } else {
            BlockState state = this.level.getBlockState(this.getBlockPos());
        	boolean i = state.getValue(KettleBlock.LID);
    		boolean flag = i == true;
    		if (flag)
            return true;
        }
        return false;
    }
	
	public boolean isWater() {
        if (this.level == null) {
            return false;
        } else {
            BlockState state = this.level.getBlockState(this.getBlockPos());
        	int i = state.getValue(KettleBlock.WATER_LEVEL);
    		boolean flag = i > 0;
    		if (flag)
            return true;
        }
        return false;
    }

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		inventory.deserializeNBT(compound.getCompound("Inventory"));
		brewTime = compound.getInt("BrewTime");
		brewTimeTotal = compound.getInt("BrewTimeTotal");
		needWater = compound.getBoolean("NeedWater");
		drinkContainerStack = ItemStack.of(compound.getCompound("Container"));
		if (compound.contains("CustomName", 8)) {
			customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
		}
		CompoundNBT compoundRecipes = compound.getCompound("RecipesUsed");
		for (String key : compoundRecipes.getAllKeys()) {
			experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
		}
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		compound.putInt("BrewTime", brewTime);
		compound.putInt("BrewTimeTotal", brewTimeTotal);
		compound.putBoolean("NeedWater", needWater);
		compound.put("Container", drinkContainerStack.serializeNBT());
		if (customName != null) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
		}
		compound.put("Inventory", inventory.serializeNBT());
		CompoundNBT compoundRecipes = new CompoundNBT();
		experienceTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
		compound.put("RecipesUsed", compoundRecipes);
		return compound;
	}

	private CompoundNBT writeItems(CompoundNBT compound) {
		super.save(compound);
		compound.put("Container", drinkContainerStack.serializeNBT());
		compound.put("Inventory", inventory.serializeNBT());
		return compound;
	}

	public CompoundNBT writeMeal(CompoundNBT compound) {
		if (getMeal().isEmpty()) return compound;

		ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);
		for (int i = 0; i < INVENTORY_SIZE; ++i) {
			drops.setStackInSlot(i, i == MEAL_DISPLAY_SLOT ? inventory.getStackInSlot(i) : ItemStack.EMPTY);
		}
		if (customName != null) {
			compound.putString("CustomName", ITextComponent.Serializer.toJson(customName));
		}
		compound.put("Container", drinkContainerStack.serializeNBT());
		compound.put("Inventory", drops.serializeNBT());
		return compound;
	}

	// ======== BASIC FUNCTIONALITY ========
	
	@Override
	public void tick() {
		if (level == null) return;

		boolean isHeated = isHeated(level, worldPosition);
		boolean didInventoryChange = false;

		if (!level.isClientSide) {
			if (isHeated && hasInput()) {
				Optional<KettleRecipe> recipe = getMatchingRecipe(new RecipeWrapper(inventory));
				if (recipe.isPresent() && canBrew(recipe.get())) {
					didInventoryChange = processBrewing(recipe.get());
				} else {
					brewTime = 0;
				}
			} else if (brewTime > 0) {
				brewTime = MathHelper.clamp(brewTime - 2, 0, brewTimeTotal);
			}

			ItemStack mealStack = getMeal();
			if (!mealStack.isEmpty()) {
				if (!doesMealHaveContainer(mealStack)) {
					moveMealToOutput();
					didInventoryChange = true;
				} else if (!inventory.getStackInSlot(CONTAINER_SLOT).isEmpty()) {
					useStoredContainersOnMeal();
					didInventoryChange = true;
				}
			}

		} else {
	        ItemStack meal = this.getMeal();
			if (isHeated && !meal.isEmpty()) {
				animate();
			}
		}

		if (didInventoryChange) {
			inventoryChanged();
		}
	}

	private Optional<KettleRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
		if (level == null) return Optional.empty();

		if (lastRecipeID != null) {
			IRecipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
					.getRecipeMap(KettleRecipe.TYPE)
					.get(lastRecipeID);
			if (recipe instanceof KettleRecipe) {
				if (recipe.matches(inventoryWrapper, level)) {
					return Optional.of((KettleRecipe) recipe);
				}
				if (recipe.getResultItem().sameItem(getMeal())) {
					return Optional.empty();
				}
			}
		}

		if (checkNewRecipe) {
			Optional<KettleRecipe> recipe = level.getRecipeManager().getRecipeFor(KettleRecipe.TYPE, inventoryWrapper, level);
			if (recipe.isPresent()) {
				lastRecipeID = recipe.get().getId();
				return recipe;
			}
		}

		checkNewRecipe = false;
		return Optional.empty();
	}

	public ItemStack getContainer() {
		if (!drinkContainerStack.isEmpty()) {
			return drinkContainerStack;
		} else {
			return getMeal().getContainerItem();
		}
	}

	private boolean hasInput() {
		for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
			if (!inventory.getStackInSlot(i).isEmpty()) return true;
		}
		return false;
	}

	protected boolean canBrew(KettleRecipe recipe) {
		needWater = recipe.getNeedWater();
		if (hasInput()) {
			ItemStack resultStack = recipe.getResultItem();
			if (resultStack.isEmpty()) {
				return false;
			} else {
				ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
			if ((isWater() || !needWater)) {
				if (storedMealStack.isEmpty()) {
					return true;
				} else if (!storedMealStack.sameItem(resultStack)) {
					return false;
				} else if (storedMealStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(MEAL_DISPLAY_SLOT)) {
					return true;
				} else {
					return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
					}
				}
			}
		}
		return false;
	}

	private boolean processBrewing(KettleRecipe recipe) {
		if (level == null) return false;
        BlockState state = this.level.getBlockState(this.worldPosition);
        int j = state.getValue(KettleBlock.WATER_LEVEL);

		++brewTime;
		brewTimeTotal = recipe.getBrewTime();
		
		if (brewTime < brewTimeTotal) {
			return false;
		}

		brewTime = 0;
		drinkContainerStack = recipe.getOutputContainer();
		ItemStack resultStack = recipe.getResultItem();
		ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		if (storedMealStack.isEmpty()) {
			inventory.setStackInSlot(MEAL_DISPLAY_SLOT, resultStack.copy());
		} else if (storedMealStack.sameItem(resultStack)) {
			storedMealStack.grow(resultStack.getCount());
		}
		trackRecipeExperience(recipe);
		if (needWater) {
		setWaterLevel(level, worldPosition, state, j - 1);
		}
		
		for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
			ItemStack slotStack = inventory.getStackInSlot(i);
			if (slotStack.hasContainerItem()) {
				Direction direction = getBlockState().getValue(KettleBlock.FACING).getCounterClockWise();
				double x = worldPosition.getX() + 0.5 + (direction.getStepX() * 0.25);
				double y = worldPosition.getY() + 0.7;
				double z = worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.25);
				ItemUtils.spawnItemEntity(level, inventory.getStackInSlot(i).getContainerItem(), x, y, z,
						direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
			}
			if (!slotStack.isEmpty())
				slotStack.shrink(1);
		}
		return true;
	}
	
	public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockAndUpdate(pos, state.setValue(KettleBlock.WATER_LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 4))));
     }


	private void animate() {
	        BlockState state = this.level.getBlockState(this.worldPosition);
	        boolean i = state.getValue(KettleBlock.LID);
	        if (level != null) {
	            if (i == true) { 
	            Direction direction = state.getValue(KettleBlock.FACING);
	            Direction.Axis direction$axis = direction.getAxis();
	            	 double d0 = (double)worldPosition.getX() + 0.5D;
	                 double d1 = (double)worldPosition.getY() + 0.5D;
	                 double d2 = (double)worldPosition.getZ() + 0.5D;
	                 double d4 = 0.0D;
	                 double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52D : d4;
	                 double d6 = 0.0D;
	                 double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52D : d4;
	          		if (level.random.nextInt(5) == 0) {
	                 level.addParticle(ParticleTypes.EFFECT, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
	          		} 
	            }
	        }
	    }

	public void trackRecipeExperience(@Nullable IRecipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation recipeID = recipe.getId();
			experienceTracker.addTo(recipeID, 1);
		}
	}

	public void clearUsedRecipes(PlayerEntity player) {
		grantStoredRecipeExperience(player.level, player.position());
		experienceTracker.clear();
	}

	public void grantStoredRecipeExperience(World world, Vector3d pos) {
		for (Object2IntMap.Entry<ResourceLocation> entry : experienceTracker.object2IntEntrySet()) {
			world.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> splitAndSpawnExperience(world, pos, entry.getIntValue(), ((KettleRecipe) recipe).getExperience()));
		}
	}

	private static void splitAndSpawnExperience(World world, Vector3d pos, int craftedAmount, float experience) {
		int expTotal = MathHelper.floor((float) craftedAmount * experience);
		float expFraction = MathHelper.frac((float) craftedAmount * experience);
		if (expFraction != 0.0F && Math.random() < (double) expFraction) {
			++expTotal;
		}

		while (expTotal > 0) {
			int expValue = ExperienceOrbEntity.getExperienceValue(expTotal);
			expTotal -= expValue;
			world.addFreshEntity(new ExperienceOrbEntity(world, pos.x, pos.y, pos.z, expValue));
		}
	}

	public boolean isHeated() {
		if (level == null) return false;
		return this.isHeated(level, worldPosition);
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}

	public ItemStack getMeal() {
		return inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
	}

	public NonNullList<ItemStack> getDroppableInventory() {
		NonNullList<ItemStack> drops = NonNullList.create();
		for (int i = 0; i < INVENTORY_SIZE; ++i) {
			if (i != MEAL_DISPLAY_SLOT) {
				drops.add(inventory.getStackInSlot(i));
			}
		}
		return drops;
	}

	private void moveMealToOutput() {
		ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
		int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
		if (outputStack.isEmpty()) {
			inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
		} else if (outputStack.getItem() == mealStack.getItem()) {
			mealStack.shrink(mealCount);
			outputStack.grow(mealCount);
		}
	}

	private void useStoredContainersOnMeal() {
		ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		ItemStack containerInputStack = inventory.getStackInSlot(CONTAINER_SLOT);
		ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);

		if (isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
			int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
			int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
			if (outputStack.isEmpty()) {
				containerInputStack.shrink(mealCount);
				inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
			} else if (outputStack.getItem() == mealStack.getItem()) {
				mealStack.shrink(mealCount);
				containerInputStack.shrink(mealCount);
				outputStack.grow(mealCount);
			}
		}
	}

	public ItemStack useHeldItemOnMeal(ItemStack container) {
		if (isContainerValid(container) && !getMeal().isEmpty()) {
			container.shrink(1);
			return getMeal().split(1);
		}
		return ItemStack.EMPTY;
	}

	private boolean doesMealHaveContainer(ItemStack meal) {
		return !drinkContainerStack.isEmpty() || meal.hasContainerItem();
	}

	public boolean isContainerValid(ItemStack containerItem) {
		if (containerItem.isEmpty()) return false;
		if (!drinkContainerStack.isEmpty()) {
			return drinkContainerStack.sameItem(containerItem);
		} else {
			return getMeal().getContainerItem().sameItem(containerItem);
		}
	}
	
	public boolean isDrinkEmpty() {
        if (this.level == null) {
            return false;
        } else {
            ItemStack meal = this.getMeal();
            if (!meal.isEmpty() && this.isHeated()) {
                return true;
            }
            return false;
        }
    }

	@Override
	public ITextComponent getName() {
		return customName != null ? customName : FRTextUtils.getTranslation("container.kettle");
	}

	@Override
	public ITextComponent getDisplayName() {
		return getName();
	}

	@Override
	@Nullable
	public ITextComponent getCustomName() {
		return customName;
	}

	public void setCustomName(ITextComponent name) {
		customName = name;
	}

	@Override
	public Container createMenu(int id, PlayerInventory player, PlayerEntity entity) {
		return new KettleContainer(id, player, this, kettleData);
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			if (side == null || side.equals(Direction.UP)) {
				return inputHandler.cast();
			} else {
				return outputHandler.cast();
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		inputHandler.invalidate();
		outputHandler.invalidate();
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return writeItems(new CompoundNBT());
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(INVENTORY_SIZE)
		{
			@Override
			protected void onContentsChanged(int slot) {
				if (slot >= 0 && slot < MEAL_DISPLAY_SLOT) {
					checkNewRecipe = true;
					inventoryChanged();
				}
			}
		};
	}

	private IIntArray createIntArray() {
		return new IIntArray()
		{
			@Override
			public int get(int index) {
				switch (index) {
					case 0:
						return KettleBlockEntity.this.brewTime;
					case 1:
						return KettleBlockEntity.this.brewTimeTotal;
					default:
						return 0;
				}
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0:
						KettleBlockEntity.this.brewTime = value;
						break;
					case 1:
						KettleBlockEntity.this.brewTimeTotal = value;
						break;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}
}
