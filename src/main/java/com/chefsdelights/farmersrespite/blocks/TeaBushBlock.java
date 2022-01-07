package com.chefsdelights.farmersrespite.blocks;

import com.chefsdelights.farmersrespite.registry.FRBlocks;
import com.chefsdelights.farmersrespite.registry.FRItems;
import com.chefsdelights.farmersrespite.setup.FRConfiguration;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class TeaBushBlock extends PlantBlock implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_LOWER = VoxelShapes.union(Block.createCuboidShape(0.0D, 11.0D, 0.0D, 16.0D, 24.0D, 16.0D), Block.createCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D));
    private static final VoxelShape SHAPE_UPPER = VoxelShapes.union(Block.createCuboidShape(0.0D, -5.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(6.0D, -16.0D, 6.0D, 10.0D, -5.0D, 10.0D));

    public TeaBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE_UPPER;
        }
        return SHAPE_LOWER;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.TEA_SEEDS);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3 && state.get(HALF) == DoubleBlockHalf.LOWER;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleblockhalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return neighborState.isOf(this) && neighborState.get(HALF) != doubleblockhalf ? state.with(AGE, neighborState.get(AGE)) : Blocks.AIR.getDefaultState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((state.get(HALF) == DoubleBlockHalf.LOWER) && random.nextInt(10) == 0) {
            this.performBonemeal(world, random, pos, state);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            this.onBreakInCreative(world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        if (blockPos.getY() < 255 && ctx.getWorld().getBlockState(blockPos.up()).canReplace(ctx)) {
            return this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    protected void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos = pos.down();
            BlockState blockstate = world.getBlockState(blockPos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.get(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockstate));
            }
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockstate = world.getBlockState(blockPos);
        return state.get(HALF) == DoubleBlockHalf.LOWER ? blockstate.isSideSolidFullSquare(world, blockPos, Direction.UP) : blockstate.isOf(this);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(AGE);
        ItemStack heldStack = player.getStackInHand(hand);
        Item item = heldStack.getItem();

        if (item == Items.SHEARS) {
            int j = world.random.nextInt(2);
            int k = 2 + world.random.nextInt(2);
            int l = world.random.nextInt(2);

            if (i == 0) {
                dropStack(world, pos, new ItemStack(FRItems.GREEN_TEA_LEAVES, 2 + j));
            }
            if (i == 1) {
                dropStack(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES, 2 + j));
            }
            if (i == 2) {
                dropStack(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES, 1 + j));
                dropStack(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES, 1 + l));
            }
            if (i == 3) {
                dropStack(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES, 2 + j));
            }
            if (state.get(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockState(pos, FRBlocks.SMALL_TEA_BUSH.getDefaultState());
            }
            if (state.get(HALF) == DoubleBlockHalf.UPPER) {
                world.setBlockState(pos.down(), FRBlocks.SMALL_TEA_BUSH.getDefaultState());
            }
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            dropStack(world, pos, new ItemStack(Items.STICK, k));
            heldStack.damage(1, player, (p_226874_1_) -> player.broadcastBreakEvent(hand));
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        int i = state.get(AGE);
        if (i != 3) {
            return FRConfiguration.BONE_MEAL_TEA.get();
        }
        return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = state.get(AGE);
        world.setBlockState(pos, state.with(AGE, i + 1));
    }
}