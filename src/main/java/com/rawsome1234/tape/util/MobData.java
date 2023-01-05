package com.rawsome1234.tape.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.apache.commons.lang3.concurrent.Computable;

import javax.annotation.Nullable;
import java.util.UUID;

public class MobData {
    public final String name;
    public final CompoundNBT mobTag;
    @Nullable
    private final UUID uuid;

    public MobData(String name, CompoundNBT mobTag, @Nullable UUID uuid) {
        this.name = name;
        this.mobTag = mobTag;
        this.uuid = uuid;
    }

    public static MobData loadFromTag(CompoundNBT tag){
        if(tag.contains("MobHolder")){
            CompoundNBT cmp = tag.getCompound("MobHolder");
            CompoundNBT entityData = cmp.getCompound("EntityData");
            String name = cmp.getString("Name");
            UUID uuid = cmp.contains("UUID") ? cmp.getUUID("UUID") : null;
            return new MobData(name, entityData, uuid);
        }
        return null;
    }

    public void saveToTage(CompoundNBT tag){
        CompoundNBT cmp = new CompoundNBT();
        cmp.putString("Name", name);
        cmp.put("EntityData", mobTag);
        if (uuid != null) cmp.putUUID("UUID", uuid);

        tag.put("MobHolder", cmp);

    }

    public static CompoundNBT genNBT(Entity e, ItemStack bucket){
        MobData data;
        String name = e.getName().getString();
        CompoundNBT mobtag = new CompoundNBT();
        e.save(mobtag);

        mobtag.remove("Passengers");
        mobtag.remove("Leash");
        mobtag.remove("UUID");

        if (mobtag == null) return null;

        UUID id = e.getUUID();
        data = new MobData(name, mobtag, id);
        CompoundNBT cmp = new CompoundNBT();
        data.saveToTage(cmp);
        return cmp;
    }

}
