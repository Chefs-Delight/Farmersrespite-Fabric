package com.chefsdelights.farmersrespite.common.block;

import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FREffects;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public class CoffeeCandleCakeBlock extends AbstractCandleBlock {
	public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
	protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
	protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
	protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
	private static final Map<Pair<Block, CoffeeCakeBlock>, CoffeeCandleCakeBlock> BY_CANDLE_AND_CAKE = Maps.newHashMap();
	private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

	private final Block candle;

	public CoffeeCandleCakeBlock(Block candle, Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
		this.candle = candle;

		BY_CANDLE_AND_CAKE.put(Pair.of(candle, (CoffeeCakeBlock) FRBlocks.COFFEE_CAKE), this);
	}

	protected Iterable<Vec3> getParticleOffsets(BlockState p_152868_) {
		return PARTICLE_OFFSETS;
	}

	public VoxelShape getShape(BlockState p_152875_, BlockGetter p_152876_, BlockPos p_152877_, CollisionContext p_152878_) {
		return SHAPE;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE) && FRBlocks.COFFEE_CAKE instanceof CoffeeCakeBlock cakeBlock) {
			if (candleHit(result) && player.getItemInHand(hand).isEmpty() && state.getValue(LIT)) {
				extinguish(player, state, level, pos);
				return InteractionResult.sidedSuccess(level.isClientSide);
			}	if ((itemstack.is(TagsRegistry.KNIVES))) {
				return cutSlice(level, pos, state, player);
			} else {
				return eatSlice(level, pos, state, player);
			}
		} else {
			return InteractionResult.PASS;
		}
	}
	
	public InteractionResult eatSlice(Level level, BlockPos pos, BlockState state, Player player) {
		if (!player.canEat(false)) {
			return InteractionResult.PASS;
		} else {
			player.awardStat(Stats.EAT_CAKE_SLICE);
			player.getFoodData().eat(2, 0.1F);
			if (!level.isClientSide()) {
				player.addEffect(new MobEffectInstance(FREffects.CAFFEINATED, 600, 0));
			}

			level.gameEvent(player, GameEvent.EAT, pos);
			level.setBlock(pos, FRBlocks.COFFEE_CAKE.defaultBlockState().setValue(CoffeeCakeBlock.BITES, 1), 3);
			Block.dropResources(state, level, pos);
			return InteractionResult.SUCCESS;
		}
	}
	
	public InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
		level.setBlock(pos, FRBlocks.COFFEE_CAKE.defaultBlockState().setValue(CoffeeCakeBlock.BITES, 1), 3);
		Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(FRItems.COFFEE_CAKE_SLICE));
		Block.dropResources(state, level, pos);
		level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
		return InteractionResult.SUCCESS;
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
		return new ItemStack(FRBlocks.COFFEE_CAKE);
	}

	private static boolean candleHit(BlockHitResult result) {
		return result.getLocation().y - (double) result.getBlockPos().getY() > 0.5D;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_152905_) {
		p_152905_.add(LIT);
	}

	public BlockState updateShape(BlockState state, Direction direction, BlockState p_152900_, LevelAccessor p_152901_, BlockPos p_152902_, BlockPos p_152903_) {
		return direction == Direction.DOWN && !state.canSurvive(p_152901_, p_152902_) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, p_152900_, p_152901_, p_152902_, p_152903_);
	}

	public boolean canSurvive(BlockState p_152891_, LevelReader p_152892_, BlockPos p_152893_) {
		return p_152892_.getBlockState(p_152893_.below()).getMaterial().isSolid();
	}

	public int getAnalogOutputSignal(BlockState p_152880_, Level p_152881_, BlockPos p_152882_) {
		return CakeBlock.FULL_CAKE_SIGNAL;
	}

	public boolean hasAnalogOutputSignal(BlockState p_152909_) {
		return true;
	}

	public boolean isPathfindable(BlockState p_152870_, BlockGetter p_152871_, BlockPos p_152872_, PathComputationType p_152873_) {
		return false;
	}

	public static BlockState byCandle(Block candle, CoffeeCakeBlock cake) {
		return BY_CANDLE_AND_CAKE.get(Pair.of(candle, cake)).defaultBlockState();
	}

	public Block getCandle() {
		return candle;
	}
}