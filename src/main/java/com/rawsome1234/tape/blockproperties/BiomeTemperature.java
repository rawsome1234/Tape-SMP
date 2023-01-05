package com.rawsome1234.tape.blockproperties;

import net.minecraft.util.IStringSerializable;
import net.minecraft.world.biome.Biome;

public enum BiomeTemperature implements IStringSerializable {
    WARM("warm"),
    COLD("cold"),
    NEUTRAL("neutral");


    private final String name;

    private BiomeTemperature(String name) { this.name = name; }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() { return this.name; }

    public static BiomeTemperature toEnum(Biome.Category biome){
        switch(biome){
            case ICY: return COLD;
            case OCEAN: return COLD;
            case TAIGA: return COLD;
            case MESA: return WARM;
            case DESERT: return WARM;
            case JUNGLE: return WARM;
            case SAVANNA: return WARM;
            case SWAMP: return WARM;
            default: return NEUTRAL;
        }
    }
}
