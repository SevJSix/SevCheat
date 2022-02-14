package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.util.text.TextFormatting;

public class Auto32kChannelCommand extends Command {
    public Auto32kChannelCommand() {
        super("channel");
    }

    @Override
    public void run(String[] args) {
        try {
            String channel = args[0];
            Utils.setChannelPayload(channel);
            Utils.sendMessage(TextFormatting.AQUA + "Set payload channel to: " + channel);
        } catch (Throwable t) {
            Utils.sendMessage("Please specify a payload channel. (manual32k, or auto32k)");
        }
    }
}
