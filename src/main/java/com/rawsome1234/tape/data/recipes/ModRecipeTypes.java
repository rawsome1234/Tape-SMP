package com.rawsome1234.tape.data.recipes;

import com.rawsome1234.tape.Tape;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Tape.MOD_ID);

    public static final RegistryObject<CoffeeMakerRecipe.Serializer> COFFEE_SERIALIZER
            = RECIPE_SERIALIZER.register("coffee", CoffeeMakerRecipe.Serializer::new);

    public static IRecipeType<CoffeeMakerRecipe> COFFEE_RECIPE
            = new CoffeeMakerRecipe.CoffeeRecipeType();

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, CoffeeMakerRecipe.TYPE_ID, COFFEE_RECIPE);
    }
}
