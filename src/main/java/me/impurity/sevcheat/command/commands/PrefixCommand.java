package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.command.CommandManager;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.util.text.TextFormatting;

public class PrefixCommand extends Command {

    private final CommandManager manager;

    public PrefixCommand(CommandManager manager) {
        super("prefix");
        this.manager = manager;
    }

    @Override
    public void run(String[] args) {
        try {
            String prefix = args[0];
            manager.setPrefix(prefix);
            Utils.sendMessage(TextFormatting.AQUA + "Set prefix to " + prefix);
        } catch (Throwable t) {
            Utils.sendMessage("Specify a prefix character");
        }
    }
}
