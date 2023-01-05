package com.rawsome1234.tape.tileentity;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Tape.MOD_ID);


    public static RegistryObject<TileEntityType<CoffeeMakerTile>> COFFEE_MAKER_TILE =
            TILE_ENTITIES.register("coffee_maker_tile", ()-> TileEntityType.Builder.of(
                    CoffeeMakerTile::new, ModBlocks.COFFEE_MAKER.get()).build(null
            ));

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register(eventBus);
    }

}
