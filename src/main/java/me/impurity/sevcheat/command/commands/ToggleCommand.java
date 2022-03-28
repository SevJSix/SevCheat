package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.Module;
import me.impurity.sevcheat.util.Utils;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("toggle");
    }

    @Override
    public void run(String[] args) {
        try {
            Module module = SevCheat.moduleManager.getModuleByName(args[0]);
            module.toggle();
        } catch (Throwable t) {
            Utils.sendMessage("Invalid module name. Make sure you type the module name correctly is it is Case-Sensitive");
            sendAllModules();
        }
    }
}
