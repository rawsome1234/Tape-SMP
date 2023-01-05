package com.rawsome1234.tape.util.SaveData;

import com.rawsome1234.tape.Tape;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

public class SuggestionSaveData extends WorldSavedData {

    public static final String NAME = Tape.MOD_ID + "_suggestionsave";

    private final List<SuggestionStorageObject> DATA = new ArrayList<>();

    public SuggestionSaveData(String p_i2141_1_) {
        super(p_i2141_1_);
    }

    public SuggestionSaveData(){
        this(NAME);
    }

    @Override
    public void load(CompoundNBT nbt) {
        CompoundNBT saveData = nbt.getCompound("savedata");
        for(int i = 0; saveData.contains("data"+i); i++){
            DATA.add(SuggestionStorageObject.serialize(saveData.getCompound("data"+i)));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT saveData = new CompoundNBT();
        for(ListIterator<SuggestionStorageObject> iterator = DATA.listIterator(); iterator.hasNext(); ){
            saveData.put("data"+iterator.nextIndex(), iterator.next().deserialize());
        }
        nbt.put("savedata", saveData);
        return nbt;
    }

    public static void putData(SuggestionStorageObject object, ServerWorld world){
        SuggestionSaveData data = world.getDataStorage().computeIfAbsent(SuggestionSaveData::new,SuggestionSaveData.NAME);
        data.DATA.add(object);
        data.setDirty();
    }

    public static void putData(String suggestion, String author, UUID uuid, ServerWorld world){
        SuggestionSaveData.putData(new SuggestionStorageObject(suggestion, author, uuid), world);
    }

    public static SuggestionSaveData getData(ServerWorld world){
        return world.getDataStorage().get(SuggestionSaveData::new, SuggestionSaveData.NAME);
    }

    public List<SuggestionStorageObject> getDATA(){
        return DATA;
    }

    public static String parseSaveData(SuggestionSaveData saveData){
        String result = "";
        List<SuggestionStorageObject> DATA = saveData.getDATA();
        for(SuggestionStorageObject object: DATA){
            CompoundNBT info = object.deserialize();
            result += "Suggestion: " + info.getString("suggestion") + ", Author: " + info.getString("author");
            result += "\n";
        }
        return result;
    }


    static class SuggestionStorageObject{
        private final String suggestion;
        private final String author;
        private final UUID id;

        SuggestionStorageObject(String suggestion, String author, UUID id) {
            this.suggestion = suggestion;
            this.author = author;
            this.id = id;
        }

        public CompoundNBT deserialize(){
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("suggestion", suggestion);
            nbt.putString("author",author);
            nbt.putUUID("id",id);
            return nbt;
        }

        public static SuggestionStorageObject serialize(CompoundNBT nbt){
            return new SuggestionStorageObject(nbt.getString("suggestion"), nbt.getString("author"), nbt.getUUID("id"));
        }

    }

}
