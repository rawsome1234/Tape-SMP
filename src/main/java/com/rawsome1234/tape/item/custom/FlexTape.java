package com.rawsome1234.tape.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.Properties;

public class FlexTape extends Item {
    public static int repairAmount = 100;

    public FlexTape(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        World world = p_77659_1_;
        PlayerEntity player = p_77659_2_;
        Hand hand = p_77659_3_;

        if(!world.isClientSide){
            ItemStack mainhand = player.getMainHandItem();
            ItemStack offhand = player.getOffhandItem();
            if (offhand.getCount() == 1 && offhand.isRepairable() && offhand.getDamageValue() != 0) {
                // item in main hand is masking tape
                setDamage(offhand, getDamage(offhand) - repairAmount);
                mainhand.setCount(mainhand.getCount() - 1);
                
            }
            else if (mainhand.getCount() == 1 && mainhand.isRepairable() && mainhand.getDamageValue() != 0){
                //item in main hand is not masking tape
                setDamage(mainhand, getDamage(mainhand) - repairAmount);
                offhand.setCount(offhand.getCount()-1);
            }
        }

        return super.use(p_77659_1_, p_77659_2_, p_77659_3_);
    }
}
