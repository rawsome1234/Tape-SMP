package com.rawsome1234.tape.events;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.events.loot.CoffeeBeansModifier;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = Tape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        event.getRegistry().registerAll(
                new CoffeeBeansModifier.Serializer().setRegistryName
                        (new ResourceLocation(Tape.MOD_ID, "coffee_bean_in_mineshaft"))



        );
    }

}
