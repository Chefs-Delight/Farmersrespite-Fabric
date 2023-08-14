package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.common.block.state.WitherRootsUtil;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoffeeMiddleStemBlock extends BushBlock implements BonemealableBlock {
    public static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public CoffeeMiddleStemBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(FRBlocks.COFFEE_STEM_DOUBLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        pLevel.destroyBlock(pPos.below(), true, pPlayer);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.COFFEE_BEANS);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        for (BlockPos neighborPos : WitherRootsUtil.randomInSquareDown(random, pos, 2)) {
            BlockState neighborState = level.getBlockState(neighborPos);
            BlockState witherRootsState = random.nextInt(2) == 0 ? FRBlocks.WITHER_ROOTS.defaultBlockState() : FRBlocks.WITHER_ROOTS_PLANT.defaultBlockState();
            if ((random.nextInt(2) == 0)) {
                if (neighborState.getBlock() instanceof CropBlock) {
                    level.setBlockAndUpdate(neighborPos, witherRootsState);
                    performBonemeal(level, random, pos, state);
                } else if (level.dimensionType().ultraWarm()) {
                    performBonemeal(level, random, pos, state);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        int i = state.getValue(AGE);
        boolean flag = i == 2;
        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        } else if (i == 2) {
            popResource(world, pos, new ItemStack(FRItems.COFFEE_BERRIES, 1));
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, 0), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else {
            return super.use(state, world, pos, player, handIn, result);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        int i = state.getValue(AGE);
        return !(i == 2);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
        int i = state.getValue(AGE);
        level.setBlockAndUpdate(pos, state.setValue(AGE, i + 1));
    }
}
