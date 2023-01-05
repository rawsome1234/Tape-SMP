package com.rawsome1234.tape.block.custom;

import com.rawsome1234.tape.block.ModBlocks;
import com.rawsome1234.tape.container.CoffeeMakerContainer;
import com.rawsome1234.tape.tileentity.CoffeeMakerTile;
import com.rawsome1234.tape.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.*;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.core.jmx.Server;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CoffeeMakerBlock extends HorizontalBlock {

    BlockPos pos;
    List<ItemStack> drops = new ArrayList<ItemStack>();
    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(1, 0, 1, 15, 2, 15),
            Block.box(2, 2, 11, 14, 12, 14),
            Block.box(0, 12, 0, 16, 14, 16),
            Block.box(10, 2, 2, 14, 12, 11),
            Block.box(4, 10, 5, 6, 12, 7),
            Block.box(9, 14, 1, 10, 16, 15),
            Block.box(14, 14, 1, 15, 16, 15),
            Block.box(10, 14, 1, 14, 16, 2),
            Block.box(10, 14, 14, 14, 16, 15),
            Block.box(2, 14, 14, 8, 16, 15),
            Block.box(2, 14, 9, 8, 16, 10),
            Block.box(2, 14, 10, 3, 16, 14),
            Block.box(7, 14, 10, 8, 16, 14),
            Block.box(3, 14, 10, 7, 15, 14),
            Block.box(10, 14, 2, 14, 15, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(1, 0, 1, 15, 2, 15),
            Block.box(11, 2, 2, 14, 12, 14),
            Block.box(0, 12, 0, 16, 14, 16),
            Block.box(2, 2, 2, 11, 12, 6),
            Block.box(5, 10, 10, 7, 12, 12),
            Block.box(1, 14, 6, 15, 16, 7),
            Block.box(1, 14, 1, 15, 16, 2),
            Block.box(1, 14, 2, 2, 16, 6),
            Block.box(14, 14, 2, 15, 16, 6),
            Block.box(14, 14, 8, 15, 16, 14),
            Block.box(9, 14, 8, 10, 16, 14),
            Block.box(10, 14, 13, 14, 16, 14),
            Block.box(10, 14, 8, 14, 16, 9),
            Block.box(10, 14, 9, 14, 15, 13),
            Block.box(2, 14, 2, 14, 15, 6)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(1, 0, 1, 15, 2, 15),
            Block.box(2, 2, 2, 14, 12, 5),
            Block.box(0, 12, 0, 16, 14, 16),
            Block.box(2, 2, 5, 6, 12, 14),
            Block.box(10, 10, 9, 12, 12, 11),
            Block.box(6, 14, 1, 7, 16, 15),
            Block.box(1, 14, 1, 2, 16, 15),
            Block.box(2, 14, 14, 6, 16, 15),
            Block.box(2, 14, 1, 6, 16, 2),
            Block.box(8, 14, 1, 14, 16, 2),
            Block.box(8, 14, 6, 14, 16, 7),
            Block.box(13, 14, 2, 14, 16, 6),
            Block.box(8, 14, 2, 9, 16, 6),
            Block.box(9, 14, 2, 13, 15, 6),
            Block.box(2, 14, 2, 6, 15, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(1, 0, 1, 15, 2, 15),
            Block.box(2, 2, 2, 5, 12, 14),
            Block.box(0, 12, 0, 16, 14, 16),
            Block.box(5, 2, 10, 14, 12, 14),
            Block.box(9, 10, 4, 11, 12, 6),
            Block.box(1, 14, 9, 15, 16, 10),
            Block.box(1, 14, 14, 15, 16, 15),
            Block.box(14, 14, 10, 15, 16, 14),
            Block.box(1, 14, 10, 2, 16, 14),
            Block.box(1, 14, 2, 2, 16, 8),
            Block.box(6, 14, 2, 7, 16, 8),
            Block.box(2, 14, 2, 6, 16, 3),
            Block.box(2, 14, 7, 6, 16, 8),
            Block.box(2, 14, 3, 6, 15, 7),
            Block.box(2, 14, 10, 14, 15, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();



    public CoffeeMakerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_196243_5_) {
        drops = genDrops(world);
        super.onRemove(state, world, pos, state2, p_196243_5_);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return drops;
    }

    private List<ItemStack> genDrops(World world){
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.add(this.asItem().getDefaultInstance());
        if(pos != null){
            TileEntity tileEntity = world.getBlockEntity(pos);
            if(tileEntity instanceof CoffeeMakerTile){
                for(ItemStack stack: ((CoffeeMakerTile) tileEntity).getDrops()){
                    System.out.println(stack);
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if(!world.isClientSide){
            TileEntity tileEntity = world.getBlockEntity(pos);
//            System.out.println("using coffee maker...");
            if(!player.isShiftKeyDown()){
//                System.out.println("not shifting");
                if(tileEntity instanceof CoffeeMakerTile){
                    this.pos = pos;
                    INamedContainerProvider containerProvider = createContainerProvider(world, pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getBlockPos());
                }
                else{
                    throw new IllegalStateException("Container Provider missing");
                }
            }

        }
        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World world, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.tape.coffee_maker");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {
                return new CoffeeMakerContainer(i, world, pos, inventory, player);
            }
        };
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.COFFEE_MAKER_TILE.get().create();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.getValue(FACING)){
            case WEST: return SHAPE_W;
            case SOUTH: return SHAPE_S;
            case EAST: return SHAPE_E;
            default: return SHAPE_N;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
//        builder.add()
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


}
