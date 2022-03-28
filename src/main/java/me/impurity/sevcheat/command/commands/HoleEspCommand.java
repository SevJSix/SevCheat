package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.modules.render.HoleEsp;
import me.impurity.sevcheat.util.Utils;
import net.minecraft.util.text.TextFormatting;

public class HoleEspCommand extends Command {
    public HoleEspCommand() {
        super("esphole");
    }

    @Override
    public void run(String[] args) {
        try {
            if (args.length > 1) {
                switch (args[0].toLowerCase()) {
                    case "height":
                        double height = Double.parseDouble(args[1]);
                        HoleEsp.setHeight(height);
                        Utils.sendMessage(TextFormatting.AQUA + "HoleESP block height set to " + height);
                        break;
                    case "range":
                        int range = Integer.parseInt(args[1]);
                        HoleEsp.setRange(range);
                        Utils.sendMessage(TextFormatting.AQUA + "HoleESP block range set to " + range);
                        break;
                }
            }
        } catch (Throwable t) {
            Utils.sendMessage("An error occured while executing command. make sure the command arguments are correct.");
        }
    }
}
