package com.rawsome1234.tape.block.custom;

import com.rawsome1234.tape.item.custom.Sack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class KidnappingSack extends Block {

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(13, 0, 3, 14, 1, 13),
            Block.box(3, 0, 3, 13, 1, 13),
            Block.box(3, 0, 2, 13, 1, 3),
            Block.box(3, 0, 13, 13, 1, 14),
            Block.box(2, 0, 3, 3, 1, 13),
            Block.box(2, 1, 2, 14, 2, 14),
            Block.box(3, 1, 14, 13, 2, 15),
            Block.box(3, 1, 1, 13, 2, 2),
            Block.box(14, 1, 3, 15, 2, 13),
            Block.box(1, 1, 3, 2, 2, 13),
            Block.box(1, 2, 1, 15, 12, 15),
            Block.box(0, 2, 2, 1, 12, 14),
            Block.box(15, 2, 2, 16, 12, 14),
            Block.box(2, 2, 0, 14, 12, 1),
            Block.box(2, 2, 15, 14, 12, 16),
            Block.box(3, 12, 1, 13, 13, 2),
            Block.box(1, 12, 3, 2, 13, 13),
            Block.box(3, 12, 14, 13, 13, 15),
            Block.box(14, 12, 3, 15, 13, 13),
            Block.box(2, 12, 2, 14, 13, 14),
            Block.box(6, 14, 6, 10, 15, 10),
            Block.box(5, 15, 5, 11, 16, 11),
            Block.box(13, 13, 3, 14, 14, 13),
            Block.box(3, 13, 13, 13, 14, 14),
            Block.box(2, 13, 3, 3, 14, 13),
            Block.box(3, 13, 3, 13, 14, 13),
            Block.box(3, 13, 2, 13, 14, 3)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public KidnappingSack(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_N;
    }


}
