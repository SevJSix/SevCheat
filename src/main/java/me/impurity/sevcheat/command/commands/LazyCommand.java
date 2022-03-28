package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.ModuleManager;

public class LazyCommand extends Command {

    ModuleManager moduleManager = SevCheat.moduleManager;

    public LazyCommand() {
        super("lazy");
    }

    @Override
    public void run(String[] args) {
        if (!moduleManager.getModuleByName("Aura").isEnabled()) {
            moduleManager.getModuleByName("Aura").enable();
        }
        if (!moduleManager.getModuleByName("AutoTotem").isEnabled()) {
            moduleManager.getModuleByName("AutoTotem").enable();
        }
        if (!moduleManager.getModuleByName("TotemPopNotify").isEnabled()) {
            moduleManager.getModuleByName("TotemPopNotify").enable();
        }
        if (!moduleManager.getModuleByName("BlockHighlight").isEnabled()) {
            moduleManager.getModuleByName("BlockHighlight").enable();
        }
        if (!moduleManager.getModuleByName("Velocity").isEnabled()) {
            moduleManager.getModuleByName("Velocity").enable();
        }
        if (!moduleManager.getModuleByName("SecretClose").isEnabled()) {
            moduleManager.getModuleByName("SecretClose").enable();
        }
        if (!moduleManager.getModuleByName("ChatSuffix").isEnabled()) {
            moduleManager.getModuleByName("ChatSuffix").enable();
        }
    }
}
