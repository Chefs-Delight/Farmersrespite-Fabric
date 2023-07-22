package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.common.block.entity.KettleBlockEntity;
import com.chefsdelights.farmersrespite.common.block.entity.inventory.ItemHandler;
import com.chefsdelights.farmersrespite.core.registry.FRBlockEntityTypes;
import com.chefsdelights.farmersrespite.core.registry.FRSounds;
import com.chefsdelights.farmersrespite.core.utility.FRTextUtils;
import com.chefsdelights.farmersrespite.core.utility.MathUtils;
import com.nhoryzon.mc.farmersdelight.block.state.CookingPotSupport;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.util.NetworkUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class KettleBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<CookingPotSupport> SUPPORT = EnumProperty.create("support", CookingPotSupport.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water", 0, 3);
    public static final BooleanProperty LID = BooleanProperty.create("lid");

    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
	protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

	public KettleBlock() {
		super(Properties.of(Material.METAL)
				.strength(0.5F, 6.0F)
				.sound(SoundType.LANTERN));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SUPPORT, CookingPotSupport.NONE).setValue(WATERLOGGED, false).setValue(WATER_LEVEL, 0).setValue(LID, true));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
		ItemStack heldStack = player.getItemInHand(handIn);
        Item item = heldStack.getItem();
		BlockEntity tileEntity = world.getBlockEntity(pos);
        int i = state.getValue(WATER_LEVEL);
		if (!world.isClientSide) {
		if (heldStack.isEmpty() && player.isShiftKeyDown()) {
			if (state.getValue(LID)) {
				world.setBlockAndUpdate(pos, state.setValue(LID, false));
				}
				if (!state.getValue(LID)) {
				world.setBlockAndUpdate(pos, state.setValue(LID, true));
				}
			world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
		}  else if (i < 3 && item == Items.WATER_BUCKET) {
			if (!player.getAbilities().instabuild) {
            player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
            }
            if (i == 0) {
				world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, i + 3));
            }
            if (i == 1) {
				world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, i + 2));
            }
            if (i == 2) {
				world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, i + 1));
	           }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
         }
	  else if (i < 3 && item == Items.POTION && PotionUtils.getPotion(heldStack) == Potions.WATER) {
            if (!player.getAbilities().instabuild) {
               player.setItemInHand(handIn, new ItemStack(Items.GLASS_BOTTLE));
            }
			world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, i + 1));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
         }
	  	else if (tileEntity instanceof KettleBlockEntity kettleEntity) {
			ItemStack servingStack = kettleEntity.useHeldItemOnMeal(heldStack);
				if (servingStack != ItemStack.EMPTY) {
					if (!player.getInventory().add(servingStack)) {
						player.drop(servingStack, false);
					}
					world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
				} else {
					NetworkUtil.openGui((ServerPlayer) player, kettleEntity, pos);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		Level world = context.getLevel();
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
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) {
			world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		}
		if (facing.getAxis().equals(Direction.Axis.Y) && !state.getValue(SUPPORT).equals(CookingPotSupport.HANDLE)) {
			return state.setValue(SUPPORT, getTrayState(world, currentPos));
		}
		return state;
	}

	private CookingPotSupport getTrayState(LevelAccessor world, BlockPos pos) {
		if (world.getBlockState(pos.below()).is(TagsRegistry.TRAY_HEAT_SOURCES)) {
			return CookingPotSupport.TRAY;
		}
		return CookingPotSupport.NONE;
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		ItemStack stack = super.getCloneItemStack(worldIn, pos, state);
		KettleBlockEntity kettleEntity = (KettleBlockEntity) worldIn.getBlockEntity(pos);
		if (kettleEntity != null) {
			CompoundTag nbt = kettleEntity.writeMeal(new CompoundTag());
			if (!nbt.isEmpty()) {
				stack.addTagElement("BlockEntityTag", nbt);
			}
			if (kettleEntity.hasCustomName()) {
				stack.setHoverName(kettleEntity.getCustomName());
			}
		}
		return stack;
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof KettleBlockEntity kettleEntity) {
				Containers.dropContents(worldIn, pos, kettleEntity.getDroppableInventory());
				kettleEntity.grantStoredRecipeExperience(worldIn, Vec3.atCenterOf(pos));
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		CompoundTag nbt = stack.getTagElement("BlockEntityTag");
		if (nbt != null) {
			CompoundTag inventoryTag = nbt.getCompound("Inventory");
			if (inventoryTag.contains("Items", 9)) {
				ItemStackHandler handler = new ItemStackHandler();
				handler.deserializeNBT(inventoryTag);
				ItemStack mealStack = handler.getStackInSlot(2);
				if (!mealStack.isEmpty()) {
					MutableComponent textServingsOf = mealStack.getCount() == 1
							? FRTextUtils.getTranslation("tooltip.kettle.single_serving")
							: FRTextUtils.getTranslation("tooltip.kettle.many_servings", mealStack.getCount());
					tooltip.add(textServingsOf.withStyle(ChatFormatting.GRAY));
					MutableComponent textMealName = mealStack.getHoverName().copy();
					tooltip.add(textMealName.withStyle(mealStack.getRarity().color));
				}
			}
		} else {
			MutableComponent textEmpty = FRTextUtils.getTranslation("tooltip.kettle.empty");
			tooltip.add(textEmpty.withStyle(ChatFormatting.GRAY));
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, SUPPORT, WATERLOGGED, WATER_LEVEL, LID);
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			BlockEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof KettleBlockEntity) {
				((KettleBlockEntity) tileEntity).setCustomName(stack.getHoverName());
			}
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof KettleBlockEntity && ((KettleBlockEntity) tileEntity).isHeated() && ((KettleBlockEntity) tileEntity).isHeated() && stateIn.getValue(LID) == true) {
			double x = pos.getX() + 0.5D;
			double y = pos.getY();
			double z = pos.getZ() + 0.5D;
			if (rand.nextInt(20) == 0) {
				worldIn.playLocalSound(x, y, z, FRSounds.BLOCK_KETTLE_WHISTLE, SoundSource.BLOCKS, 0.07F, rand.nextFloat() * 0.2F + 0.9F, false);

			}
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof KettleBlockEntity) {
			ItemHandler inventory = ((KettleBlockEntity) tileEntity).getInventory();
			return MathUtils.calcRedstoneFromItemHandler(inventory);
		}
		return 0;
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return FRBlockEntityTypes.KETTLE.create(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
		if (level.isClientSide) {
			return createTickerHelper(blockEntity, FRBlockEntityTypes.KETTLE, KettleBlockEntity::animationTick);
		} else {
			return createTickerHelper(blockEntity, FRBlockEntityTypes.KETTLE, KettleBlockEntity::cookingTick);
		}
	}
}
