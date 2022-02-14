package me.impurity.sevcheat.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class PacketRecievingEvent extends PacketEvent {
    public PacketRecievingEvent(Packet<?> packet) {
        super(packet);
    }
}
