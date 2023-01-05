package com.rawsome1234.tape.container;

import com.rawsome1234.tape.Tape;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, Tape.MOD_ID);

    public static final RegistryObject<ContainerType<CoffeeMakerContainer>> COFFEE_MAKER_CONTAINER =
            CONTAINERS.register("coffee_maker_container",
                    ()-> IForgeContainerType.create(((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        World world = inv.player.level;
                        return new CoffeeMakerContainer(windowId, world, pos, inv, inv.player);
                    })));

    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }

}
