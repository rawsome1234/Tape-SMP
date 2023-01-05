package com.rawsome1234.tape.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import com.rawsome1234.tape.block.ModBlocks;
import com.rawsome1234.tape.item.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class CoffeeMakerRecipe implements ICoffeeMakerRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int bottlesReq;
    private final int ticksReq;

    public CoffeeMakerRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int bottlesReq, int ticksReq) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.bottlesReq = bottlesReq;
        this.ticksReq = ticksReq;
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        if(recipeItems.get(0).test((inv.getItem(1)))){
            if(recipeItems.size() == 1)
                return true;
            return recipeItems.get(1).test(inv.getItem(4))
                    && inv.getItem(2).getItem() == ModItems.MUG.get();
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return false;
    }

    public int getBottlesReq(){
        return bottlesReq;
    }

    public ItemStack getIcon(){
        return new ItemStack(ModBlocks.COFFEE_MAKER.get());
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getTicksReq(){
        return ticksReq;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.COFFEE_SERIALIZER.get();
    }

    public static class CoffeeRecipeType implements IRecipeType<CoffeeMakerRecipe> {
        @Override
        public String toString() {
            return CoffeeMakerRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<CoffeeMakerRecipe> {

        @Override
        public CoffeeMakerRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));
            int bottles = JSONUtils.getAsInt(json, "bottles");
            int ticks = JSONUtils.getAsInt(json, "ticks");

            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CoffeeMakerRecipe(recipeID, output, inputs, bottles, ticks);
        }

        @Nullable
        @Override
        public CoffeeMakerRecipe fromNetwork(ResourceLocation recipeID, PacketBuffer packet) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(packet.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++){
                inputs.set(i, Ingredient.fromNetwork(packet));
            }

            ItemStack output = packet.readItem();
            return new CoffeeMakerRecipe(recipeID, output, inputs, 0, 200);
        }

        @Override
        public void toNetwork(PacketBuffer packet, CoffeeMakerRecipe recipe) {
            packet.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()){
                ing.toNetwork(packet);
            }
            packet.writeItemStack(recipe.getResultItem(), false);
        }
    }



}
