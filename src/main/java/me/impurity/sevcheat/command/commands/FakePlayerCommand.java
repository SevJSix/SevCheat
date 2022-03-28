package me.impurity.sevcheat.command.commands;

import me.impurity.sevcheat.SevCheat;
import me.impurity.sevcheat.command.Command;

public class FakePlayerCommand extends Command {
    public FakePlayerCommand() {
        super("fakeplayer");
    }

    @Override
    public void run(String[] args) {
        SevCheat.moduleManager.getModuleByName("FakePlayer").toggle();
    }
}
