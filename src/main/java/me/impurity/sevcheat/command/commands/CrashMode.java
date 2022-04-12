package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;
import me.impurity.sevcheat.module.modules.combat.Auto32k;
import me.impurity.sevcheat.module.modules.exploit.SevCrasher;
import me.impurity.sevcheat.util.Utils;

public class CrashMode extends Command {

    public CrashMode() {
        super("crasher");
    }

    @Override
    public void run(String[] args) {
        try {
            String mode = args[0];
            switch (mode) {
                case "tacohack":
                    SevCrasher.mode = SevCrasher.CrashMode.OUT_OF_BOUNDS;
                    Utils.sendMessage("Set crasher mode to OUT_OF_BOUNDS");
                    break;
                case "armorcrash":
                    SevCrasher.mode = SevCrasher.CrashMode.ARMOR_CRASH;
                    Utils.sendMessage("Set crasher mode to ARMOR_CRASH");
                    break;
                default:
                    Utils.sendMessage("Modes: tacohack, armorcrash");
                    break;
            }
        } catch (Throwable t) {
            Utils.sendMessage("Modes: tacohack, armorcrash");
        }
    }
}
