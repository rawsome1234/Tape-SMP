package com.rawsome1234.tape.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.container.CoffeeMakerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CoffeeMakerScreen extends ContainerScreen<CoffeeMakerContainer> {

    private final ResourceLocation GUI = new ResourceLocation(Tape.MOD_ID, "textures/gui/coffee_maker_gui.png");

    public CoffeeMakerScreen(CoffeeMakerContainer p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack stack, float partialTicks, int x, int y) {

        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bind(GUI);
        int i = this.getGuiLeft();
        int j = this.getGuiTop();
        this.blit(stack, i, j, 0, 0, this.getXSize(), this.getYSize());

        if(menu.getBottles() != -1){
            float l = ((float) menu.getBottles()) / menu.getMaxBottles();
            int pixelHeight = Math.round(l * 52);
//          System.out.println(pixelHeight);
            this.blit(stack, i + 14, j+16+52-pixelHeight, 176, 53-pixelHeight, 18, pixelHeight);
        }



        int ticksInRecipe = menu.getTotalTicks();
//        System.out.println(ticksInRecipe);
        // if recipe in progress
        if(ticksInRecipe != -1){
            float recipeProgress = 1 - (menu.getRemainingTicks() / ((float) ticksInRecipe));
            int pixelsPainted = Math.round(recipeProgress * 23);
//            System.out.println("Pixels: " + pixelsPainted + "\nRecipe Progress: " + recipeProgress);
            this.blit(stack, i+111, j+17, 176, 53, pixelsPainted, 51);
        }


    }
}
