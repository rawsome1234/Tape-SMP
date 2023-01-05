package com.rawsome1234.tape.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SuggestionSlip extends Item {

    public SuggestionSlip(Properties p_i48455_1_) {
        super(p_i48455_1_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown())
            tooltip.add(new TranslationTextComponent("tooltip.tape.suggestion_slip_shift"));
        else
            tooltip.add(new TranslationTextComponent("tooltip.tape.suggestion_slip"));

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        return true;
    }
}
