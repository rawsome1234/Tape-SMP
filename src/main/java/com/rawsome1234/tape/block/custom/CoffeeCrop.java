package com.rawsome1234.tape.block.custom;

import com.rawsome1234.tape.blockproperties.BiomeTemperature;
import com.rawsome1234.tape.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CoffeeCrop extends CropsBlock {
    public static final EnumProperty<BiomeTemperature> BIOME_TEMP = EnumProperty.create("biome_temp", BiomeTemperature.class);


    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)
    };

    public CoffeeCrop(Properties p_i48421_1_) {

        super(p_i48421_1_);
    }

    @Override
    protected IItemProvider getBaseSeedId(){
        return ModItems.COFFEE_BEANS.get();
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected int getBonemealAgeIncrease(World world) {
        return MathHelper.nextInt(world.random, 1, 2);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())/2];
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(BIOME_TEMP);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        this.registerDefaultState(this.stateDefinition.any().setValue(BIOME_TEMP,
                BiomeTemperature.toEnum(context.getLevel().getBiome(context.getClickedPos()).getBiomeCategory())));
        return this.defaultBlockState();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader reader, BlockPos pos) {
        return state.is(Blocks.DIRT) || state.is(Blocks.GRASS_BLOCK);
    }
}
