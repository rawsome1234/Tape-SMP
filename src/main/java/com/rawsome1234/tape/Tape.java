package com.rawsome1234.tape;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rawsome1234.tape.block.ModBlocks;
import com.rawsome1234.tape.container.ModContainers;
import com.rawsome1234.tape.data.recipes.ModRecipeTypes;
import com.rawsome1234.tape.item.ModItems;
import com.rawsome1234.tape.screen.CoffeeMakerScreen;
import com.rawsome1234.tape.tileentity.ModTileEntities;
import com.rawsome1234.tape.util.SaveData.SuggestionSaveData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.command.CommandSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Tape.MOD_ID)
public class Tape
{
    public static List<JsonObject> suggestions = new ArrayList<JsonObject>() {

        @Override
        public String toString(){
            String res = new String();

            for (JsonObject obj: this){
                res.concat(obj.toString() + "  ");
            }

            return res;
        }
    };

    public static final String MOD_ID = "tape";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Tape() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTileEntities.register(eventBus);
        ModContainers.register(eventBus);
        ModRecipeTypes.register(eventBus);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void addNewSuggestion(CommandSource source, JsonObject json) throws CommandSyntaxException {
        World world = source.getPlayerOrException().getCommandSenderWorld();
        if (!world.isClientSide){
            SuggestionSaveData.putData(json.get("suggestion").toString(), json.get("player").toString(), UUID.randomUUID(), world.getServer().overworld());
        }

        addSuggestionG(json);
    }

    public static void addSuggestionG(JsonObject json){
        suggestions.add(json);
    }

    public static List<JsonObject> getSuggestions(){

        return suggestions;
    }

    public static String getSuggestionsString(ServerWorld world){
        return SuggestionSaveData.parseSaveData(SuggestionSaveData.getData(world));
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        ScreenManager.register(ModContainers.COFFEE_MAKER_CONTAINER.get(),
                CoffeeMakerScreen::new);

        RenderTypeLookup.setRenderLayer(ModBlocks.COFFEE_CROP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.FROLEN_CROP.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.MOLZEN_CROP.get(), RenderType.cutout());
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("tape", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
