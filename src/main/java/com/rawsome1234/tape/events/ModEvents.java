package com.rawsome1234.tape.events;

import com.rawsome1234.tape.Tape;
import com.rawsome1234.tape.commands.GetSuggestions;
import com.rawsome1234.tape.commands.Piss;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
@Mod.EventBusSubscriber(modid = Tape.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new Piss(event.getDispatcher());
        new GetSuggestions(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

}
