package com.rawsome1234.tape.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup TAB_TAPE = new ItemGroup("tapetab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.MASKING_TAPE.get());
        }
    };
}
