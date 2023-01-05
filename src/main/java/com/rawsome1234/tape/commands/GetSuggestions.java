package com.rawsome1234.tape.commands;

import com.rawsome1234.tape.Tape;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.rawsome1234.tape.item.custom.SuggestionSlip;
import com.rawsome1234.tape.util.TapeTags;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.impl.ExecuteCommand;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import org.lwjgl.system.CallbackI;

public class GetSuggestions {
    public GetSuggestions(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(Commands.literal("suggest").then(Commands.literal("get").requires((source) -> {
            return source.hasPermission(2);
                }).executes(
                (command) -> { return getSuggestions(command.getSource());
        })).then(Commands.literal("add").then(Commands.argument("suggestion", StringArgumentType.string()).executes(
                (command) -> { return addSuggestions(command.getSource(), StringArgumentType.getString(command, "suggestion"));
                })))
        );

    }

    private ServerPlayerEntity getPlayer(CommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrException();
        return player;
    }

    private int getSuggestions(CommandSource source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getPlayerOrException();

        String command = "/tellraw @p {\"text\":\"Click to Copy JSON\",\"clickEvent\":{\"action\":\"copy_to_clipboard\",\"value\":\"";

        String output = Tape.getSuggestionsString(source.getServer().overworld());

        for(int i = 0; i < output.length(); i++){
            if(output.charAt(i) == '"' || output.charAt(i) == '\n'){
                output = output.substring(0,i) + output.substring(i+1);
                i--; continue;
            }

        }
        command += output;
//        System.out.println(output);
        command += "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to Copy\"}}";
        int result = source.getServer().getCommands().performCommand(source, command);

        return result;
    }
    private int addSuggestions(CommandSource source, String suggestion) throws CommandSyntaxException{
        ServerPlayerEntity player = source.getPlayerOrException();

        boolean slip = player.getMainHandItem().getItem().is(TapeTags.Items.SLIP);
        System.out.println(slip);
        if(!slip){
            source.sendFailure(new StringTextComponent("You don't have a suggestion slip in your mainhand!"));
            return -1;
        }

        JsonObject json = new JsonObject();

        json.addProperty("suggestion", suggestion);
        json.addProperty("player", player.getName().getString());

        Tape.addNewSuggestion(source, json);

        source.sendSuccess(new StringTextComponent("Your suggestion has been added!"), true);

        getPlayer(source).getMainHandItem().setCount(getPlayer(source).getMainHandItem().getCount()-1);
        return 1;
    }
}
