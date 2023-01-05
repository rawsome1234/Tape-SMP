package com.rawsome1234.tape.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;

public class Piss {
    public Piss(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("piss").executes((command) -> {
            return pissMessage(command.getSource());
        }));
    }

    private int pissMessage(CommandSource source) throws CommandSyntaxException{
        ServerPlayerEntity player = source.getPlayerOrException();
        source.sendSuccess(new StringTextComponent("piss"), true);
        return 1;
    }

}
