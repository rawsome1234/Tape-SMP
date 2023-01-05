package com.rawsome1234.tape.tileentity;

import com.rawsome1234.tape.data.recipes.CoffeeMakerRecipe;
import com.rawsome1234.tape.data.recipes.ModRecipeTypes;
import com.rawsome1234.tape.item.ModItems;
import com.rawsome1234.tape.util.TapeTags;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoffeeMakerTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int waterBottles;
    public int maxWaterStore = 12;

    public int ticksInRecipe;
    public int ticksLeft;

    private CoffeeMakerRecipe recipe;

    public CoffeeMakerTile(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        ticksLeft = 0;
        waterBottles = 0;
        ticksInRecipe = -1;
    }

    public CoffeeMakerTile(){
        this(ModTileEntities.COFFEE_MAKER_TILE.get());
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.putInt("water", waterBottles);
        nbt.putInt("maxWater", maxWaterStore);
        nbt.putInt("ticksRemaining", ticksLeft);
        nbt.putInt("totalTicks", ticksInRecipe);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        waterBottles = nbt.getInt("water");
        maxWaterStore = nbt.getInt("maxWater");
        ticksLeft = nbt.getInt("ticksRemaining");
        ticksInRecipe = nbt.getInt("totalTicks");
        super.load(state, nbt);
    }

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(5){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch(slot){
                    case 0: return stack.getItem().is(TapeTags.Items.ALLOWED_COFFEE_FLUIDS);
                    case 1: return stack.getItem().is(TapeTags.Items.BEANS);
                    case 2: return stack.getItem() == ModItems.MUG.get();
                    case 4: return true;
                    default: return false;
                }
            }

            @Override
            public int getSlotLimit(int slot) {
                switch (slot){
                    case 1: return 64;
                    case 2: return 64;
                    case 3: return 64;
                    case 4: return 64;
                    default: return 1;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)){
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }

        };
    }

    public ItemStack overrideInsert(int slot, @Nonnull ItemStack stack, boolean simulate){
        ItemStack current = itemHandler.getStackInSlot(slot);
        if (current.sameItem(stack)){
            if(current.getMaxStackSize() < current.getCount()+1)
                return stack;
            current.setCount(current.getCount()+1);
            itemHandler.setStackInSlot(slot, current);
            return ItemStack.EMPTY;
        }
        else if (current.getCount() == 0) {
            itemHandler.setStackInSlot(slot, stack);
        }

        return stack;

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    public boolean addWater(int buckets)
    {
        System.out.println("Filling...");

        if(waterBottles + (buckets * 3) > maxWaterStore) {
            return false;
        }
        waterBottles += (buckets * 3);
        this.setChanged();
        return true;
    }

    public List<ItemStack> getDrops(){
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        for(int i = 0; i < itemHandler.getSlots(); i++){
            if(!itemHandler.getStackInSlot(i).isEmpty()){
                stacks.add(itemHandler.getStackInSlot(i).copy());
            }
        }
        return stacks;
    }

    public boolean removeWater(int buckets){
        if (waterBottles - (buckets * 3) >= 0){
            waterBottles -= buckets * 3;
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean hasWater(){
        return waterBottles > 0;
    }

    public boolean hasEnoughWater(int bottles){
        return waterBottles >= bottles;
    }

    public boolean recipeInAction(){
        return ticksLeft > 0 && ticksInRecipe > 0;
    }

    public int getWaterBottles(){
        return waterBottles;
    }

    public int getMaxWaterStore(){
        return maxWaterStore;
    }

    public int getTicksInRecipe(){
        return ticksInRecipe;
    }

    public int getTicksLeft(){
        return ticksLeft;
    }

    public void startCraft(){
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<CoffeeMakerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.COFFEE_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            if(waterBottles >= iRecipe.getBottlesReq() && hasMug()){
                ticksInRecipe = iRecipe.getTicksReq();
                this.recipe = iRecipe;
                ticksLeft = ticksInRecipe;
                sendUpdate();
            }
        });


    }


    public void craftRecipe(CoffeeMakerRecipe iRecipe){
        ItemStack output = iRecipe.getResultItem();

        if(iRecipe.getBottlesReq() <= waterBottles){
            itemHandler.extractItem(1, 1, false);
            itemHandler.extractItem(2, 1, false);
            if(recipe.getIngredients().size() == 2)
                itemHandler.extractItem(4, 1, false);
                if(itemHandler.getStackInSlot(4).sameItem(new ItemStack(Items.MILK_BUCKET))){
                    emptyBucketIngredient();
                }
            waterBottles -= iRecipe.getBottlesReq();
            overrideInsert(3, output, false);
        }

        resetRecipe();
    }

    public void resetRecipe(){
        recipe = null;
        ticksInRecipe = -1;
        ticksLeft = 0;

        sendUpdate();
    }

    public boolean hasMug(){
        return itemHandler.getStackInSlot(2).sameItem(new ItemStack(ModItems.MUG.get()));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("water", waterBottles);
        nbt.putInt("maxWater", maxWaterStore);
        nbt.putInt("ticksRemaining", ticksLeft);
        nbt.putInt("totalTicks", ticksInRecipe);
        return new SUpdateTileEntityPacket(getBlockPos(), -1, nbt);
    }

    public boolean recipeValid(){
        if(recipe.getIngredients().size() == 1 && !itemHandler.getStackInSlot(4).sameItem(ItemStack.EMPTY)){
            return false;
        }
        boolean fin = recipe.getIngredients().get(0).test(itemHandler.getStackInSlot(1)) && hasMug();
        System.out.println(fin);
        if(recipe.getIngredients().size() == 2){
            fin = fin && recipe.getIngredients().get(1).test(itemHandler.getStackInSlot(4));
        }
        return fin;
    }

    public void emptyBucketIngredient(){
        itemHandler.setStackInSlot(4, new ItemStack(Items.BUCKET.getItem()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getTag();
        if(tag.contains("water")){
            waterBottles = tag.getInt("water");
            this.getTileData().putInt("water", waterBottles);
        }
        if(tag.contains("maxWater")){
            maxWaterStore = tag.getInt("maxWater");
            this.getTileData().putInt("maxWater", maxWaterStore);
        }
        if(tag.contains("ticksRemaining")){
            ticksLeft = tag.getInt("ticksRemaining");
            this.getTileData().putInt("ticksRemaining", ticksLeft);
        }
        if(tag.contains("totalTicks")){
            ticksInRecipe = tag.getInt("totalTicks");
            this.getTileData().putInt("totalTicks", ticksInRecipe);
        }
    }

    @Override
    public void tick() {
        if(level.isClientSide){
            return;
        }
        if(itemHandler.getStackInSlot(0).getItem().is(TapeTags.Items.ALLOWED_COFFEE_FLUIDS)){
            if(addWater(1)) {
                itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET.getItem()));
            }
            sendUpdate();
        }
        if(recipeInAction()){
            ticksLeft -= 1;
            System.out.println("Ticking Down...");
            if(!recipeInAction()){
                System.out.println("Ticking done");
                craftRecipe(recipe);
            }
            else if (!recipeValid()){
                System.out.println("Invalid recipe, removing");
                resetRecipe();
            }
        }
        else {
            startCraft();
        }
        sendUpdate();
    }

    private void sendUpdate(){
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }
}


