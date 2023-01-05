package com.rawsome1234.tape.data.recipes;

import com.rawsome1234.tape.Tape;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface ICoffeeMakerRecipe extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(Tape.MOD_ID, "coffee");

    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }


}