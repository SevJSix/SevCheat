package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.modules.player.ChatSuffix;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.util.text.TextFormatting;

public class SuffixCommand extends Command {
    public SuffixCommand() {
        super("suffix");
    }

    @Override
    public void run(String[] args) {
        try {
            String suffix = args[0];
            ChatSuffix.setSuffix(suffix);
            Utils.sendMessage(TextFormatting.AQUA + "Set chat suffix to: " + suffix);
        } catch (Throwable t) {
            Utils.sendMessage("Specify a chat suffix");
        }
    }
}
