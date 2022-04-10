package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.modules.combat.Auto32k;
import me.impurity.sevcheat.util.Utils;

public class Auto32kMode extends Command {
    public Auto32kMode() {
        super("32kmode");
    }

    @Override
    public void run(String[] args) {
        try {
            String mode = args[0];
            switch (mode) {
                case "auto":
                    Auto32k.autoPlace = true;
                    Utils.sendMessage("Set auto32k mode to AUTO_PLACE");
                    break;
                case "manual":
                    Auto32k.autoPlace = false;
                    Utils.sendMessage("Set auto32k mode to MANUAL_PLACE");
                    break;
                default:
                    Utils.sendMessage("Modes: auto, manual");
                    break;
            }
        } catch (Throwable t) {
            Utils.sendMessage("Modes: auto, manual");
        }
    }
}
