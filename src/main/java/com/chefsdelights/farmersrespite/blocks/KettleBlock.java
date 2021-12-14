package com.umpaz.farmersrespite.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.umpaz.farmersrespite.registry.FRSounds;
import com.umpaz.farmersrespite.registry.FRTileEntityTypes;
import com.umpaz.farmersrespite.tile.KettleTileEntity;
import com.umpaz.farmersrespite.utils.FRTextUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.blocks.state.CookingPotSupport;
import vectorwing.farmersdelight.registry.ModSounds;
import vectorwing.farmersdelight.utils.MathUtils;
import vectorwing.farmersdelight.utils.tags.ModTags;

@SuppressWarnings("deprecation")
public class KettleBlock extends HorizontalBlock implements IWaterLoggable {

	public static final EnumProperty<CookingPotSupport> SUPPORT = EnumProperty.create("support", CookingPotSupport.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water", 0, 4);
    public static final BooleanProperty LID = BooleanProperty.create("lid");

    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
    protected static final VoxelShape SHAPE_WITH_TRAY = VoxelShapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

    public KettleBlock() {
    	super(Properties.of(Material.METAL)
				.strength(0.5F, 6.0F)
				.sound(SoundType.LANTERN));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SUPPORT, CookingPotSupport.NONE).setValue(WATERLOGGED, false).setValue(WATER_LEVEL, 0).setValue(LID, true));
    } 
    
    @Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		ItemStack heldStack = player.getItemInHand(handIn);
		TileEntity tileEntity = world.getBlockEntity(pos);
		KettleTileEntity kettleEntity = (KettleTileEntity) tileEntity;
		ItemStack servingStack = kettleEntity.useHeldItemOnMeal(heldStack);
        Item item = heldStack.getItem();
        int i = state.getValue(WATER_LEVEL);
        if (!world.isClientSide) {
		if (heldStack.isEmpty() && player.isShiftKeyDown()) {
			if (state.getValue(LID) == true) {
				world.setBlockAndUpdate(pos, state.setValue(LID, false));
				}
				if (state.getValue(LID) == false) {
				world.setBlockAndUpdate(pos, state.setValue(LID, true));
				}
			world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundCategory.BLOCKS, 0.7F, 1.0F);
		} else if (i < 4 && item == Items.WATER_BUCKET) {
	               if (!player.abilities.instabuild) {
	               player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
	               }
	               if (i < 2) {
	               this.setWaterLevel(world, pos, state, i + 3);
	               }
	               if (i == 2) {
	               this.setWaterLevel(world, pos, state, i + 2);
		           }
	               if (i == 3) {
		           this.setWaterLevel(world, pos, state, i + 1);
		           }
	               world.playSound((PlayerEntity)null, pos, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
	            }
		  else if (i < 4 && item == Items.POTION && PotionUtils.getPotion(heldStack) == Potions.WATER) {
	               if (!player.abilities.instabuild) {
	                  player.setItemInHand(handIn, new ItemStack(Items.GLASS_BOTTLE));
	               }
	               this.setWaterLevel(world, pos, state, i + 1);
	               world.playSound((PlayerEntity)null, pos, SoundEvents.BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
	            }
		  		else if (tileEntity instanceof KettleTileEntity) {
				if (servingStack != ItemStack.EMPTY) {
					if (!player.inventory.add(servingStack)) {
						player.drop(servingStack, false);
					}
					world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				 else {
					NetworkHooks.openGui((ServerPlayerEntity) player, kettleEntity, pos);
				}
		  	}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.SUCCESS;
	}

    @Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos = context.getClickedPos();
		World world = context.getLevel();
		FluidState fluid = world.getFluidState(context.getClickedPos());

		BlockState state = this.defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);

		if (context.getClickedFace().equals(Direction.DOWN)) {
			return state.setValue(SUPPORT, CookingPotSupport.HANDLE);
		}
		return state.setValue(SUPPORT, getTrayState(world, pos));
	}
    
	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		if (facing.getAxis().equals(Direction.Axis.Y) && !state.getValue(SUPPORT).equals(CookingPotSupport.HANDLE)) {
			return state.setValue(SUPPORT, getTrayState(world, currentPos));
		}
		return state;
	}

	private CookingPotSupport getTrayState(IWorld world, BlockPos pos) {
		if (world.getBlockState(pos.below()).getBlock().is(ModTags.TRAY_HEAT_SOURCES)) {
			return CookingPotSupport.TRAY;
		}
		return CookingPotSupport.NONE;
	}

    @Override
	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		ItemStack stack = super.getCloneItemStack(worldIn, pos, state);
		KettleTileEntity kettleEntity = (KettleTileEntity) worldIn.getBlockEntity(pos);
		if (kettleEntity != null) {
			CompoundNBT nbt = kettleEntity.writeMeal(new CompoundNBT());
			if (!nbt.isEmpty()) {
				stack.addTagElement("BlockEntityTag", nbt);
			}
			if (kettleEntity.hasCustomName()) {
				stack.setHoverName(kettleEntity.getCustomName());
			}
		}
		return stack;
	}
    
    public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
        worldIn.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 4))));
        worldIn.updateNeighbourForOutputSignal(pos, this);
     }

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof KettleTileEntity) {
				KettleTileEntity kettleEntity = (KettleTileEntity) tileEntity;
				InventoryHelper.dropContents(worldIn, pos, kettleEntity.getDroppableInventory());
				kettleEntity.grantStoredRecipeExperience(worldIn, Vector3d.atCenterOf(pos));
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
		if (nbt != null) {
			CompoundNBT inventoryTag = nbt.getCompound("Inventory");
			if (inventoryTag.contains("Items", 9)) {
				ItemStackHandler handler = new ItemStackHandler();
				handler.deserializeNBT(inventoryTag);
				ItemStack drinkStack = handler.getStackInSlot(2);
				if (!drinkStack.isEmpty()) {
					IFormattableTextComponent textServingsOf = drinkStack.getCount() == 1
							? FRTextUtils.getTranslation("tooltip.kettle.single_serving")
							: FRTextUtils.getTranslation("tooltip.kettle.many_servings", drinkStack.getCount());
					tooltip.add(textServingsOf.withStyle(TextFormatting.GRAY));
					IFormattableTextComponent textDrinkName = drinkStack.getHoverName().copy();
					tooltip.add(textDrinkName.withStyle(drinkStack.getRarity().color));
				}
			}
		} else {
			IFormattableTextComponent textEmpty = FRTextUtils.getTranslation("tooltip.kettle.empty");
			tooltip.add(textEmpty.withStyle(TextFormatting.GRAY));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, SUPPORT, WATERLOGGED, WATER_LEVEL, LID);
	}

	@Override
	public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof KettleTileEntity) {
				((KettleTileEntity) tileEntity).setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof KettleTileEntity && ((KettleTileEntity) tileEntity).isHeated() && ((KettleTileEntity)tileEntity).isDrinkEmpty() == true && stateIn.getValue(LID) == true) {
			double x = (double) pos.getX() + 0.5D;
			double y = pos.getY();
			double z = (double) pos.getZ() + 0.5D;
			if (rand.nextInt(20) == 0) {
				worldIn.playLocalSound(x, y, z, FRSounds.BLOCK_KETTLE_WHISTLE.get(), SoundCategory.BLOCKS, 0.07F, rand.nextFloat() * 0.2F + 0.9F, false);
			}
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof KettleTileEntity) {
			ItemStackHandler inventory = ((KettleTileEntity) tileEntity).getInventory();
			return MathUtils.calcRedstoneFromItemHandler(inventory);
		}
		return 0;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return FRTileEntityTypes.KETTLE_TILE.get().create();
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
