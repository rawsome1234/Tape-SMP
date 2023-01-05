package com.rawsome1234.tape.integration.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.block.ModBlocks;
import com.rawsome1234.tape.data.recipes.CoffeeMakerRecipe;
import com.rawsome1234.tape.item.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CoffeeMakerRecipeCategory implements IRecipeCategory<CoffeeMakerRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Tape.MOD_ID, "coffee");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Tape.MOD_ID, "textures/gui/coffee_maker_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable water;
    private final IDrawable requiredMug;

    public CoffeeMakerRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0,176, 166);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.COFFEE_MAKER.get()));
        this.water = helper.createDrawable(TEXTURE, 176, 0, 18,53);
        this.requiredMug = helper.createDrawableIngredient(new ItemStack(ModItems.MUG.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends CoffeeMakerRecipe> getRecipeClass() {
        return CoffeeMakerRecipe.class;
    }

    @Override
    public String getTitle() {
        return ModBlocks.COFFEE_MAKER.get().getName().getString();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(CoffeeMakerRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CoffeeMakerRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(1, true, 94, 11);
        recipeLayout.getItemStacks().init(2, true, 94, 34);
        recipeLayout.getItemStacks().init(4, true, 94, 57);
        recipeLayout.getItemStacks().set(4, new ItemStack(ModItems.MUG.get()));

        recipeLayout.getItemStacks().init(3, false, 138, 34);
        recipeLayout.getItemStacks().set(ingredients);

    }

    @Override
    public void draw(CoffeeMakerRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
//        int bottles = recipe.getBottlesReq();
//        float percent = bottles / 12f;
        this.water.draw(matrixStack, 14, 15);
    }
}
