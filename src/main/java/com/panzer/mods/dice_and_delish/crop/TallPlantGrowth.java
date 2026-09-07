package com.panzer.mods.dice_and_delish.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface TallPlantGrowth {

    EnumProperty<DoubleBlockHalf> halfProperty();

    default boolean upperHalfCanSurvive(@NotNull Block self, @NotNull LevelReader level, @NotNull BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return below.is(self) && below.getValue(halfProperty()) == DoubleBlockHalf.LOWER;
    }

    default boolean resolveTallGrowth(@NotNull Block self, @NotNull Level level, @NotNull BlockPos lowerPos,
                                      @NotNull IntegerProperty ageProperty, int nextAge, int splitThreshold,
                                      @NotNull Function<Block, BlockState> upperStateFactory) {
        BlockPos upperPos = lowerPos.above();
        BlockState upperState = level.getBlockState(upperPos);
        boolean upperExists = upperState.is(self) && upperState.getValue(halfProperty()) == DoubleBlockHalf.UPPER;

        if (nextAge < splitThreshold) {
            if (upperExists) {
                level.setBlock(upperPos, Blocks.AIR.defaultBlockState(), 35);
            }
            return true;
        }

        if (upperExists) {
            level.setBlock(upperPos, upperState.setValue(ageProperty, nextAge), 2);
            return true;
        }

        if (!upperState.canBeReplaced()) {
            return false;
        }

        level.setBlock(upperPos, upperStateFactory.apply(self)
                .setValue(halfProperty(), DoubleBlockHalf.UPPER)
                .setValue(ageProperty, nextAge), 3);
        return true;
    }
}
