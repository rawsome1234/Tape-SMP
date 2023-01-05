package com.rawsome1234.tape.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class Launchpad extends HorizontalBlock {

    public Launchpad(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(12, 2, 3, 13, 4, 4),
            Block.box(13, 0, 2, 14, 2, 3),
            Block.box(2, 0, 2, 3, 2, 3),
            Block.box(2, 0, 13, 3, 2, 14),
            Block.box(13, 0, 13, 14, 2, 14),
            Block.box(12, 2, 12, 13, 4, 13),
            Block.box(3, 2, 12, 4, 4, 13),
            Block.box(3, 2, 3, 4, 4, 4),
            Block.box(4, 4, 12, 12, 5, 13),
            Block.box(4, 4, 3, 12, 5, 4),
            Block.box(3, 4, 4, 4, 5, 12),
            Block.box(12, 4, 4, 13, 5, 12),
            Block.box(4, 3, 4, 12, 4, 12)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_N;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
        p_180658_3_.causeFallDamage(p_180658_4_, 0.0F);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void stepOn(World world, BlockPos blockPos, Entity entity) {
        Direction motion = entity.getMotionDirection();
        entity.setDeltaMovement(0, 2, 0);


        super.stepOn(world, blockPos, entity);
    }




}
