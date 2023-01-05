package com.rawsome1234.tape.integration.jei;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.data.recipes.CoffeeMakerRecipe;
import com.rawsome1234.tape.data.recipes.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class TapeJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Tape.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new CoffeeMakerRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.COFFEE_RECIPE).stream()
                .filter(r -> r instanceof CoffeeMakerRecipe).collect(Collectors.toList()), CoffeeMakerRecipeCategory.UID);
    }
}
