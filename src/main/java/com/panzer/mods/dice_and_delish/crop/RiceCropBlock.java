package com.panzer.mods.dice_and_delish.crop;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
//? if >=1.21.2 {
/*import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ScheduledTickAccess;
*///?}
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class RiceCropBlock extends ModCropBlock implements TallPlantHalves {

    private static final int MAX_AGE = 4;
    private static final int SPLIT_THRESHOLD = 3;
    private static final VoxelShape HITBOX = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private final MapCodec<RiceCropBlock> codec;

    public RiceCropBlock(BlockBehaviour.Properties properties, VoxelShape[] shapes,
                         Supplier<? extends ItemLike> seedSupplier) {
        super(properties, MAX_AGE, shapes, seedSupplier);
        this.codec = simpleCodec(props -> new RiceCropBlock(props, shapes, seedSupplier));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(getAgeProperty(), 0)
                .setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public @NotNull MapCodec<RiceCropBlock> codec() {
        return this.codec;
    }

    @Override
    protected @NotNull IntegerProperty getAgeProperty() {
        return BlockStateProperties.AGE_4;
    }

    @Override
    public @NotNull EnumProperty<DoubleBlockHalf> halfProperty() {
        return HALF;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.AGE_4, HALF);
    }

    @Override
    protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Blocks.MUD);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public @NotNull BlockState lowerStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public @NotNull BlockState upperStateForPlacedBy(@NotNull Level level, @NotNull BlockPos lowerPos, @NotNull BlockState lowerState) {
        return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER);
    }

    @Override
    protected boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState belowState = level.getBlockState(pos.below());
            return belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int age = state.getValue(getAgeProperty());
        DoubleBlockHalf half = state.getValue(HALF);

        if (half == DoubleBlockHalf.LOWER) {
            if (age < SPLIT_THRESHOLD) {
                return super.getShape(state, level, pos, context);
            }
            return HITBOX;
        } else { // UPPER
            if (age >= SPLIT_THRESHOLD) {
                return HITBOX;
            }
            return Shapes.empty();
        }
    }

    @Override
    protected void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return;
        }

        int age = state.getValue(getAgeProperty());
        if (age >= getMaxAge()) {
            return;
        }

        float growthSpeed = getGrowthSpeed(state, level, pos);
        if (random.nextInt((int)(25.0F / growthSpeed) + 1) != 0) {
            return;
        }

        int nextAge = age + 1;

        level.setBlock(pos, state.setValue(getAgeProperty(), nextAge), 3);

        if (!resolveTallGrowth(this, level, pos, getAgeProperty(), nextAge, SPLIT_THRESHOLD,
                block -> this.defaultBlockState())) {
            level.setBlock(pos, state, 3);
        }
    }

    @Override
    //? if <1.21.2 {
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction,
                                           @NotNull BlockState neighborState, @NotNull LevelAccessor level,
                                           @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (direction.getAxis() == Direction.Axis.Y) {
            DoubleBlockHalf half = state.getValue(HALF);
            if ((half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) ||
                    (half == DoubleBlockHalf.LOWER && direction == Direction.UP && state.getValue(getAgeProperty()) >= SPLIT_THRESHOLD)) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) == half) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
    //?} else {
    /*protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level,
                                              @NotNull ScheduledTickAccess scheduledTickAccess, @NotNull BlockPos pos,
                                              @NotNull Direction direction, @NotNull BlockPos neighborPos,
                                              @NotNull BlockState neighborState, @NotNull RandomSource random) {
        if (direction.getAxis() == Direction.Axis.Y) {
            DoubleBlockHalf half = state.getValue(HALF);
            if ((half == DoubleBlockHalf.UPPER && direction == Direction.DOWN) ||
                (half == DoubleBlockHalf.LOWER && direction == Direction.UP && state.getValue(getAgeProperty()) >= SPLIT_THRESHOLD)) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) == half) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }
    *///?}

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        tallPlantPlayerWillDestroy(this, level, pos, state, player);
        return super.playerWillDestroy(level, pos, state, player);
    }
}
