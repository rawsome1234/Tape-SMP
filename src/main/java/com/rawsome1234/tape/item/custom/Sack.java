package com.rawsome1234.tape.item.custom;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.util.MobData;
import com.rawsome1234.tape.util.TapeTags;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.NBTCompoundTagArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.NBTTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sack extends Item {
    public Sack(Properties p_i48527_2_) {
        super(p_i48527_2_);
    }

//    Entity holding;
//    private List<Entity> blacklist = new ArrayList<Entity>().add();


    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        CompoundNBT current = player.getMainHandItem().getOrCreateTag().getCompound("holding");
        if (entity instanceof Entity && current == null) {
            CompoundNBT nbt = new CompoundNBT();
            current.putUUID("holding", entity.getUUID());
            player.getMainHandItem().getOrCreateTag().put("holding", current);
            entity.remove();
            player.getMainHandItem().getOrCreateTag().putFloat("filled", 1f);
            return true;

        }

        return false;
    }

    public static boolean canCatchEntity(Entity e){
        EntityType<?> type = e.getType();
        return !type.is(TapeTags.Entities.SACK);
    }

    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (this.canCatchEntity(entity) && !isFull(stack)){
            // Capture
            entity.revive();

            if (player.level.isClientSide) return ActionResultType.SUCCESS;
            player.setItemInHand(hand,
                    captureEntity(entity, stack, ItemStack.EMPTY));
            entity.remove();
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    public ItemStack captureEntity(Entity e, ItemStack current, ItemStack bucket){
        ItemStack ret = new ItemStack(this);
        CompoundNBT cmp = MobData.genNBT(e, bucket);
        if (cmp != null) ret.addTagElement("BlockEntityTag", cmp);
        return ret;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        ItemStack stack = context.getItemInHand();
        CompoundNBT com = stack.getTagElement("BlockEntityTag");
        PlayerEntity player = context.getPlayer();
        if (!player.isShiftKeyDown() && com != null){
            boolean success = false;
            World world = context.getLevel();
            Vector3d v = context.getClickLocation();
            if (com.contains("MobHolder")){
                CompoundNBT nbt = com.getCompound("MobHolder");
                Entity entity = EntityType.loadEntityRecursive(nbt.getCompound("EntityData"), world, o -> o);

                if (entity != null){
                    success = true;
                    if (!world.isClientSide()){
                        entity.absMoveTo(v.x(), v.y(), v.z(), context.getRotation(), 0);
                        UUID temp = entity.getUUID();

                        if (nbt.contains("UUID")){
                            UUID id = nbt.getUUID("UUID");
                            entity.setUUID(id);
                        }

                        if (!world.addFreshEntity(entity)){
                            entity.setUUID(temp);
                            success = world.addFreshEntity(entity);
                        }


                    }

                }
            }
            if (success) {
                if (!world.isClientSide()){
                    ItemStack ret = new ItemStack(this);
                    player.setItemInHand(context.getHand(), ret);
                }
                return ActionResultType.sidedSuccess(world.isClientSide);
            }
        }



        return super.useOn(context);
    }

    public boolean isFull(ItemStack stack){
        CompoundNBT tag = stack.getTag();
        return tag != null && tag.contains("BlockEntityTag");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> text, ITooltipFlag tool) {
        super.appendHoverText(stack, world, text, tool);
        CompoundNBT nbt = stack.getTagElement("BlockEntityTag");
        if (nbt == null) return;
        if(nbt.contains("MobHolder")){
            text.add(new TranslationTextComponent("message.tape.sack")
                    .append(nbt.getCompound("MobHolder").getString("Name")));
        }
    }

//    @Override
//    public ActionResultType place(BlockItemUseContext context) {
//        PlayerEntity player = context.getPlayer();
//        if (player != null && player.isShiftKeyDown()) {
//            return super.place(context);
//        }
//        return ActionResultType.PASS;
//    }

}